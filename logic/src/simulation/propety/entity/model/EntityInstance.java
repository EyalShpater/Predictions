package simulation.propety.entity.model;

import java.util.ArrayList;
import java.util.List;

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
            properties.add(createPropertyInstanceFromProperty(entity.propertyAt(i)));
        }

        return properties;
    }

    private PropertyInstance createPropertyInstanceFromProperty(Property propertyDefinition) {
        PropertyInstance instance = null;


        return instance;
    }
}








/*

Name: Smoker
Population: 100

[{"LCP, double, Range, ...}, {KFD, int, ...}]


----------------------------------------------


Name: Smoker


object LCP = Double (1);
object KFD = Integer (23);

if(properties[0] instance of Integer)


 */