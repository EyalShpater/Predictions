package action.helper.function.api;

import action.helper.function.context.api.HelperFunctionContext;

public interface HelperFunctionValueGenerator {

    Object getValueFromHelperFunction(HelperFunctionContext context);
}
