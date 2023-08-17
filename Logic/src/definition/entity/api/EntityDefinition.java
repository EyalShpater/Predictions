package definition.entity.api;

import api.DTOConvertible;
import definition.property.api.PropertyDefinition;
import impl.EntityDefinitionDTO;

public interface EntityDefinition extends DTOConvertible<EntityDefinitionDTO> {
    String getName();

    int getPopulation();

    PropertyDefinition propertyAt(int index);

    PropertyDefinition getPropertyByName(String name);

    void addProperty(PropertyDefinition newProperty);

    int getNumOfProperties();
}
