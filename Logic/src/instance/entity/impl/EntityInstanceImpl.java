package instance.entity.impl;

import definition.entity.api.EntityDefinition;
import definition.property.api.PropertyDefinition;
import instance.property.api.PropertyInstance;
import instance.property.impl.PropertyInstanceImpl;
import instance.entity.api.EntityInstance;

import java.io.Serializable;
import java.util.*;

public class EntityInstanceImpl implements EntityInstance  , Serializable {
    private final int id;
    private final String name;
    private Map<String, PropertyInstance> propNameToPropInstance;
    private boolean isAlive = true;

    public EntityInstanceImpl(EntityDefinition entity, int id) {
        name = entity.getName();
        propNameToPropInstance = createPropertyInstancesFromDefinition(entity);
        this.id = id;
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
}