package action.expression.impl;

import action.expression.api.AbstractExpression;
import action.expression.api.Expression;
import action.expression.api.ExpressionTypeConverter;
import action.context.api.Context;
import instance.entity.api.EntityInstance;

import java.io.Serializable;

public class ExpressionFactory implements Expression , Serializable {
    private String expression;
    private EntityInstance primaryInstance;
    private EntityInstance secondaryInstance;

    public ExpressionFactory(String expression, Context context) {
        this.expression = expression;
        this.primaryInstance = context.getPrimaryEntityInstance();
        this.secondaryInstance = context.getSecondaryEntityInstance();
    }

    @Override
    public Object getValue(Context context) {
        ExpressionTypeConverter converter = new ExpressionTypeConverterImpl();
        AbstractExpression expressionInstance = converter.convert(expression, primaryInstance, secondaryInstance);

        return expressionInstance.getValue(context);
    }

    @Override
    public String getExpressionString() {
        return expression;
    }

}
