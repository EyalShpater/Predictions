package simulation.propety.entity.model;

import java.util.Map;

public class EntityInstance {
    private final String entityName;
    private Map<String, Object> properties;

    EntityInstance(Entity entity) {
        entityName = entity.getName();
        properties = entity.getPropertiesAsObjects();
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