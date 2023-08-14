package action.helper.function.impl;

import action.helper.function.api.AbstractHelperFunction;
import action.helper.function.api.HelperFunctionType;

import action.context.api.Context;
import instance.property.api.PropertyInstance;

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
            throw new IllegalArgumentException("Unsupported function");
        }
    }
}
