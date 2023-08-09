package action.expression.impl;

import action.expression.api.AbstractExpression;
import instance.entity.api.EntityInstance;


public class PropertyExpression extends AbstractExpression {
    public PropertyExpression(String byExpression, EntityInstance entityInstance) {
        super(byExpression, entityInstance);
    }

    @Override
    public AbstractExpression convert() {
        return new PropertyExpression(this.expression, this.entityInstance);
    }

    @Override
    public Object getValue() {
        return entityInstance.getPropertyByName(expression).getValue();
    }
}
