package impl;

import api.DTO;
import java.util.List;

public class EntityDefinitionDTO implements DTO {
    private final String name;
    private final int population;
    private List<PropertyDefinitionDTO> properties;

    public EntityDefinitionDTO(String name, int population, List<PropertyDefinitionDTO> properties) {
        this.name = name;
        this.population = population;
        this.properties = properties;
    }

    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }

    public List<PropertyDefinitionDTO> getProperties() {
        return properties;
    }
}
