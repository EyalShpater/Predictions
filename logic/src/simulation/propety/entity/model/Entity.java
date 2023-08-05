package simulation.propety.entity.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Entity {
    private int population;
    private String name;
    private List<Property> properties = new ArrayList<>();

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        if (population <= 0) {
            throw new IllegalArgumentException("Population must be a positive number.");
        }

        this.population = population;
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

    public Map<String, Object> getPropertiesAsObjects() {
        Map<String, Object> res = new HashMap<>();

        for (Property property : properties) {
            res.put(property.getTitle(), property.getValue());
        }

        return res;
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

        result.append("Name: ").append(name).append(System.lineSeparator());
        result.append("Population: ").append(population).append(System.lineSeparator());
        result.append("Properties:").append(System.lineSeparator());
        for (Property prop : properties) {
            result.append(prop).append(System.lineSeparator());
        }

        return result.toString();
    }
}
