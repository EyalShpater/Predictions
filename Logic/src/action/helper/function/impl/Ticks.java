package action.helper.function.impl;

import action.context.api.Context;
import action.helper.function.api.HelperFunction;

public class Ticks implements HelperFunction {

    Context context;
    String entityName;
    String propertyName;

    public Ticks(String entityName, String propertyName, Context context) {
        this.context = context;
        this.entityName = entityName;
        this.propertyName = propertyName;
    }

    @Override
    public Object getValue() {
        // TODO: add ticks field to the PropertyInstance
        return null;
    }
}
