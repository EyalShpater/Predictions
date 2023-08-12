package action.helper.function.impl;

import action.helper.function.api.AbstractHelperFunction;
import action.helper.function.api.HelperFunctionType;
import action.helper.function.api.HelperFunctionValueGenerator;

import execution.context.api.Context;
import instance.property.api.PropertyInstance;

import java.util.Optional;

public class EnvironmentHelperFunction extends AbstractHelperFunction {

    public EnvironmentHelperFunction(HelperFunctionType type) {
        super(type);
    }

    @Override
    public Object getValueFromHelperFunction(Context context) {
        PropertyInstance environmentVariable = context.getEnvironmentVariable(context.getExpressionStringValue());
        try {
            return environmentVariable.getValue();
        } catch (Exception e) {
            throw new IllegalArgumentException("Unsported function");
        }
    }
}
