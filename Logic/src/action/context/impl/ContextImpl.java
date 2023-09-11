package action.context.impl;

import action.context.api.Context;
import action.second.entity.SecondaryEntity;
import instance.entity.api.EntityInstance;
import instance.entity.manager.api.EntityInstanceManager;
import instance.enviornment.api.ActiveEnvironment;
import instance.property.api.PropertyInstance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ContextImpl implements Context , Serializable {
    private EntityInstance entityInstance;
    private EntityInstanceManager entityInstanceManager;
    private ActiveEnvironment activeEnvironment;

    public ContextImpl(EntityInstance entityInstance, EntityInstanceManager entityInstanceManager, ActiveEnvironment activeEnvironment) {
        this.entityInstance = entityInstance;
        this.entityInstanceManager = entityInstanceManager;
        this.activeEnvironment = activeEnvironment;
    }

    @Override
    public void removeEntity(EntityInstance entityInstance) {
        entityInstanceManager.killEntity(entityInstance.getId());
    }

    @Override
    public EntityInstance getEntityInstance() {
        return entityInstance;
    }

    @Override
    public PropertyInstance getEnvironmentVariable(String name) {
        return activeEnvironment.getPropertyByName(name);
    }


    @Override
    public List<EntityInstance> getSecondEntityFilteredList(SecondaryEntity secondaryEntity) {

        String secondEntityName = secondaryEntity.getSecondEntity().getName();
        String secondEntityCount = secondaryEntity.getInstancesCount();

        List<EntityInstance> filteredSecondaryEntities = getInstancesWithName(secondEntityName);

        if (!secondEntityCount.equals("ALL")) {
            int count = Integer.parseInt(secondEntityCount);
            filteredSecondaryEntities = getSecondEntityFilteredListByCountAndCondition(secondaryEntity, filteredSecondaryEntities, count);
        }
        return filteredSecondaryEntities;
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
                    .filter(entityInstance -> secondaryEntity.evaluateCondition(duplicateContextWithEntityInstance(entityInstance), entityInstance))
                    .limit(count)
                    .collect(Collectors.toList());

        } else {
            newFilteredSecondaryEntities = getRandomElementsWithRepetition(filteredSecondaryEntities, count);
        }
        return newFilteredSecondaryEntities;
    }

    @Override
    public Context duplicateContextWithEntityInstance(EntityInstance newEntityInstance) {
        return new ContextImpl(newEntityInstance, entityInstanceManager, activeEnvironment);
    }

    private List<EntityInstance> getRandomElementsWithRepetition(List<EntityInstance> filteredSecondaryEntities, int count) {
        List<EntityInstance> randomList = new ArrayList<>(filteredSecondaryEntities);
        Collections.shuffle(randomList);

        return randomList.stream()
                .filter(EntityInstance::isAlive)
                .limit(count)
                .collect(Collectors.toList());
    }
}
