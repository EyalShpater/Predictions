package instance.entity.api;

import grid.api.Location;
import instance.property.api.PropertyInstance;

import java.awt.*;

public interface EntityInstance {
    int getId();
    PropertyInstance getPropertyByName(String name);
    String getName();
    boolean isAlive();
    void kill();

    Location getLocationInSpace();

    void setLocationInSpace(int x, int y);

    void setLocationInSpace(Location newLocation);

}
