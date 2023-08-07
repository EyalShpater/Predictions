package definition.entity.api;

import definition.property.impl.PropertyDefinitionImpl;

public interface EntityDefinition {
    String getName();
    int getPopulation();

    PropertyDefinitionImpl propertyAt(int index);

    void addProperty(PropertyDefinitionImpl newProperty);
}
