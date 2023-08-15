package action.impl.condition.impl.single;

import action.context.api.Context;
import action.expression.api.Expression;
import action.expression.impl.ExpressionFactory;

public class Equal extends SingleCondition{
    public Equal(Context context, String expression1, String expression2) {
        super(context, expression1, expression2);
    }

    @Override
    protected boolean evaluate(Expression expression1, Expression expression2) {
        Object value1 = expression1.getValue(context);
        Object value2 = expression2.getValue(context);

        return value1.equals(value2);
    }

    @Override
    public String getOperationSign() {
        return "=";
    }
}
