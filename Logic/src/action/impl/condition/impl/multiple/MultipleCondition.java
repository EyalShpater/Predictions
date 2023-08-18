package action.impl.condition.impl.multiple;

import action.context.api.Context;
import action.impl.condition.Condition;

public abstract class MultipleCondition implements Condition{

    private final Condition condition1;
    private final Condition condition2;

    public MultipleCondition(Condition condition1, Condition condition2) {
        this.condition1 = condition1;
        this.condition2 = condition2;
    }

    @Override
    public boolean evaluate(Context context) {
        return evaluate(condition1, condition2, context);
    }

    abstract protected boolean evaluate (Condition condition1, Condition condition2, Context context);
}
