package action.helper.function.context.api;

import instance.property.api.PropertyInstance;

public interface HelperFunctionContext {

    PropertyInstance getEnvironmentVariable(String name);

    String getTheStringValue();
}
