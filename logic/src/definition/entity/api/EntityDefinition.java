package definition.entity.api;

import definition.property.api.PropertyDefinition;
import definition.property.impl.PropertyDefinitionImpl;

public interface EntityDefinition {
    String getName();
    int getPopulation();

    PropertyDefinition propertyAt(int index);

    PropertyDefinition getPropertyByName(String name);

    void addProperty(PropertyDefinition newProperty);

    int getNumOfProperties();
}
