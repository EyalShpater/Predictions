package action.impl.condition.impl.single;

import action.context.api.Context;
import action.expression.impl.ExpressionFactory;
import definition.entity.api.EntityDefinition;

import java.io.Serializable;

public class NotEqual extends SingleCondition implements Serializable {
    public NotEqual(String expression1, String expression2, EntityDefinition toEvaluate) {
        super(toEvaluate, expression1, expression2);
    }

    @Override
    protected boolean evaluate(String expression1, String expression2, Context context) {
        Object value1 = new ExpressionFactory(expression1, context).getValue(context);
        Object value2 = new ExpressionFactory(expression2, context).getValue(context);

        return !value1.equals(value2);
    }

    @Override
    public String getOperationSign() {
        return "!=";
    }
}
