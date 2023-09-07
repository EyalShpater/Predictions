package instance.entity.impl;

import definition.entity.api.EntityDefinition;
import definition.property.api.PropertyDefinition;
import grid.api.Location;
import grid.api.SphereSpace;
import instance.property.api.PropertyInstance;
import instance.property.impl.PropertyInstanceImpl;
import instance.entity.api.EntityInstance;

import java.io.Serializable;
import java.util.*;

public class EntityInstanceImpl implements EntityInstance, Serializable {
    private final int id;
    private final String name;
    private Map<String, PropertyInstance> propNameToPropInstance;
    private boolean isAlive;
    private Location locationInSpace;

    public EntityInstanceImpl(EntityDefinition entity, int id) {
        name = entity.getName();
        propNameToPropInstance = createPropertyInstancesFromDefinition(entity);
        this.id = id;
        isAlive = true;
    }

    private Map<String, PropertyInstance> createPropertyInstancesFromDefinition(EntityDefinition entity) {
        Map<String, PropertyInstance> properties = new HashMap<>();
        PropertyDefinition currentPropertyDefinition;

        for (int i = 0; i < entity.getNumOfProperties(); i++) {
            currentPropertyDefinition = entity.propertyAt(i);
            properties.put(currentPropertyDefinition.getName(),
                    new PropertyInstanceImpl(currentPropertyDefinition));
        }

        return properties;
    }

    @Override
    public PropertyInstance getPropertyByName(String name) {
        return propNameToPropInstance.get(name);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public void kill(){
        this.isAlive = false;
    }

    @Override
    public Location getLocationInSpace() {
        return locationInSpace;
    }

    @Override
    public void setLocationInSpace(Location newLocation) {
        this.locationInSpace = newLocation;
    }

}