package action.expression.impl;

import action.expression.api.AbstractExpression;
import action.expression.api.ExpressionType;
import action.expression.api.ExpressionTypeConverter;
import action.helper.function.api.HelperFunctionType;
import definition.entity.api.EntityDefinition;

public class ExpressionTypeConverterImpl implements ExpressionTypeConverter {
    private final char HELPER_FUNCTION_TOKEN = '(';
    private final int NOT_FOUND = -1;

    @Override
    public AbstractExpression convert(String expression, EntityDefinition entityDefinition) {
        AbstractExpression type;
        expression = expression.trim();

        if (isHelperFunction(expression)) {
            type = new FunctionExpression();//ExpressionType.FUNCTION_EXP;
        } else if (isProperty(expression, entityDefinition)) {
            type = new PropertyExpression();
        } else {//return to the logic of checking the OTHER_EXP context
            type = new ValueExpression();
        }

        return type;
    }

    private boolean isHelperFunction(String expression) {
        int indexOfToken = expression.indexOf(HELPER_FUNCTION_TOKEN);
        String subExpression = null;

        if (indexOfToken != NOT_FOUND) {
            subExpression = expression.substring(0, indexOfToken);
        }

        return isHelperFunctionExist(subExpression);
    }

    private boolean isHelperFunctionExist(String functionName) {
        boolean isExist = false;

        for (HelperFunctionType type : HelperFunctionType.values()) {
            if (type.name().toLowerCase().equals(functionName)) {
                isExist = true;
            }
        }

        return isExist;
    }

    private boolean isProperty(String expression, EntityDefinition entityDefinition) {
        return entityDefinition.getPropertyByName(expression) != null;
    }
}