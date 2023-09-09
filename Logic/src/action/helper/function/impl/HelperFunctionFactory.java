package action.helper.function.impl;

import action.context.api.Context;
import action.helper.function.api.HelperFunction;

import java.io.Serializable;

public class HelperFunctionFactory implements Serializable {
    private final static String IGNORE_SIGNS = "\\(";
    private final static int MINIMUM_NUM_OF_ELEMENTS_IN_FUNCTION_DECLARATION = 2;

    public HelperFunction convert(Context context, String expression) {
        String[] parts = expression.split(IGNORE_SIGNS);

        if (parts.length < MINIMUM_NUM_OF_ELEMENTS_IN_FUNCTION_DECLARATION) {
            throw new IllegalArgumentException("Illegal function!");
        }

        String functionName = parts[0];
        String variable = parts[1].substring(0, parts[1].length() - 1);

        switch (functionName) {
            case "environment":
                return new Environment(variable, context);
            case "random":
                return new RandomHelper(variable, context);
            case "ticks":
                return tokenizeTicksExpression(variable, context);
            case "evaluate":
                return tokenizeEvaluateExpression(variable, context);
            case "percent":
                return tokenizePercentExpression(variable, context);
            default:
                throw new IllegalArgumentException("Function not found");
        }
    }

    private HelperFunction tokenizePercentExpression(String variable, Context context) {
        // TODO: implement
        return null;
    }

    private HelperFunction tokenizeEvaluateExpression(String variable, Context context) {
        String[] tokens = variable.split("\\.");

        String entityName = tokens[0];
        String propertyName = tokens[1];

        return new Evaluate(entityName, propertyName, context);
    }

    private HelperFunction tokenizeTicksExpression(String variable, Context context) {

        String[] tokens = variable.split("\\.");

        String entityName = tokens[0];
        String propertyName = tokens[1];

        return new Ticks(entityName, propertyName, context);
    }
}
