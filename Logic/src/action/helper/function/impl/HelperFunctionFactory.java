package action.helper.function.impl;

import action.context.api.Context;
import action.helper.function.api.HelperFunction;

public class HelperFunctionFactory {
    private final static String IGNORE_SIGNS = "\\(";
    private final static int MINIMUM_NUM_OF_ELEMENTS_IN_FUNCTION_DECLARATION = 2;

//    public HelperFunctionValueGenerator convert(Context context) {
//        //TODO: First thing in the morning to change the setExpression
//        String theStringValue = context.getExpression().getExpressionString();
//        if (theStringValue.startsWith("environment")) {
//            String extractedValue = theStringValue.substring(12, theStringValue.length() - 1);
//            context.setExpressionStringValue(extractedValue);
//            return new Environment(HelperFunctionType.ENVIRONMENT);
//        } else if (theStringValue.startsWith("random")) {
//            String extractedValue = theStringValue.substring(7, theStringValue.length() - 1);
//            context.setExpressionStringValue(extractedValue);
//            return new Random(HelperFunctionType.RANDOM);
//        } else {
//            throw new IllegalArgumentException("Unsported function");
//        }
//    }

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
