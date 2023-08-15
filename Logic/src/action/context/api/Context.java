package action.context.api;

import action.expression.api.Expression;
import instance.entity.api.EntityInstance;
import instance.property.api.PropertyInstance;

public interface Context {
    EntityInstance getEntityInstance();
    PropertyInstance getEnvironmentVariable(String name);
    void removeEntity(EntityInstance entityInstance);
}
