package action.impl.condition.impl.multiple;

import action.context.api.Context;
import action.impl.condition.Condition;
import definition.entity.api.EntityDefinition;
import instance.entity.api.EntityInstance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class MultipleCondition implements Condition, Serializable {

    protected List<Condition> conditions;

    public MultipleCondition() {
        conditions = new ArrayList<>();
    }

    @Override
    public Boolean evaluate(Context context) {
        return evaluate(conditions, context);
    }

    public void addCondition(Condition condition) {
        if (condition == null) {
            throw new NullPointerException();
        }
        conditions.add(condition);
    }

    @Override
    public Map<String, String> getArguments() {
        Map<String, String> arguments = new LinkedHashMap<>();

        arguments.put("logical", getOperationSign());

        return arguments;
    }

    @Override
    public Map<String, String> getAdditionalInformation() {
        Map<String, String> info = new LinkedHashMap<>();

        info.put("num of nested conditions", String.valueOf(conditions.size()));

        return info;
    }

    @Override
    public EntityDefinition getPrimaryEntity() {
        return null;
    }

    abstract protected boolean evaluate(List<Condition> conditions, Context context);

    private boolean isConditionPrimaryEntityEqualsContextSecondaryEntity(Condition condition, Context context) {
        boolean isEqual = false;

        if (condition.isSingleCondition() && context.getSecondaryEntityInstance() != null) {
            isEqual = condition
                    .getPrimaryEntity()
                    .getName()
                    .equals(context.getPrimaryEntityInstance().getName());
        }

        return isEqual;
    }

    protected Context checkAndReplaceContextByConditionPrimaryInstance(Condition condition, Context context) {
        return isConditionPrimaryEntityEqualsContextSecondaryEntity(condition, context) ?
                context.duplicateAndSwapPrimaryInstanceAndSecondary() :
                context;
    }

    public boolean isSingleCondition() {
        return false;
    }
}
