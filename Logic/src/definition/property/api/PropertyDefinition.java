package definition.property.api;

import api.DTOConvertible;

public interface PropertyDefinition extends DTOConvertible {
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
