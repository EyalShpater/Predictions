package action.impl.condition.impl;

import action.api.AbstractAction;
import action.api.Action;
import action.api.ActionType;
import action.context.api.Context;
import action.impl.condition.Condition;
import action.impl.condition.impl.multiple.MultipleCondition;
import action.second.entity.SecondaryEntity;
import definition.entity.api.EntityDefinition;
import instance.entity.api.EntityInstance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ConditionImpl extends AbstractAction implements Condition, Serializable {
    private final MultipleCondition condition; // todo: think to change it to Condition instead of MultipleCondition
    List<Action> then;
    List<Action> notTrue;
    private final String logical;

    public ConditionImpl(MultipleCondition condition, String logical, EntityDefinition entity) {
        super(entity, ActionType.CONDITION);
        this.condition = condition;
        this.logical = logical;
        this.then = new ArrayList<>();
        this.notTrue = new ArrayList<>();
    }

    public ConditionImpl(MultipleCondition condition, String logical, EntityDefinition mainEntity, SecondaryEntity secondaryEntity) {
        super(mainEntity, secondaryEntity, ActionType.CONDITION);
        this.condition = condition;
        this.logical = logical;
        this.then = new ArrayList<>();
        this.notTrue = new ArrayList<>();
    }

    public void addThanList(List<Action> thanList) {
        if (thanList == null) {
            throw new IllegalArgumentException();
        }

        this.then = thanList;
    }

    public void addNotTrueList(List<Action> newNotTrueList) {
        if (newNotTrueList == null) {
            throw new IllegalArgumentException();
        }

        this.notTrue = newNotTrueList;
    }

    @Override
    public void apply(Context context) {
        if (isSecondaryEntityExist()) {
            evaluateConditionSecondaryEntityVersion(context);
        } else {
            evaluateAcordingToEntityInstance(context, context.getEntityInstance());
        }

    }


    private void evaluateConditionSecondaryEntityVersion(Context context) {
        if (!secondaryEntitiesInstances.isEmpty()) {
            for (EntityInstance secondEntityInstance : secondaryEntitiesInstances) {
                context.setSecondaryEntity(secondEntityInstance);
                evaluateAcordingToEntityInstance(context, secondEntityInstance);
            }
        } else {
            evaluateAcordingToEntityInstance(context, context.getEntityInstance());
        }

    }

    private void evaluateAcordingToEntityInstance(Context context, EntityInstance entityInstance) {
        if (evaluate(context, entityInstance)) {
            then.forEach(action -> action.invoke(context));
        } else if (notTrue != null) {
            notTrue.forEach(action -> action.invoke(context));
        }
    }

    @Override
    public boolean evaluate(Context context, EntityInstance secondEntityInstance) {
        return condition.evaluate(context, secondEntityInstance);
    }

    @Override
    public String getOperationSign() {
        return null;
    }

    @Override
    public Map<String, String> getArguments() {
        Map<String, String> attributes = new LinkedHashMap<>();
        Condition singleCondition = condition.isSingleCondition();

        if (singleCondition != null) {
            attributes = singleCondition.getArguments();
        } else {
            attributes = condition.getArguments();
        }

        return attributes;
    }
}