package component.results.helper;

import javafx.beans.property.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Category {
    private long time;
    private int id;
    private String simulationName;

    public Category(String simulationName, long time, int id) {
        this.time = time;
        this.id = id;
        this.simulationName = simulationName;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSimulationName() {
        return simulationName;
    }

    public void setSimulationName(String simulationName) {
        this.simulationName = simulationName;
    }

    @Override
    public String toString() {
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy | HH:mm:ss");

        return new String(simpleDateFormat.format(date));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
