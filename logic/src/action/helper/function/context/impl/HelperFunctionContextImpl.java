package action.helper.function.context.impl;

import action.helper.function.context.api.HelperFunctionContext;
import instance.enviornment.api.ActiveEnvironment;
import instance.property.api.PropertyInstance;

public class HelperFunctionContextImpl implements HelperFunctionContext {

    private ActiveEnvironment activeEnvironment;

    private String theValue;

    public HelperFunctionContextImpl(ActiveEnvironment activeEnvironment, String theValue) {
        this.activeEnvironment = activeEnvironment;
        this.theValue = theValue;
    }

    @Override
    public PropertyInstance getEnvironmentVariable(String name) {
        return activeEnvironment.getPropertyByName(name);
    }

    @Override
    public String getTheStringValue() {
        return theValue;
    }
}
