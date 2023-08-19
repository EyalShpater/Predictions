package definition.property.api;

import java.io.Serializable;

public enum PropertyType implements Serializable {
    INT("Integer Number"),
    DOUBLE("Real Number"),
    BOOLEAN("Boolean"),
    STRING("String");

    private final String description;

    PropertyType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
