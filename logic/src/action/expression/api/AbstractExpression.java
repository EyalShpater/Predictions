package action.expression.api;

import action.expression.impl.ExpressionTypeConverterImpl;
import definition.entity.api.EntityDefinition;
import instance.entity.api.EntityInstance;

public abstract class AbstractExpression implements Expression {
    private String byExpression;
    private EntityDefinition entityDefinition;

    public AbstractExpression(String byExpression, EntityDefinition entityDefinition) {
        this.byExpression = byExpression;
        this.entityDefinition = entityDefinition;
    }

    public abstract AbstractExpression convert();
}
