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
        try {
            int tickValue = context.getTickThisPropertyWasntChanged(propertyName);
            return tickValue;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Inside tick function " + e.getMessage());
        }
    }
}
