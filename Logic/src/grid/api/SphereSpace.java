package grid.api;

import instance.entity.api.EntityInstance;

import java.awt.*;

public interface SphereSpace {
    boolean makeRandomMove(EntityInstance entity);

    boolean setRandomLocation(EntityInstance entity);
}
