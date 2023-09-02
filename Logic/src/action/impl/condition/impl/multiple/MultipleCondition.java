package action.impl.condition.impl.multiple;

import action.context.api.Context;
import action.impl.condition.Condition;
import action.impl.condition.impl.single.SingleCondition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class MultipleCondition implements Condition , Serializable {

    protected List<Condition> conditions;

    public MultipleCondition() {
        conditions = new ArrayList<>();
    }

    @Override
    public boolean evaluate(Context context) {
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
        arguments.put("num of conditions", String.valueOf(conditions.size()));

        return arguments;
    }

    abstract protected boolean evaluate(List<Condition> conditions, Context context);

    public Condition isSingleCondition() {
        return conditions.size() <= 1 ?
                conditions.get(0) :
                null;
    }
}
