package action.helper.function.impl;

import action.expression.impl.FunctionExpression;
import action.expression.impl.PropertyExpression;
import action.expression.impl.ValueExpression;
import action.helper.function.api.AbstractHelperFunction;
import action.helper.function.api.HelperFunctionType;
import action.helper.function.api.HelperFunctionValueGenerator;
import execution.context.api.Context;

public class HelperFunctionFactory implements HelperFunctionValueGenerator {
    @Override
    public Object getValueFromHelperFunction(Context context) {
        AbstractHelperFunction helperFunction = convert(context);
        return helperFunction.getValueFromHelperFunction(context);
    }

    public AbstractHelperFunction convert(Context context) {
        //TODO: First thing in the morning to change the setExpression
        String theStringValue = context.getExpression().getExpressionString();
        if (theStringValue.startsWith("environment")) {
            String extractedValue = theStringValue.substring(12, theStringValue.length() - 1);
            context.setExpressionStringValue(extractedValue);
            return new EnvironmentHelperFunction(HelperFunctionType.ENVIRONMENT);
        } else if (theStringValue.startsWith("random")) {
            String extractedValue = theStringValue.substring(7, theStringValue.length() - 1);
            context.setExpressionStringValue(extractedValue);
            return new RandomHelperFunction(HelperFunctionType.RANDOM);
        } else {
            throw new IllegalArgumentException("Unsported function");
        }
    }
}
