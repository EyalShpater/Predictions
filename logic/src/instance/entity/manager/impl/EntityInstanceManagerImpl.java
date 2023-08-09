package instance.entity.manager.impl;

import definition.entity.api.EntityInstance;
import instance.entity.impl.EntityInstanceImpl;
import instance.entity.manager.api.EntityInstanceManager;

import java.util.ArrayList;
import java.util.List;

public class EntityInstanceManagerImpl implements EntityInstanceManager {
    List<instance.entity.api.EntityInstance> instances;

    public EntityInstanceManagerImpl() {
        instances = new ArrayList<>();
    }

    @Override
    public void create(EntityInstance entityDefinition) {
        instances.add(new EntityInstanceImpl(entityDefinition));
    }

    @Override
    public List<instance.entity.api.EntityInstance> getInstances() {
        return instances;
    }
}
