package action.expression.api;

import definition.entity.api.EntityDefinition;

public interface ExpressionTypeConverter {
    AbstractExpression convert(String byExpression, EntityDefinition entityDefinition);
}
