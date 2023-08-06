package simulation.propety.entity.model;

import java.util.*;

public class EntityInstance {
    private final String entityName;
    private List<PropertyInstance> properties;

    EntityInstance(Entity entity) {
        entityName = entity.getName();
        properties = setProperties(entity);
    }

    private List<PropertyInstance> setProperties(Entity entity) {
        List<PropertyInstance> properties = new ArrayList<>();

        for (int i = 0; i <= entity.getNumOfProperties(); i++) {
            properties.add(new PropertyInstance(entity.propertyAt(i)));
        }

        return properties;
    }
}