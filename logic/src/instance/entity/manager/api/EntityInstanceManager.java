package instance.entity.manager.api;

import definition.entity.api.EntityInstance;

import java.util.List;

public interface EntityInstanceManager {
    void create(EntityInstance entityDefinition);

    List<instance.entity.api.EntityInstance> getInstances();
}
