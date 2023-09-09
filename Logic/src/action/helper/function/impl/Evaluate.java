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
        //TODO : think about creating a new context type of object that contains Context and secondaryEntity
        return null;
    }
}
