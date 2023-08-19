package action.impl.condition.impl;

import action.api.AbstractAction;
import action.api.Action;
import action.api.ActionType;
import action.context.api.Context;
import action.impl.condition.Condition;
import action.impl.condition.impl.multiple.MultipleCondition;
import definition.entity.api.EntityDefinition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConditionImpl extends AbstractAction implements Condition , Serializable {
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

    public void addThanList(List<Action> thanList) {
        if (thanList == null) {
            throw new IllegalArgumentException();
        }

        this.than = thanList;
    }

    public void addNotTrueList(List<Action> newNotTrueList) {
        if (newNotTrueList == null) {
            throw new IllegalArgumentException();
        }

        this.notTrue = newNotTrueList;
    }

    @Override
    public void invoke(Context context) {
        if (evaluate(context)) {
            than.forEach(action -> action.invoke(context));
        } else if (notTrue != null){
            notTrue.forEach(action -> action.invoke(context));
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
