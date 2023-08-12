package action.helper.function.impl;

import action.helper.function.api.AbstractHelperFunction;
import action.helper.function.api.HelperFunctionType;
import action.helper.function.api.HelperFunctionValueGenerator;
import action.helper.function.context.api.HelperFunctionContext;

import java.util.Optional;

public class EnvironmentHelperFunction extends AbstractHelperFunction {

    @Override
    public Object getValueFromHelperFunction(HelperFunctionContext context) {
        return Optional.ofNullable(context.getEnvironmentVariable(context.getTheStringValue()).getValue());
    }
}
