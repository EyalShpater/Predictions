package instance.entity.manager.api;

import action.context.api.Context;
import definition.entity.api.EntityDefinition;
import grid.api.SphereSpace;
import instance.entity.api.EntityInstance;

import java.util.List;

public interface EntityInstanceManager {
    void createInstancesFromDefinition(EntityDefinition entityDefinition, SphereSpace space);

    //todo: can we delete?
    //void killEntity(int idToKill);

    List<EntityInstance> getInstances();

    void moveAllEntitiesInSpace(SphereSpace space);

    void createNewEntityInstanceFromScratch(EntityDefinition entityToCreate, SphereSpace space);

    void createNewEntityInstanceWithSamePropertyValues(EntityInstance entityToCopy, EntityDefinition entityToCreate, Context context);
}
