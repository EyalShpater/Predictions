package action.impl.condition.impl.multiple;

import action.context.api.Context;
import action.impl.condition.Condition;

import java.util.List;

public class Or extends MultipleCondition{

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
