package action.impl.condition.impl.multiple;

import action.context.api.Context;
import action.impl.condition.Condition;

public class Or extends MultipleCondition{
    public Or(Condition condition1, Condition condition2) {
        super(condition1, condition2);
    }

    @Override
    public String getOperationSign() {
        return "or";
    }

    @Override
    protected boolean evaluate(Condition condition1, Condition condition2, Context context) {
        return condition1.evaluate(context) || condition2.evaluate(context);
    }
}
