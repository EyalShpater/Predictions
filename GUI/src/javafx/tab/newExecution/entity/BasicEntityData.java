package javafx.tab.newExecution.entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class BasicEntityData implements EntityData {
    protected SimpleStringProperty entityName;
    protected SimpleIntegerProperty population;

    public BasicEntityData(String entityName, int population) {
        this.entityName = new SimpleStringProperty(entityName);
        this.population = new SimpleIntegerProperty(population);
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
    public int getPopulation() {
        return population.get();
    }

    @Override
    public void setPopulation(int population) {
        this.population.set(population);
    }
}
