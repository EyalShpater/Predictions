package action.helper.function.impl;

import action.context.api.Context;
import action.helper.function.api.HelperFunction;
import definition.entity.api.EntityDefinition;
import instance.entity.api.EntityInstance;

public class Evaluate implements HelperFunction {

    Context context;
    String propertyName;
    String entityName;

    public Evaluate(String entityName, String propertyName, Context context) {
        this.context = context;
        this.propertyName = propertyName;
        this.entityName = entityName;
    }

    @Override
    public Object getValue() {
        if (context.isEntityRelatedToAction(entityName)) {
            return context.getPropertyOfEntity(entityName, propertyName);
        } else {
            throw new IllegalArgumentException("Somthing went wrong in evaluate function");
        }
    }
}
