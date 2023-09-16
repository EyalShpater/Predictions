package javafx.tab.newExecution.entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class BasicEntityData implements EntityData {
    protected SimpleStringProperty entityName;
    protected SimpleStringProperty population;

    public BasicEntityData(String entityName, String population) {
        this.entityName = new SimpleStringProperty(entityName);
        this.population = new SimpleStringProperty(population);
    }

    @Override
    public String getEntity() {
        return entityName.get();
    }

    @Override
    public void setEntity(String entityName) {
        this.entityName.set(entityName);
    }

    @Override
    public String getPopulation() {
        return population.get();
    }

    @Override
    public void setPopulation(String population) {
        this.population.set(population);
    }
}
