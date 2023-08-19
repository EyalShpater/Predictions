package action.expression.impl;

import action.expression.api.AbstractExpression;
import action.expression.api.Expression;
import action.expression.api.ExpressionTypeConverter;
import action.context.api.Context;
import instance.entity.api.EntityInstance;

import java.io.Serializable;

public class ExpressionFactory implements Expression , Serializable {
    private String expression;
    private EntityInstance entityInstance;

    public ExpressionFactory(String expression, EntityInstance entityInstance) {
        this.expression = expression;
        this.entityInstance = entityInstance;
    }

    @Override
    public Object getValue(Context context) {
        ExpressionTypeConverter converter = new ExpressionTypeConverterImpl();
        AbstractExpression expressionInstance = converter.convert(expression, entityInstance);

        return expressionInstance.getValue(context);
    }

    @Override
    public String getExpressionString() {
        return expression;
    }

}
