package definition.property.api;

import api.DTOConvertible;
import impl.PropertyDefinitionDTO;

public interface PropertyDefinition extends DTOConvertible<PropertyDefinition, PropertyDefinitionDTO> {
    String getName();
    PropertyType getType();
    Range getRange();
    boolean isValueInitializeRandomly();
    Object getDefaultValue();

    boolean isInteger();

    boolean isDouble();

    boolean isString();

    boolean isBoolean();

    boolean isNumeric();
}
