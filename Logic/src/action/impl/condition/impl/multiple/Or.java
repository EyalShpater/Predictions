package action.impl.condition.impl.multiple;

import action.context.api.Context;
import action.impl.condition.Condition;

import java.util.List;

public class Or extends MultipleCondition{
    public Or(Condition condition1, Condition condition2) {
        super(condition1, condition2);
    }

    @Override
    public String getOperationSign() {
        return "or";
    }

    @Override
    protected boolean evaluate(List<Condition> conditions, Context context) {
        boolean result = false;

        for (Condition condition : conditions) {
            result = result || condition.evaluate(context);
        }

        return result;
    }
}
