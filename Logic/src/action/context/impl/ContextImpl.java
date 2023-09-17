package action.context.impl;

import action.api.Action;
import action.api.ReplaceMode;
import action.context.api.Context;
import action.second.entity.SecondaryEntity;
import definition.entity.api.EntityDefinition;
import instance.entity.api.EntityInstance;
import instance.entity.manager.api.EntityInstanceManager;
import instance.enviornment.api.ActiveEnvironment;
import instance.property.api.PropertyInstance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ContextImpl implements Context, Serializable {
    private EntityInstance primaryEntityInstance;
    private EntityInstance secondaryEntityInstance;
    private EntityInstanceManager entityInstanceManager;
    private ActiveEnvironment activeEnvironment;
    private int currentTickNumber;


    public ContextImpl(EntityInstance primaryEntity, EntityInstanceManager entityInstanceManager, ActiveEnvironment activeEnvironment, int tick) {
        this(primaryEntity, null, entityInstanceManager, activeEnvironment, tick);
    }

    public ContextImpl(EntityInstance primaryEntity, EntityInstance secondaryEntity, EntityInstanceManager entityInstanceManager, ActiveEnvironment activeEnvironment, int tick) {
        this.primaryEntityInstance = primaryEntity;
        this.secondaryEntityInstance = secondaryEntity;
        this.entityInstanceManager = entityInstanceManager;
        this.activeEnvironment = activeEnvironment;
        this.currentTickNumber = tick;
    }

    @Override
    public EntityInstance getPrimaryEntityInstance() {
        return primaryEntityInstance;
    }

    @Override
    public int getTickNumber() {
        return currentTickNumber;
    }

    @Override
    public int getTickThisPropertyWasntChanged(String propertyName) {
        PropertyInstance propertyInstance = primaryEntityInstance.getPropertyByName(propertyName);

        if (propertyInstance == null) {
            throw new IllegalArgumentException("no Such property as: " + propertyName + " for " + primaryEntityInstance.getName());
        }

        return currentTickNumber - propertyInstance.getLastUpdateTick();
    }

    @Override
    public EntityInstance getSecondaryEntityInstance() {
        return secondaryEntityInstance;
    }

    @Override
    public PropertyInstance getEnvironmentVariable(String name) {
        return activeEnvironment.getPropertyByName(name);
    }


    @Override
    public List<EntityInstance> getSecondEntityFilteredList(SecondaryEntity secondaryEntity) {

        String secondEntityName = secondaryEntity.getSecondaryEntity().getName();
        String secondEntityCount = secondaryEntity.getInstancesCount();

        List<EntityInstance> filteredSecondaryEntities = getInstancesWithName(secondEntityName);

        if (!secondEntityCount.equals("ALL")) {
            int count = Integer.parseInt(secondEntityCount);
            filteredSecondaryEntities = getSecondEntityFilteredListByCountAndCondition(secondaryEntity, filteredSecondaryEntities, count);
        }
        return filteredSecondaryEntities;
    }

    @Override
    public boolean isEntityRelatedToAction(String entityName) {
        boolean isEntityRelatedToAction = false;
        if (secondaryEntityInstance != null) {
            isEntityRelatedToAction = entityName.equals(primaryEntityInstance.getName()) || entityName.equals(secondaryEntityInstance.getName());
        } else {
            isEntityRelatedToAction = entityName.equals(primaryEntityInstance.getName());
        }
        return isEntityRelatedToAction;
    }

    @Override
    public Object getPropertyValueOfEntity(String entityName, String propertyName) {
        Object value;
        if (entityName.equals(primaryEntityInstance.getName())) {
            value = primaryEntityInstance.getPropertyByName(propertyName).getValue();
        } else if (secondaryEntityInstance != null && entityName.equals(secondaryEntityInstance.getName())) {
            value = secondaryEntityInstance.getPropertyByName(propertyName).getValue();
        } else {
            throw new IllegalArgumentException("No such entity name as: " + entityName);
        }
        return value;
    }

    @Override
    public void setSecondaryEntity(EntityInstance secondaryEntityInstance) {
        this.secondaryEntityInstance = secondaryEntityInstance;
    }

    @Override
    public void produceAnewEntityByMode(EntityDefinition entityToCreate, ReplaceMode mode) {
        switch (mode) {
            case DERIVED:
                entityInstanceManager.createNewEntityInstanceWithSamePropertyValues(this.primaryEntityInstance, entityToCreate, this);
                break;
            case SCRATCH:
                entityInstanceManager.createNewEntityInstanceFromScratch(entityToCreate, primaryEntityInstance.getSpace());
                break;
        }
        this.primaryEntityInstance.kill();
    }

    private List<EntityInstance> getInstancesWithName(String secondEntityName) {
        return entityInstanceManager.getInstances()
                .stream()
                .filter(eInstance -> eInstance.getName().equals(secondEntityName))
                .collect(Collectors.toList());
    }

    private List<EntityInstance> getSecondEntityFilteredListByCountAndCondition(SecondaryEntity secondaryEntity, List<EntityInstance> filteredSecondaryEntities, int count) {

        List<EntityInstance> newFilteredSecondaryEntities;
        if (secondaryEntity.isConditionExist()) {
            newFilteredSecondaryEntities = filteredSecondaryEntities.stream()
                    .filter(entityInstance -> secondaryEntity.evaluateCondition(duplicateContextWithEntityInstance(entityInstance)))
                    .limit(count)
                    .collect(Collectors.toList());

        } else {
            newFilteredSecondaryEntities = getRandomElementsWithRepetition(filteredSecondaryEntities, count);
        }
        return newFilteredSecondaryEntities;
    }

    @Override
    public Context duplicateContextWithEntityInstance(EntityInstance newEntityInstance) {
        return new ContextImpl(newEntityInstance, entityInstanceManager, activeEnvironment, currentTickNumber);
    }

    @Override
    public Context duplicateAndSwapPrimaryInstanceAndSecondary() {
        return new ContextImpl(secondaryEntityInstance, primaryEntityInstance, entityInstanceManager, activeEnvironment, currentTickNumber);
    }

    private List<EntityInstance> getRandomElementsWithRepetition(List<EntityInstance> filteredSecondaryEntities, int count) {
        List<EntityInstance> randomList = new ArrayList<>(filteredSecondaryEntities);
        Collections.shuffle(randomList);

        return randomList.stream()
                .filter(EntityInstance::isAlive)
                .limit(count)
                .collect(Collectors.toList());
    }

    @Override
    public void setForAction(Action action) {
        setPrimaryEntityByAction(action);
    }

    private void setPrimaryEntityByAction(Action action) {
        String actionPrimaryName = action.applyOn().getName();

        if (!(actionPrimaryName.equals(primaryEntityInstance.getName())) && !(actionPrimaryName.equals(secondaryEntityInstance.getName()))) {
            throw new IllegalArgumentException(
                    "The action \""
                            + action.getName()
                            + "\" apply on "
                            + action.applyOn().getName()
                            + " and there is not a valid instance of it"
            );
        }

        if (secondaryEntityInstance != null && actionPrimaryName.equals(secondaryEntityInstance.getName())) {
            EntityInstance temp = primaryEntityInstance;
            primaryEntityInstance = secondaryEntityInstance;
            secondaryEntityInstance = temp;
        }
    }
}
