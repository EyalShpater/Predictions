package instance.entity.impl;

import action.context.api.Context;
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
    private int deathTick = -1;
    private SphereSpace space;
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
    public List<PropertyInstance> getAllProperties() {
        return new ArrayList<>(propNameToPropInstance.values());
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
    public void kill(int tick) {
        space.removeEntityFromSpace(this);
        this.isAlive = false;
        this.deathTick = tick;
    }


    @Override
    public int getDeathTick() {
        return deathTick;
    }

    @Override
    public Location getLocationInSpace() {
        return locationInSpace;
    }

    @Override
    public void setLocationInSpace(Location newLocation) {
        this.locationInSpace = newLocation;
    }

    @Override
    public void moveRandomly() {
        if (space == null) {
            throw new NullPointerException("Space must be initialize before!");
        }

        space.makeRandomMove(this);
    }

    @Override
    public List<EntityInstance> getNearbyEntities(int radius) {
        return space.getNearbyEntities(this, radius);
    }

    @Override
    public void copyPropertiesFrom(EntityInstance entityToCopy, Context context) {
        propNameToPropInstance.forEach((propertyName, propertyInstance) -> {
            PropertyInstance propertyInstanceToCopy = entityToCopy.getPropertyByName(propertyName);
            if (isPropertyExist(propertyInstanceToCopy)) {
                if (isSameType(propertyInstance, propertyInstanceToCopy)) {
                    propertyInstance.setValue(propertyInstanceToCopy.getValue(), context);
                }
            }
        });
    }

    private boolean isPropertyExist(PropertyInstance propertyInstance) {
        return propertyInstance != null;
    }

    private boolean isSameType(PropertyInstance propertyInstance, PropertyInstance propertyInstanceToCopy) {
        return propertyInstance.getPropertyDefinition().getType() == propertyInstanceToCopy.getPropertyDefinition().getType();
    }

    @Override
    public void setSpace(SphereSpace space) {
        this.space = space;
    }

    @Override
    public SphereSpace getSpace() {
        return space;
    }
}