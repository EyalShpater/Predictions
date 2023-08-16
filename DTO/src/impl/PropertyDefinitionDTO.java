package impl;

import api.DTO;

public class PropertyDefinitionDTO implements DTO {
    private final String name;
    private final String type;
    private final Double from;
    private final Double to;
    private final boolean isRandom;
    private final Object defaultValue;

    public PropertyDefinitionDTO(String name, String type, Double from, Double to, boolean isRandom, Object defaultValue) {
        this.name = name;
        this.type = type;
        this.from = from;
        this.to = to;
        this.isRandom = isRandom;
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Double getFrom() {
        return from;
    }

    public Double getTo() {
        return to;
    }

    public boolean isRandom() {
        return isRandom;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }
}
