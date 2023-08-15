package instance.entity.api;

import instance.property.api.PropertyInstance;

public interface EntityInstance {
    int getId();

    PropertyInstance getPropertyByName(String name);

    void setEntityFirstName(String name);

    String getEntityFirstName();

    boolean isAlive();

    void kill();

}
