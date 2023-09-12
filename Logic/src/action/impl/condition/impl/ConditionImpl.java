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
    private final Condition condition;
    List<Action> then;
    List<Action> notTrue;
    private final String logical;

    public ConditionImpl(Condition condition, String logical, EntityDefinition entity) {
        super(entity, ActionType.CONDITION);
        this.condition = condition;
        this.logical = logical;
        this.then = new ArrayList<>();
        this.notTrue = new ArrayList<>();
    }

    public ConditionImpl(Condition condition, String logical, EntityDefinition mainEntity, SecondaryEntity secondaryEntity) {
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
            evaluateAccordingToEntityInstance(context, context.getEntityInstance());
        }

    }

    private void evaluateConditionSecondaryEntityVersion(Context context) {
        if (!secondaryEntitiesInstances.isEmpty()) {
            for (EntityInstance secondEntityInstance : secondaryEntitiesInstances) {
                evaluateAccordingToEntityInstance(context, secondEntityInstance);
            }
        }

    }

    private void evaluateAccordingToEntityInstance(Context context, EntityInstance entityInstance) {
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
        return condition.getArguments();
    }

    @Override
    public Map<String, String> getAdditionalInformation() {
        Map<String, String> info = new LinkedHashMap<>();

        if (condition.getAdditionalInformation() != null) {
            info.putAll(condition.getAdditionalInformation());
        }

        info.put("num of actions in then", String.valueOf(then.size()));
        if (!notTrue.isEmpty()) {
            info.put("num of conditions in else", String.valueOf(notTrue.size()));
        }

        return info;
    }


    @Override
    public boolean isSingleCondition() {
        return condition.isSingleCondition();
    }
}