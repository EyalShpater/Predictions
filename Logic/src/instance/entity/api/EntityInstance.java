package instance.entity.api;

import grid.api.Location;
import grid.api.SphereSpace;
import instance.property.api.PropertyInstance;

import java.awt.*;
import java.util.List;

public interface EntityInstance {
    int getId();

    PropertyInstance getPropertyByName(String name);

    String getName();

    boolean isAlive();

    void kill();

    void setSpace(SphereSpace space);

    SphereSpace getSpace();

    void moveRandomly();

    Location getLocationInSpace();

    void setLocationInSpace(Location newLocation);

    List<EntityInstance> getNearbyEntities(int radius);

    void copyPropertiesFrom(EntityInstance entityToCopy);
}
