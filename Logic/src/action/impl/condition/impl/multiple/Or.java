package action.impl.condition.impl.multiple;

import action.context.api.Context;
import action.impl.condition.Condition;
import instance.entity.api.EntityInstance;

import java.io.Serializable;
import java.util.List;

public class Or extends MultipleCondition implements Serializable {

    @Override
    public String getOperationSign() {
        return "or";
    }

    @Override
    protected boolean evaluate(List<Condition> conditions, Context context, EntityInstance secondEntityInstance) {
        boolean result = false;

        for (Condition condition : conditions) {
            Boolean evaluateResult = condition.evaluate(context, secondEntityInstance);
            if (evaluateResult != null) {
                result = result || condition.evaluate(context, secondEntityInstance);
            }
        }

        return result;
    }
}
