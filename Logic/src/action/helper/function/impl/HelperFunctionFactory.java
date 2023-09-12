package action.helper.function.impl;

import action.context.api.Context;
import action.helper.function.api.HelperFunction;

import java.io.Serializable;

public class HelperFunctionFactory implements Serializable {
    private final static String IGNORE_SIGNS = "\\(";
    private final static int MINIMUM_NUM_OF_ELEMENTS_IN_FUNCTION_DECLARATION = 2;
    private final static int NOT_FOUND = -1;

    public HelperFunction convert(Context context, String expression) {
        String[] nonPercentArgs = tokenizeNonPercentArgs(expression);
        String functionName = nonPercentArgs[0];
        String variable = nonPercentArgs[1].substring(0, nonPercentArgs[1].length() - 1);

        switch (functionName) {
            case "environment":
                return tokenizeEnvExpression(variable, context);
            case "random":
                return tokenizeRandomExpression(variable, context);
            case "ticks":
                return tokenizeTicksExpression(variable, context);
            case "evaluate":
                return tokenizeEvaluateExpression(variable, context);
            case "percent":
                return tokenizePercentExpression(expression, context);
            default:
                throw new IllegalArgumentException("Function not found");
        }
    }


    private HelperFunction tokenizeEnvExpression(String variable, Context context) {
        return new Environment(variable, context);
    }

    private HelperFunction tokenizeRandomExpression(String variable, Context context) {
        return new RandomHelper(variable, context);
    }

    private String[] tokenizeNonPercentArgs(String expression) {
        String[] parts = expression.split(IGNORE_SIGNS);

        if (parts.length < MINIMUM_NUM_OF_ELEMENTS_IN_FUNCTION_DECLARATION) {
            throw new IllegalArgumentException("Illegal function!");
        }
        return parts;
    }

    private HelperFunction tokenizePercentExpression(String expression, Context context) {

        String[] percentExpressionArguments = null;
        int indexOfToken = expression.indexOf("(");
        String functionName = null;
        String functionArgument = null;
        if (indexOfToken != NOT_FOUND) {
            functionName = expression.substring(0, indexOfToken);
            int closingParenthesisIndex = expression.lastIndexOf(")");
            if (closingParenthesisIndex != NOT_FOUND && closingParenthesisIndex > indexOfToken) {
                functionArgument = expression.substring(indexOfToken + 1, closingParenthesisIndex).trim();
                percentExpressionArguments = tokenizePercentForArguments(functionArgument);
            }
        }
        return new Percent(context, percentExpressionArguments[0], percentExpressionArguments[1]);
    }

    private String[] tokenizePercentForArguments(String functionArgument) {
        int indexOfToken = functionArgument.indexOf(',');
        String expression1 = "";
        String expression2 = "";

        if (indexOfToken != NOT_FOUND) {
            expression1 = functionArgument.substring(0, indexOfToken);
            expression2 = functionArgument.substring(indexOfToken + 1, functionArgument.length());
        }
        String[] tokens = {expression1, expression2};
        return tokens;
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
