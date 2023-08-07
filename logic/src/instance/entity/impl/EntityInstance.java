package instance.entity.impl;

import definition.entity.impl.EntityDefinition;
import instance.entity.api.EntityInstanceInterface;
import instance.property.impl.PropertyInstance;

import java.util.*;

public class EntityInstance implements EntityInstanceInterface {
    private static int id = 1;
    private final String entityName;
    private List<PropertyInstance> properties;

    EntityInstance(EntityDefinition entity) {
        entityName = entity.getName();
        properties = setProperties(entity);
    }

    private List<PropertyInstance> setProperties(EntityDefinition entity) {
        List<PropertyInstance> properties = new ArrayList<>();

        for (int i = 0; i <= entity.getNumOfProperties(); i++) {
            properties.add(new PropertyInstance(entity.propertyAt(i)));
        }

        return properties;
    }

    @Override
    public int getId() {
        return id;
    }
}