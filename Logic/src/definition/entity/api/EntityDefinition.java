package definition.entity.api;

import api.DTOConvertible;
import definition.property.api.PropertyDefinition;

public interface EntityDefinition extends DTOConvertible {
    String getName();
    int getPopulation();

    PropertyDefinition propertyAt(int index);

    PropertyDefinition getPropertyByName(String name);

    void addProperty(PropertyDefinition newProperty);

    int getNumOfProperties();
}
