package action.impl.condition.impl.single;

import action.context.api.Context;
import action.expression.api.Expression;
import action.expression.impl.ExpressionFactory;

public class BiggerThan extends SingleCondition{
    public BiggerThan(Context context, String expression1, String expression2) {
        super(expression1, expression2);
    }

    @Override
    public String getOperationSign() {
        return "bt";
    }

    @Override
    protected boolean evaluate(String expression1, String expression2, Context context) {
        Object value1 = new ExpressionFactory(expression1, context.getEntityInstance()).getValue(context);
        Object value2 = new ExpressionFactory(expression2, context.getEntityInstance()).getValue(context);

        if(value1 instanceof Number && value2 instanceof Number){
            return ((Number)value1).doubleValue() > ((Number)value2).doubleValue();
        }

        throw new IllegalArgumentException("Bigger than is defined for numeric types only");
    }
}
