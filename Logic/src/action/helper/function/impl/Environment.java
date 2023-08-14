package action.helper.function.impl;

import action.helper.function.api.HelperFunction;

import action.context.api.Context;
import instance.property.api.PropertyInstance;

public class Environment implements HelperFunction {
    String propertyName;
    Context context;

    public Environment(String propertyName, Context context) {
        this.propertyName = propertyName;
        this.context = context;
    }

    @Override
    public Object getValue() {
        PropertyInstance environmentVariable = context.getEnvironmentVariable(propertyName);

        if (environmentVariable == null) {
            throw new IllegalArgumentException("Unsupported function");
        }

        return environmentVariable.getValue();
    }
}
