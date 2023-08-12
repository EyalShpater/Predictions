package action.helper.function.api;

import action.helper.function.context.api.HelperFunctionContext;

public abstract class AbstractHelperFunction implements HelperFunctionValueGenerator {


    @Override
    public abstract Object getValueFromHelperFunction(HelperFunctionContext context);
}
