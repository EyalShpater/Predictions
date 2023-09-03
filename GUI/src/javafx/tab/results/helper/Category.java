package javafx.tab.results.helper;

import javafx.beans.property.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Category {
    private LongProperty time;
    private IntegerProperty id;

    public Category(long time, int id) {
        this.time = new SimpleLongProperty(time);
        this.id = new SimpleIntegerProperty(id);
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

    @Override
    public String toString() {
        Date date = new Date(time.get());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy | HH.mm.ss");

        return new String(simpleDateFormat.format(date) + ", id: #" + id.get());
    }
}
