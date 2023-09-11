package grid.api;

import instance.entity.api.EntityInstance;

import java.util.List;

public interface SphereSpace {
    boolean makeRandomMove(EntityInstance entity);

    Location placeEntityRandomlyInWorld(EntityInstance entity);

    void removeEntityFromSpace(EntityInstance entityToRemove);

    List<EntityInstance> getNearbyEntities(EntityInstance target, int radius);
}
