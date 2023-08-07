package definition.entity.api;

import definition.property.impl.PropertyDefinition;

public interface EntityDefinitionInterface {
    String getName();

    int getPopulation();

    PropertyDefinition propertyAt(int index);

    void addProperty(PropertyDefinition newProperty);
}
