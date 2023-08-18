package definition.property.impl;

import api.DTO;
import api.DTOConvertible;
import definition.property.api.PropertyDefinition;
import definition.property.api.PropertyType;
import definition.property.api.Range;
import impl.PropertyDefinitionDTO;

import java.util.stream.Collectors;

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

    public PropertyDefinitionImpl(String name, PropertyType type, Range range, boolean isValueInitializeRandomly, Object defaultValue) {
        this.name = name;
        this.type = type;
        this.range = range;
        this.isValueInitializeRandomly = isValueInitializeRandomly;
        this.defaultValue = defaultValue;
    }

    public PropertyDefinitionImpl(PropertyDefinitionDTO dto) {
        this(dto.getName(),
                PropertyType.valueOf(dto.getType()),
                dto.isRandom(),
                dto.getDefaultValue()
        );

        if (dto.getFrom() != null && dto.getTo() != null) {
            range = new Range(dto.getFrom(), dto.getTo());
        }
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
    public boolean isInteger() {
        return type.equals(PropertyType.INT);
    }

    @Override
    public boolean isDouble() {
        return type.equals(PropertyType.DOUBLE);
    }

    @Override
    public boolean isString() {
        return type.equals(PropertyType.STRING);
    }

    @Override
    public boolean isBoolean() {
        return type.equals(PropertyType.BOOLEAN);
    }

    @Override
    public boolean isNumeric() {
        return isDouble() || isInteger();
    }

    @Override
    public PropertyDefinitionDTO convertToDTO() {
        return new PropertyDefinitionDTO(
                name,
                type.name(),
                range != null ?
                        range.getMin() :
                        null,
                range != null ?
                        range.getMax() :
                        null,
                isValueInitializeRandomly,
                defaultValue
        );
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
