package action.impl.condition.impl.multiple;

import action.context.api.Context;
import action.impl.condition.Condition;

import java.io.Serializable;
import java.util.List;

public class Or extends MultipleCondition implements Serializable {

    @Override
    public String getOperationSign() {
        return "or";
    }

    @Override
    protected boolean evaluate(List<Condition> conditions, Context context) {
        boolean result = false;

        for (Condition condition : conditions) {
            Context relevantContext = checkAndReplaceContextByConditionPrimaryInstance(condition, context);
            Boolean evaluateResult = condition.evaluate(relevantContext);

            if (evaluateResult != null) {
                result = result || evaluateResult;
            }
        }

        return result;
    }
}
