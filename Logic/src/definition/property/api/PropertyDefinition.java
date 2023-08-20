package definition.property.api;

import api.DTOConvertible;
import impl.PropertyDefinitionDTO;

public interface PropertyDefinition extends DTOConvertible<PropertyDefinitionDTO> {
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

    void setRandom(boolean isRandom);
}
