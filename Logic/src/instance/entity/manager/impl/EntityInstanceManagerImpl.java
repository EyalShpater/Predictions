package instance.entity.manager.impl;

import action.context.api.Context;
import definition.entity.api.EntityDefinition;
import definition.property.api.PropertyDefinition;
import grid.api.Location;
import grid.api.SphereSpace;
import instance.entity.api.EntityInstance;
import instance.entity.impl.EntityInstanceImpl;
import instance.entity.manager.api.EntityInstanceManager;
import instance.property.impl.PropertyInstanceImpl;

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
    public void createInstancesFromDefinition(EntityDefinition entityDefinition, int population, SphereSpace space) {
        for (int i = 1; i <= population; i++) {
            EntityInstance newInstance = new EntityInstanceImpl(entityDefinition, id);
            Location placeInSpace = space.placeEntityRandomlyInWorld(newInstance);

            if (placeInSpace == null) {
                throw new IllegalArgumentException(
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

    // todo: what is the different between this and the method above?
    @Override
    public void createNewEntityInstanceFromScratch(EntityDefinition entityToCreate, SphereSpace space) {
        EntityInstance newInstance = new EntityInstanceImpl(entityToCreate, id);
        Location placeInSpace = space.placeEntityRandomlyInWorld(newInstance);
        if (placeInSpace == null) {
            throw new IllegalArgumentException(
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

    // todo: maybe change the name to clone
    @Override
    public void createNewEntityInstanceWithSamePropertyValues(EntityInstance entityToCopy, EntityDefinition entityToCreate, Context context) {
        EntityInstance newInstance = new EntityInstanceImpl(entityToCreate, id);
        Location placeInSpace = entityToCopy.getLocationInSpace();
        if (placeInSpace == null) {
            throw new IllegalArgumentException(
                    "There is no place to add "
                            + newInstance.getName()
                            + " id #"
                            + newInstance.getId()
                            + " to the sphere space."
            );
        }
        newInstance.copyPropertiesFrom(entityToCopy, context);

        newInstance.setLocationInSpace(placeInSpace);
        newInstance.setSpace(entityToCopy.getSpace());
        instances.put(id, newInstance);

        id++;
    }

    @Override
    public List<EntityInstance> getInstances() {
        return new ArrayList<>(instances.values());
    }

//    @Override
//    public void killEntity(int idToKill) {
//        EntityInstance instanceToKill = instances.get(idToKill);
//
//        if (instanceToKill == null) {
//            throw new IllegalArgumentException("ID is not valid");
//        }
//
//        instanceToKill.kill();
//    }

    @Override
    public void moveAllEntitiesInSpace(SphereSpace space) {
        instances
                .values()
                .stream()
                .filter(EntityInstance::isAlive)
                .forEach(space::makeRandomMove);
    }
}
