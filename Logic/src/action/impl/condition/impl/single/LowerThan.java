package action.impl.condition.impl.single;

import action.context.api.Context;
import action.expression.api.Expression;

public class LowerThan extends SingleCondition{
    public LowerThan(Context context, String expression1, String expression2) {
        super(context, expression1, expression2);
    }

    @Override
    public String getOperationSign() {
        return "lt";
    }

    @Override
    protected boolean evaluate(Expression expression1, Expression expression2) {
        Object value1 = expression1.getValue(context);
        Object value2 = expression2.getValue(context);

        if(value1 instanceof Number && value2 instanceof Number){
            return ((Number)value1).doubleValue() < ((Number)value2).doubleValue();
        }

        throw new IllegalArgumentException("Lower than is defined for numeric types only");
    }
}