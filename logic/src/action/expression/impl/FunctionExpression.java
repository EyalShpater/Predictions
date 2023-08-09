package action.expression.impl;

import action.expression.api.AbstractExpression;
import instance.entity.api.EntityInstance;

public class FunctionExpression extends AbstractExpression {

    public FunctionExpression(String expression, EntityInstance entityInstance) {
        super(expression, entityInstance);
    }

    @Override
    public AbstractExpression convert() {
        return null;
    }

    @Override
    public Object getValue() {
        return null;
    }
}
