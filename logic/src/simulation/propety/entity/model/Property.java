package simulation.propety.entity.model;

public class Property {
    private String title;
    private PropertyType type;
    private Range range;
    boolean isNeedToBeInitialize;


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
        return "title= " + title;
    }
}
