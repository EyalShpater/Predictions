package instance.entity.impl;

import definition.entity.api.EntityDefinition;
import definition.property.api.PropertyDefinition;
import instance.property.api.PropertyInstance;
import instance.property.impl.PropertyInstanceImpl;
import java.util.*;

public class EntityInstanceImpl implements instance.entity.api.EntityInstance {
    private final int id;
    private final String entityName;
    private Map<String, PropertyInstance> propNameToPropInstance;

    public EntityInstanceImpl(EntityDefinition entity, int id) {
        entityName = entity.getName();
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
}