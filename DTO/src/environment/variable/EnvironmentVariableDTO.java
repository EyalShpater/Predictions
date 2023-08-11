package environment.variable;

import api.DTO;

public class EnvironmentVariableDTO implements DTO {
    private String name;
    private String type;
    private Double from;
    private Double to;
    private boolean isRandom;
    private Object defaultValue;

    public EnvironmentVariableDTO(String name, String type, Double from, Double to, boolean isRandom, Object defaultValue) {
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
