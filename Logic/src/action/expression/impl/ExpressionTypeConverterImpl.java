package action.expression.impl;

import action.expression.api.AbstractExpression;
import action.expression.api.ExpressionTypeConverter;
import action.helper.function.api.HelperFunctionType;
import instance.entity.api.EntityInstance;

import java.io.Serializable;

public class ExpressionTypeConverterImpl implements ExpressionTypeConverter , Serializable {
    private static final char HELPER_FUNCTION_TOKEN = '(';
    private static final int NOT_FOUND = -1;

    @Override
    public AbstractExpression convert(String expression, EntityInstance primaryEntity, EntityInstance secondaryInstances) {
        AbstractExpression expressionInstance;
        expression = expression.trim();

        if (isHelperFunction(expression)) {
            expressionInstance = new FunctionExpression(expression, primaryEntity, secondaryInstances);
        } else if (isProperty(expression, primaryEntity)) {
            expressionInstance = new PropertyExpression(expression, primaryEntity);
        } else {
            expressionInstance = new ValueExpression(expression, primaryEntity);
        }

        return expressionInstance;
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

    private boolean isProperty(String expression, EntityInstance entityInstance) {
        return entityInstance.getPropertyByName(expression) != null;
    }
}