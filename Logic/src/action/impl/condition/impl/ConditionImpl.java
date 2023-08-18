package action.impl.condition.impl;

import action.api.AbstractAction;
import action.api.Action;
import action.api.ActionType;
import action.context.api.Context;
import action.impl.condition.Condition;
import action.impl.condition.impl.multiple.MultipleCondition;
import definition.entity.api.EntityDefinition;

import java.util.ArrayList;
import java.util.List;

public class ConditionImpl extends AbstractAction implements Condition {
    private final MultipleCondition condition;
    private final String logical;
    List<Action> than;
    List<Action> notTrue;

    public ConditionImpl(MultipleCondition condition, String logical, EntityDefinition entity) {
        super(entity, ActionType.CONDITION);
        this.condition = condition;
        this.logical = logical;
        this.than = new ArrayList<>();
        this.notTrue = new ArrayList<>();
    }

    public void addActionToThan(Action newAction) {
        if (newAction == null) {
            throw new IllegalArgumentException();
        }

        than.add(newAction);
    }

    public void addActionToNotTrue(Action newAction) {
        if (newAction == null) {
            throw new IllegalArgumentException();
        }

        than.add(newAction);
    }

    @Override
    public void invoke(Context context) {
        if (evaluate(context)) {
            than.forEach(action -> invoke(context));
        } else if (notTrue != null){
            notTrue.forEach(action -> invoke(context));
        }
    }

    @Override
    public boolean evaluate(Context context) {
        return condition.evaluate(context);
    }

    @Override
    public String getOperationSign() {
        return null;
    }

}
