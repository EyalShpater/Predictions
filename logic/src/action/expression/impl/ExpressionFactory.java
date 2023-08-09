package action.expression.impl;

import action.expression.api.AbstractExpression;
import action.expression.api.Expression;
import action.expression.api.ExpressionTypeConverter;
import instance.entity.api.EntityInstance;

public class ExpressionFactory implements Expression {
    private String expression;
    private EntityInstance entityInstance;

    public ExpressionFactory(String expression, EntityInstance entityInstance) {
        this.expression = expression;
        this.entityInstance = entityInstance;
    }

    @Override
    public Object getValue() {
        ExpressionTypeConverter converter = new ExpressionTypeConverterImpl();
        AbstractExpression expressionInstance = converter.convert(expression, entityInstance);

        return expressionInstance.getValue();
    }
}