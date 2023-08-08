package instance.entity.impl;

import definition.entity.api.EntityDefinition;
import definition.property.api.PropertyDefinition;
import instance.entity.api.EntityInstance;
import instance.property.api.PropertyInstance;
import instance.property.impl.PropertyInstanceImpl;
import java.util.*;

public class EntityInstanceImpl implements EntityInstance {
    private static int id = 1;
    private final String entityName;
    private Map<String, PropertyInstance> properties;

    public EntityInstanceImpl(EntityDefinition entity) {
        entityName = entity.getName();
        properties = setProperties(entity);
    }

    private Map<String, PropertyInstance> setProperties(EntityDefinition entity) {
        Map<String, PropertyInstance> properties = new HashMap<>();
        PropertyDefinition currentPropertyDefinition;

        for (int i = 0; i < entity.getNumOfProperties(); i++) {
            currentPropertyDefinition = entity.propertyAt(i);
            properties.put(currentPropertyDefinition.getName(),
                    new PropertyInstanceImpl(currentPropertyDefinition));
        }
        //why does a set method returns the map ?
        return properties;
    }

    @Override
    public PropertyInstance getPropertyByName(String name) {
        return properties.get(name);
    }

    @Override
    public int getId() {
        return id;
    }
}