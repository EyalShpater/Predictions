package action.helper.function.impl;

import action.expression.impl.FunctionExpression;
import action.expression.impl.PropertyExpression;
import action.expression.impl.ValueExpression;
import action.helper.function.api.AbstractHelperFunction;
import action.helper.function.api.HelperFunctionType;
import action.helper.function.api.HelperFunctionValueGenerator;
import action.helper.function.context.api.HelperFunctionContext;

public class HelperFunctionFactory implements HelperFunctionValueGenerator {
    @Override
    public Object getValueFromHelperFunction(HelperFunctionContext context) {
        AbstractHelperFunction helperFunction = convert(context.getTheStringValue());
        return helperFunction.getValueFromHelperFunction(context);
    }

    public AbstractHelperFunction convert(String theStringValue) {
        if (theStringValue.startsWith("environment")) {
            return new EnvironmentHelperFunction();
        } else if (theStringValue.startsWith("random")) {
            return new RandomHelperFunction();
        } else {
            throw new IllegalArgumentException("Unsported function");
        }
    }
}
