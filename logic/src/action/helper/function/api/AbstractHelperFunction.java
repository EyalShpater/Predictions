package action.helper.function.api;

import action.context.api.Context;

public abstract class AbstractHelperFunction implements HelperFunctionValueGenerator {

    HelperFunctionType type;

    public AbstractHelperFunction(HelperFunctionType type) {
        this.type = type;
    }

    @Override
    public abstract Object getValueFromHelperFunction(Context context);
}
