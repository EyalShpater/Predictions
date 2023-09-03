package instance.property.api;

import api.DTOConvertible;
import definition.property.api.PropertyDefinition;
import impl.PropertyDefinitionDTO;

public interface PropertyInstance extends DTOConvertible<PropertyDefinitionDTO> {
    Object getValue();

    String getName();

    PropertyDefinition getPropertyDefinition();

    void setValue(Object val);
}
