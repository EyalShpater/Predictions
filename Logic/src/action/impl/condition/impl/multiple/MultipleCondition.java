package action.impl.condition.impl.multiple;

import action.context.api.Context;
import action.impl.condition.Condition;

import java.util.ArrayList;
import java.util.List;

public abstract class MultipleCondition implements Condition{

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

    abstract protected boolean evaluate(List<Condition> conditions, Context context);
}
