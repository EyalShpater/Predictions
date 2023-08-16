package definition.entity.impl;

import api.DTO;
import api.DTOConvertible;
import definition.entity.api.EntityDefinition;
import definition.property.api.PropertyDefinition;

import java.util.*;

public class EntityDefinitionImpl implements EntityDefinition, DTOConvertible {
    private String name;
    private int population;
    private List<PropertyDefinition> properties = new ArrayList<>();

    public EntityDefinitionImpl(String name, int population) {
        setName(name);
        setPopulation(population);
    }


    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        if (population <= 0) {
            throw new IllegalArgumentException("Population must be a positive number.");
        }

        this.population = population;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.isEmpty()) {
            throw new NullPointerException("Name can not be empty");
        }

        this.name = name;
    }

    public void addProperty(PropertyDefinition newProperty) {
        if (newProperty == null) {
            throw new NullPointerException("Can not add empty property!");
        }

        properties.add(newProperty);
    }

    @Override
    public PropertyDefinition propertyAt(int index) {
        return properties.get(index);
    }

    @Override
    public PropertyDefinition getPropertyByName(String name) {
        Optional<PropertyDefinition> theProperty = properties.stream().filter(property -> property.getName().equals(name)).findFirst();
        return theProperty.get();

    }

    @Override
    public int getNumOfProperties() {
        return properties.size();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("Name: ").append(name).append(System.lineSeparator());
        result.append("Population: ").append(population).append(System.lineSeparator());
        result.append("Properties:").append(System.lineSeparator());
        for (PropertyDefinition prop : properties) {
            result.append(prop).append(System.lineSeparator());
        }

        return result.toString();
    }

    @Override
    public DTO convertToDTO() {
        return null;
    }
}
