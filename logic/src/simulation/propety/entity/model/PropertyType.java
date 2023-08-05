package simulation.propety.entity.model;

public enum PropertyType {
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
