package instance.entity.impl;

import definition.entity.impl.EntityDefinitionImpl;
import instance.entity.api.EntityInstance;
import instance.property.impl.PropertyInstanceImpl;

import java.util.*;

public class EntityInstanceImpl implements EntityInstance {
    private static int id = 1;
    private final String entityName;
    private List<PropertyInstanceImpl> properties;

    EntityInstanceImpl(EntityDefinitionImpl entity) {
        entityName = entity.getName();
        properties = setProperties(entity);
    }

    private List<PropertyInstanceImpl> setProperties(EntityDefinitionImpl entity) {
        List<PropertyInstanceImpl> properties = new ArrayList<>();

        for (int i = 0; i <= entity.getNumOfProperties(); i++) {
            properties.add(new PropertyInstanceImpl(entity.propertyAt(i)));
        }

        return properties;
    }

    @Override
    public int getId() {
        return id;
    }
}