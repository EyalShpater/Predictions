package execution.simulation.api;

import api.DTO;

import java.util.*;

public interface PredictionsLogic {
    void loadXML(String path);

    List<DTO> getEnvironmentVariablesToSet();

    void setEnvironmentVariablesValues(List<DTO> variablesValues);

    DTO getSimulationDetails();

    DTO runNewSimulation(DTO environmentVariables);

    Collection<DTO> getAllPreviousSimulationData();
}
