package simulation.propety.entity.model;

public class Property {
    private String title;
    private final PropertyType type;
    private Range range = null;
    boolean isValueInitializeRandomly;
    Object defaultValue = null;

    private Property(String title, PropertyType type, boolean isValueInitializeRandomly) {
        if (title.isEmpty()) {
            throw new NullPointerException("Title can not be empty!");
        }

        if (type == null) {
            throw new NullPointerException("Property type can not be null!");
        }

        this.title = title;
        this.type = type;
        this.isValueInitializeRandomly = isValueInitializeRandomly();
    }

    public Property(String title, PropertyType type, boolean isValueInitializeRandomly, Range range) {
        this(title, type, isValueInitializeRandomly);
        this.range = range;
    }

    public Property(String title, PropertyType type, boolean isValueInitializeRandomly, Object value) {
        this(title, type, isValueInitializeRandomly);
        if (value == null) {
            throw new IllegalArgumentException("Value can not be null!");
        }

        this.defaultValue = value;
    }

    public void setTitle(String title) {
        if (title.isEmpty()) {
            throw new NullPointerException("Title can not be empty!");
        }

        this.title = title;
    }

    public void setValueInitializeRandomly(boolean valueInitializeRandomly) {
        isValueInitializeRandomly = valueInitializeRandomly;
    }

    public String getTitle() {
        return title;
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
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append(this.title).append(System.lineSeparator());
        str.append("Type: ").append(type).append(System.lineSeparator());
        if (range != null) {
            str.append("Range: ").append(range).append(System.lineSeparator());
        }

        str.append(isValueInitializeRandomly ? "Not random" : "Random").append(" initialize");

        return str.toString();
    }
}
