package action.expression.api;

import instance.entity.api.EntityInstance;

public interface ExpressionTypeConverter {
    AbstractExpression convert(String expression, EntityInstance primaryEntity, EntityInstance secondaryInstance);
}
