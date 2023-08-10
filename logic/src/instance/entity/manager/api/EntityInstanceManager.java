package instance.entity.manager.api;

import definition.entity.api.EntityDefinition;

import java.util.List;

public interface EntityInstanceManager {
    void create(EntityDefinition entityDefinition);

    List<instance.entity.api.EntityInstance> getInstances();
}
