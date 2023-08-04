package simulation.propety.entity.model;

import java.util.ArrayList;

public class Entity {
    static private int population;
    private String name;
    private ArrayList<Property> properties;

    public static int getPopulation() {
        return population;
    }

    public static void setPopulation(int population) {
        Entity.population = population;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
