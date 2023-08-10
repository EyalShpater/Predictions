package instance.entity.manager.impl;

import definition.entity.api.EntityDefinition;
import instance.entity.api.EntityInstance;
import instance.entity.impl.EntityInstanceImpl;
import instance.entity.manager.api.EntityInstanceManager;

import java.util.*;

public class EntityInstanceManagerImpl implements EntityInstanceManager {
    private int id;
    private List<EntityInstance> instances;

    public EntityInstanceManagerImpl() {
        instances = new ArrayList<>();
        id = 1;
    }

    @Override
    public void create(EntityDefinition entityDefinition) {
        instances.add(new EntityInstanceImpl(entityDefinition, id));
        id++;
    }

    @Override
    public List<EntityInstance> getInstances() {
        return instances;
    }
}
