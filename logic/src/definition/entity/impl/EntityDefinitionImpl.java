package definition.entity.impl;

import definition.entity.api.EntityDefinition;
import definition.property.impl.PropertyDefinitionImpl;

import java.util.*;

public class EntityDefinitionImpl implements EntityDefinition {
    private String name;
    private int population;
    private List<PropertyDefinitionImpl> properties = new ArrayList<>();

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

    public void addProperty(PropertyDefinitionImpl newProperty) {
        if (newProperty == null) {
            throw new NullPointerException("Can not add empty property!");
        }

        properties.add(newProperty);
    }

    public PropertyDefinitionImpl propertyAt(int index) {
        return properties.get(index);
    }

    public int getNumOfProperties() {
        return properties.size();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("Name: ").append(name).append(System.lineSeparator());
        result.append("Population: ").append(population).append(System.lineSeparator());
        result.append("Properties:").append(System.lineSeparator());
        for (PropertyDefinitionImpl prop : properties) {
            result.append(prop).append(System.lineSeparator());
        }

        return result.toString();
    }


}
