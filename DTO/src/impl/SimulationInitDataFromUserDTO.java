package impl;

import api.DTO;

import java.util.List;
import java.util.Map;

public class SimulationInitDataFromUserDTO implements DTO {
    List<PropertyDefinitionDTO> environmentVariables;
    Map<String, Integer> entityNameToPopulation;

    public SimulationInitDataFromUserDTO(List<PropertyDefinitionDTO> environmentVariables, Map<String, Integer> entityNameToPopulation) {
        this.environmentVariables = environmentVariables;
        this.entityNameToPopulation = entityNameToPopulation;
    }

    public List<PropertyDefinitionDTO> getEnvironmentVariables() {
        return environmentVariables;
    }

    public Map<String, Integer> getEntityNameToPopulation() {
        return entityNameToPopulation;
    }
}
