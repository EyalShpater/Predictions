package action.expression.api;

import instance.entity.api.EntityInstance;

import java.io.Serializable;

public abstract class AbstractExpression implements Expression , Serializable {
    protected String expression;
    protected EntityInstance entityInstance;

    public AbstractExpression(String expression, EntityInstance entityInstance) {
        this.expression = expression;
        this.entityInstance = entityInstance;
    }

    public abstract AbstractExpression convert();
}
