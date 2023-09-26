package instance.entity.api;

import action.context.api.Context;
import grid.api.Location;
import grid.api.SphereSpace;
import instance.property.api.PropertyInstance;

import java.awt.*;
import java.util.List;

public interface EntityInstance {
    int getId();

    PropertyInstance getPropertyByName(String name);

    List<PropertyInstance> getAllProperties();

    String getName();

    boolean isAlive();

    void kill(int tick);

    int getDeathTick();

    void setSpace(SphereSpace space);

    SphereSpace getSpace();

    void moveRandomly();

    Location getLocationInSpace();

    void setLocationInSpace(Location newLocation);

    List<EntityInstance> getNearbyEntities(int radius);

    void copyPropertiesFrom(EntityInstance entityToCopy, Context context);
}
