package execution.context.api;

import instance.entity.api.EntityInstance;
import instance.property.api.PropertyInstance;

public interface Context {
    EntityInstance getPrimaryEntityInstance();

    void removeEntity(EntityInstance entityInstance);

    PropertyInstance getEnvironmentVariable(String name);
}
