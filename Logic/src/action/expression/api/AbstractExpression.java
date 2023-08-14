package action.expression.api;

import instance.entity.api.EntityInstance;

public abstract class AbstractExpression implements Expression {
    protected String expression;
    protected EntityInstance entityInstance;

    public AbstractExpression(String expression, EntityInstance entityInstance) {
        this.expression = expression;
        this.entityInstance = entityInstance;
    }

    public abstract AbstractExpression convert();
}
