package instance.entity.manager.impl;

import definition.entity.api.EntityDefinition;
import grid.api.Location;
import grid.api.SphereSpace;
import instance.entity.api.EntityInstance;
import instance.entity.impl.EntityInstanceImpl;
import instance.entity.manager.api.EntityInstanceManager;

import java.io.Serializable;
import java.util.*;

public class EntityInstanceManagerImpl implements EntityInstanceManager , Serializable {
    private int id;
    private Map<Integer, EntityInstance> instances;

    public EntityInstanceManagerImpl() {
        instances = new HashMap<>();
        id = 1;
    }

    @Override
    public void createInstancesFromDefinition(EntityDefinition entityDefinition, SphereSpace space) {
        for (int i = 1; i <= entityDefinition.getPopulation(); i++) {
            EntityInstance newInstance = new EntityInstanceImpl(entityDefinition, id);
            Location placeInSpace = space.placeEntityRandomlyInWorld(newInstance);

            if (placeInSpace == null) {
                throw new NullPointerException(
                        "There is no place to add "
                                + newInstance.getName()
                                + " id #"
                                + newInstance.getId()
                                + " to the sphere space."
                );
            }

            newInstance.setLocationInSpace(placeInSpace);
            newInstance.setSpace(space);
            instances.put(id, newInstance);
            id++;
        }
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

    @Override
    public void moveAllEntitiesInSpace(SphereSpace space) {
        instances.values().forEach(space::makeRandomMove);
    }
}
