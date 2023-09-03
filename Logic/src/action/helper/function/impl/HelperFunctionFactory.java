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
            case "environment": return new Environment(variable, context);
            case "random": return new RandomHelper(variable, context);
            default: throw new IllegalArgumentException("Function not found");
        }
    }
}
