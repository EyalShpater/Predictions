package impl;

import api.DTO;

import java.util.List;
import java.util.Map;

public class SimulationInitDataFromUserDTO implements DTO {
    private final List<PropertyDefinitionDTO> environmentVariables;
    private final Map<String, Integer> entityNameToPopulation;
    private final TerminationDTO termination;


    public SimulationInitDataFromUserDTO(List<PropertyDefinitionDTO> environmentVariables, Map<String, Integer> entityNameToPopulation, TerminationDTO termination) {
        this.environmentVariables = environmentVariables;
        this.entityNameToPopulation = entityNameToPopulation;
        this.termination = termination;
    }

    public List<PropertyDefinitionDTO> getEnvironmentVariables() {
        return environmentVariables;
    }

    public Map<String, Integer> getEntityNameToPopulation() {
        return entityNameToPopulation;
    }

    public TerminationDTO getTermination() {
        return termination;
    }
}
