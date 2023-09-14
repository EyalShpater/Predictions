package action.expression.impl;

import action.expression.api.AbstractExpression;
import action.context.api.Context;
import instance.entity.api.EntityInstance;

import java.io.Serializable;


public class PropertyExpression extends AbstractExpression implements  Serializable {
    public PropertyExpression(String byExpression, EntityInstance entityInstance) {
        super(byExpression, entityInstance);
    }

    @Override
    public AbstractExpression convert() {
        return new PropertyExpression(this.expression, this.primaryEntity);
    }

    @Override
    public Object getValue(Context context) {
        return primaryEntity.getPropertyByName(expression).getValue();
    }

    @Override
    public String getExpressionString() {
        return expression;
    }
}
