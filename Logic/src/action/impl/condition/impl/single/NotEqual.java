package action.impl.condition.impl.single;

import action.context.api.Context;
import action.expression.api.Expression;
import action.expression.impl.ExpressionFactory;

public class NotEqual extends SingleCondition{
    public NotEqual(String expression1, String expression2) {
        super(expression1, expression2);
    }

    @Override
    protected boolean evaluate(String expression1, String expression2, Context context) {
        Object value1 = new ExpressionFactory(expression1, context.getEntityInstance()).getValue(context);
        Object value2 = new ExpressionFactory(expression2, context.getEntityInstance()).getValue(context);

        return !value1.equals(value2);
    }

    @Override
    public String getOperationSign() {
        return "!=";
    }
}
