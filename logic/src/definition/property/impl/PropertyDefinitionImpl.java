package definition.property.impl;

import definition.property.api.PropertyDefinition;
import definition.property.api.PropertyType;
import definition.property.api.Range;

public class PropertyDefinitionImpl implements PropertyDefinition {
    private String name;
    private final PropertyType type;
    private Range range = null;
    private boolean isValueInitializeRandomly;
    private Object defaultValue = null;

    public PropertyDefinitionImpl(String name, PropertyType type, boolean isValueInitializeRandomly) {
        if (name.isEmpty()) {
            throw new NullPointerException("Title can not be empty!");
        }

        if (type == null) {
            throw new NullPointerException("Property type can not be null!");
        }

        this.name = name;
        this.type = type;
        this.isValueInitializeRandomly = isValueInitializeRandomly;
    }

    public PropertyDefinitionImpl(String name, PropertyType type, boolean isValueInitializeRandomly, Range range) {
        this(name, type, isValueInitializeRandomly);
        this.range = range;
    }

    public PropertyDefinitionImpl(String name, PropertyType type, boolean isValueInitializeRandomly, Object value) {
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

    @Override
    public PropertyType getType() {
        return type;
    }

    @Override
    public Range getRange() {
        return range;
    }

    @Override
    public boolean isValueInitializeRandomly() {
        return isValueInitializeRandomly;
    }

    @Override
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
