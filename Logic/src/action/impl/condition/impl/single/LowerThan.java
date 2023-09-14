package action.impl.condition.impl.single;

import action.context.api.Context;
import action.expression.impl.ExpressionFactory;
import definition.entity.api.EntityDefinition;

import java.io.Serializable;

public class LowerThan extends SingleCondition implements Serializable {
    public LowerThan(String expression1, String expression2, EntityDefinition toEvaluate) {
        super(toEvaluate, expression1, expression2);
    }

    @Override
    public String getOperationSign() {
        return "lt";
    }

    @Override
    protected boolean evaluate(String expression1, String expression2, Context context) {
        Object value1 = new ExpressionFactory(expression1, context.getPrimaryEntityInstance()).getValue(context);
        Object value2 = new ExpressionFactory(expression2, context.getPrimaryEntityInstance()).getValue(context);

        if(value1 instanceof Number && value2 instanceof Number){
            return ((Number)value1).doubleValue() < ((Number)value2).doubleValue();
        }

        throw new IllegalArgumentException("Lower than is defined for numeric types only");
    }
}
