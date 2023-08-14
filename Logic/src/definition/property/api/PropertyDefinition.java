package definition.property.api;

public interface PropertyDefinition {
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
