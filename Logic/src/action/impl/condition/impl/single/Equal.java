package action.impl.condition.impl.single;

import action.context.api.Context;
import action.expression.impl.ExpressionFactory;
import definition.entity.api.EntityDefinition;

import java.io.Serializable;

public class Equal extends SingleCondition implements Serializable {
    public Equal(String expression1, String expression2, EntityDefinition contextConditionEntity) {
        super(expression1, expression2, contextConditionEntity);
    }

    @Override
    protected boolean evaluate(String expression1, String expression2, Context context) {
        Object value1 = new ExpressionFactory(expression1, context.getPrimaryEntityInstance()).getValue(context);
        Object value2 = new ExpressionFactory(expression2, context.getPrimaryEntityInstance()).getValue(context);

        return value1.equals(value2);
    }

    @Override
    public String getOperationSign() {
        return "=";
    }
}
