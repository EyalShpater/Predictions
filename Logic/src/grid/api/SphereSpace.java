package grid.api;

import instance.entity.api.EntityInstance;

public interface SphereSpace {
    boolean makeRandomMove(EntityInstance entity);

    Location placeEntityRandomlyInWorld(EntityInstance entity);
}
