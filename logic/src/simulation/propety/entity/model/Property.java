package simulation.propety.entity.model;

public class Property {
    private String title;
    private PropertyType type;
    private Range range = null;
    boolean isNeedToBeInitialize = true;

    public Property(String title, PropertyType type, boolean isNeedToBeInitialize) {
        if (title.isEmpty()) {
            throw new NullPointerException("Title can not be empty!");
        }

        this.title = title;
        this.type = type;
        this.isNeedToBeInitialize = isNeedToBeInitialize;
    }

    public Property(String title, PropertyType type, boolean isNeedToBeInitialize, Range range) {
        this(title, type, isNeedToBeInitialize);
        this.range = range;
    }

    public void setTitle(String title) {
        if (title.isEmpty()) {
            throw new NullPointerException("Title can not be empty!");
        }

        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setNeedToBeInitialize(boolean needToBeInitialize) {
        isNeedToBeInitialize = needToBeInitialize;
    }

    public boolean isNeedToBeInitialize() {
        return isNeedToBeInitialize;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append(this.title).append(System.lineSeparator());
        str.append("Type: ").append(type).append(System.lineSeparator());
        if (range != null) {
            str.append("Range: ").append(range).append(System.lineSeparator());
        }

        str.append(isNeedToBeInitialize ? "Not random" : "Random").append(" initialize");

        return str.toString();
    }
}
