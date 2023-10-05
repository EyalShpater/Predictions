package component.results.helper;

import javafx.beans.property.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Category {
    private LongProperty time;
    private IntegerProperty id;
    private StringProperty simulationName;

    public Category(String simulationName, long time, int id) {
        this.time = new SimpleLongProperty(time);
        this.id = new SimpleIntegerProperty(id);
        this.simulationName = new SimpleStringProperty(simulationName);
    }

    public LongProperty getTimeProperty() {
        return time;
    }

    public long getTime() {
        return time.get();
    }

    public void setTime(long time) {
        this.time.set(time);
    }

    public IntegerProperty getIdProperty() {
        return id;
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getSimulationName() {
        return simulationName.get();
    }

    public StringProperty simulationNameProperty() {
        return simulationName;
    }

    @Override
    public String toString() {
        Date date = new Date(time.get());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy | HH:mm:ss");

        return new String(simpleDateFormat.format(date));
    }
}
