package action.context.api;

import action.expression.api.Expression;
import instance.entity.api.EntityInstance;
import instance.property.api.PropertyInstance;

import java.util.List;

public interface Context {
    EntityInstance getEntityInstance();

    PropertyInstance getEnvironmentVariable(String name);

    void removeEntity(EntityInstance entityInstance);

    List<EntityInstance> getInstancesWithName(String secondEntityName);

    Context duplicateContextWithEntityInstance(EntityInstance newEntityInstance);
}
