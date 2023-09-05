package instance.entity.api;

import instance.property.api.PropertyInstance;

import java.awt.*;

public interface EntityInstance {
    int getId();
    PropertyInstance getPropertyByName(String name);
    String getName();
    boolean isAlive();
    void kill();

    Point getLocationInSpace();

}
