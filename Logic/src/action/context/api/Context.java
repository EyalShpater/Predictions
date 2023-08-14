package action.context.api;

import action.expression.api.Expression;
import instance.entity.api.EntityInstance;
import instance.property.api.PropertyInstance;

public interface Context {
    EntityInstance getPrimaryEntityInstance();
    PropertyInstance getEnvironmentVariable(String name);
    String getExpressionStringValue();
    Expression getExpression();
}
