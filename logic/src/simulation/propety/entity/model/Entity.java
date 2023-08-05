package simulation.propety.entity.model;

import java.util.ArrayList;
import java.util.List;

public class Entity {
    static private int population;
    private String name;
    private List<Property> properties = new ArrayList<>();

    public static int getPopulation() {
        return population;
    }

    public static void setPopulation(int population) {
        if (population <= 0) {
            throw new IllegalArgumentException("Population must be a positive number.");
        }

        Entity.population = population;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.isEmpty()) {
            throw new NullPointerException("Name can not be empty");
        }
        
        this.name = name;
    }

    public void addProperty(Property newProperty) {
        if (newProperty == null) {
            throw new NullPointerException("Can not add empty property!");
        }

        properties.add(newProperty);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("Name: " + name + System.lineSeparator());
        result.append("Population: " + population + System.lineSeparator());
        result.append("Properties:" + System.lineSeparator());
        for (Property prop : properties) {
            result.append(prop + System.lineSeparator());
        }

        return result.toString();
    }
}
