package execution.context.api;

import action.expression.api.Expression;
import instance.entity.api.EntityInstance;
import instance.property.api.PropertyInstance;

public interface Context {
    EntityInstance getPrimaryEntityInstance();

    void removeEntity(EntityInstance entityInstance);

    PropertyInstance getEnvironmentVariable(String name);

    String getExpressionStringValue();

    void setExpressionStringValue(String value);

    Expression getExpression();

    void setExpression(Expression expression);

}
