package instance.entity.manager.impl;

import definition.entity.api.EntityDefinition;
import instance.entity.api.EntityInstance;
import instance.entity.impl.EntityInstanceImpl;
import instance.entity.manager.api.EntityInstanceManager;

import java.util.ArrayList;
import java.util.List;

public class EntityInstanceManagerImpl implements EntityInstanceManager {
    List<EntityInstance> instances;

    public EntityInstanceManagerImpl() {
        instances = new ArrayList<>();
    }

    @Override
    public void create(EntityDefinition entityDefinition) {
        instances.add(new EntityInstanceImpl(entityDefinition));
    }

    @Override
    public List<EntityInstance> getInstances() {
        return instances;
    }
}
