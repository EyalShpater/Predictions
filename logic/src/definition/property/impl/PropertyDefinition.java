package definition.property.impl;

import definition.property.api.PropertyDefinitionInterface;
import definition.property.api.PropertyType;
import simulation.propety.entity.model.Range;

public class PropertyDefinition implements PropertyDefinitionInterface {
    private String name;
    private final PropertyType type;
    private Range range = null;
    boolean isValueInitializeRandomly;
    Object defaultValue = null;

    private PropertyDefinition(String name, PropertyType type, boolean isValueInitializeRandomly) {
        if (name.isEmpty()) {
            throw new NullPointerException("Title can not be empty!");
        }

        if (type == null) {
            throw new NullPointerException("Property type can not be null!");
        }

        this.name = name;
        this.type = type;
        this.isValueInitializeRandomly = isValueInitializeRandomly();
    }

    public PropertyDefinition(String name, PropertyType type, boolean isValueInitializeRandomly, Range range) {
        this(name, type, isValueInitializeRandomly);
        this.range = range;
    }

    public PropertyDefinition(String name, PropertyType type, boolean isValueInitializeRandomly, Object value) {
        this(name, type, isValueInitializeRandomly);
        if (value == null) {
            throw new IllegalArgumentException("Value can not be null!");
        }

        this.defaultValue = value;
    }

    public void setName(String name) {
        if (name.isEmpty()) {
            throw new NullPointerException("Title can not be empty!");
        }

        this.name = name;
    }

    public void setValueInitializeRandomly(boolean valueInitializeRandomly) {
        isValueInitializeRandomly = valueInitializeRandomly;
    }

    public PropertyType getType() {
        return type;
    }

    public Range getRange() {
        return range;
    }

    public boolean isValueInitializeRandomly() {
        return isValueInitializeRandomly;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append(this.name).append(System.lineSeparator());
        str.append("Type: ").append(type).append(System.lineSeparator());
        if (range != null) {
            str.append("Range: ").append(range).append(System.lineSeparator());
        }

        str.append(isValueInitializeRandomly ? "Not random" : "Random").append(" initialize");

        return str.toString();
    }
}
