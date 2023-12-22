package impl;

import api.DTO;

import java.util.List;
import java.util.Map;

public class SimulationInitDataFromUserDTO implements DTO {
    private final List<PropertyDefinitionDTO> environmentVariables;
    private final Map<String, Integer> entityNameToPopulation;
    private final TerminationDTO termination;
    private final String worldName;
    private final String userName;
    private final Integer requestID;

    public SimulationInitDataFromUserDTO(List<PropertyDefinitionDTO> environmentVariables, Map<String, Integer> entityNameToPopulation
            , TerminationDTO termination, String worldName, String userName, Integer requestID) {
        this.environmentVariables = environmentVariables;
        this.entityNameToPopulation = entityNameToPopulation;
        this.termination = termination;
        this.worldName = worldName;
        this.userName = userName;
        this.requestID = requestID;
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

    public String getWorldName() {
        return worldName;
    }

    public String getUserName() {
        return userName;
    }

    public Integer getRequestID() {
        return requestID;
    }
}
