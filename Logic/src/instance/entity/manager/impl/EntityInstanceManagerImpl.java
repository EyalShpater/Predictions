package instance.entity.manager.impl;

import definition.entity.api.EntityDefinition;
import instance.entity.api.EntityInstance;
import instance.entity.impl.EntityInstanceImpl;
import instance.entity.manager.api.EntityInstanceManager;

import java.util.*;
import java.util.stream.Collectors;

public class EntityInstanceManagerImpl implements EntityInstanceManager {
    private int id;
    private Map<Integer, EntityInstance> instances;

    public EntityInstanceManagerImpl() {
        instances = new HashMap<>();
        id = 1;
    }

    @Override
    public void create(EntityDefinition entityDefinition) {
        instances.put(id, new EntityInstanceImpl(entityDefinition, id));
        id++;
    }

    @Override
    public List<EntityInstance> getInstances() {
        return new ArrayList<EntityInstance>(instances.values());
    }

    @Override
    public void killEntity(int idToKill) {
        EntityInstance instanceToKill = instances.get(idToKill);

        if (instanceToKill == null) {
            throw new IllegalArgumentException("ID is not valid");
        }

        instanceToKill.kill();
    }
}
