package action.api;

import action.context.api.Context;
import action.second.entity.SecondEntity;
import definition.entity.api.EntityDefinition;

import impl.ActionDTO;
import instance.entity.api.EntityInstance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractAction implements Action, Serializable {
    private final EntityDefinition mainEntity;

    private SecondEntity secondEntity;

    private final ActionType type;

    public AbstractAction(EntityDefinition mainEntity, ActionType type) {
        this.mainEntity = mainEntity;
        this.type = type;
    }

    public AbstractAction(EntityDefinition mainEntity, SecondEntity secondEntity, ActionType type) {
        this.mainEntity = mainEntity;
        this.secondEntity = secondEntity;
        this.type = type;
    }

    @Override
    public EntityDefinition applyOn() {
        return mainEntity;
    }

    @Override
    public ActionType getType() {
        return type;
    }

    @Override
    public String getName() {
        return type.name();
    }

    @Override
    public ActionDTO convertToDTO() {
        return new ActionDTO(
                type.name(),
                mainEntity.convertToDTO(),
                null, // todo: should add secondary entity to the class
                getArguments()
        );
    }

    @Override
    public EntityDefinition secondaryEntityForAction() {
        return secondEntity.getSecondEntity();
    }

    @Override
    public SecondEntity getSecondaryEntityForAction() {
        return secondEntity;
    }

    @Override
    public Boolean isSecondaryEntityExist() {
        return secondEntity != null;
    }

    @Override
    public List<EntityInstance> getSecondEntityFilteredList(Context context) {

        String secondEntityName = secondEntity.getSecondEntity().getName();
        String secondEntityCount = secondEntity.getInstancesCount();

        List<EntityInstance> filteredSecondaryEntities = context.getInstancesWithName(secondEntityName);

        if (!secondEntityCount.equals("ALL")) {
            int count = Integer.parseInt(secondEntityCount);
            filteredSecondaryEntities = getSecondEntityFilteredListByContAndCondition(filteredSecondaryEntities, context, count);
        }
        return filteredSecondaryEntities;
    }

    private List<EntityInstance> getSecondEntityFilteredListByContAndCondition(List<EntityInstance> filteredSecondaryEntities, Context context, int count) {

        List<EntityInstance> newFilteredSecondaryEntities;
        if (secondEntity.isConditionExist()) {
            newFilteredSecondaryEntities = filteredSecondaryEntities.stream()
                    .filter(entityInstance -> secondEntity.evaluateCondition(context.duplicateContextWithEntityInstance(entityInstance)))
                    .limit(count)
                    .collect(Collectors.toList());

        } else {
            newFilteredSecondaryEntities = getRandomElementsWithRepetition(filteredSecondaryEntities, count);
        }
        return newFilteredSecondaryEntities;
    }

    private List<EntityInstance> getRandomElementsWithRepetition(List<EntityInstance> filteredSecondaryEntities, int count) {
        List<EntityInstance> randomList = new ArrayList<>(filteredSecondaryEntities);
        Collections.shuffle(randomList);

        return randomList.stream()
                .limit(count)
                .collect(Collectors.toList());
    }
}
