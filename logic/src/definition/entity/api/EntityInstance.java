package definition.entity.api;

import definition.property.api.PropertyDefinition;

public interface EntityInstance {
    String getName();
    int getPopulation();

    PropertyDefinition propertyAt(int index);

    PropertyDefinition getPropertyByName(String name);

    void addProperty(PropertyDefinition newProperty);

    int getNumOfProperties();
}