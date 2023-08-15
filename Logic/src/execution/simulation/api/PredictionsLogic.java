package execution.simulation.api;

import api.DTO;

import java.util.*;

public interface PredictionsLogic {
    boolean loadXML(String path);

    List<DTO> getEnvironmentVariablesToSet();

    DTO getSimulationDetails();

    DTO runNewSimulation(List<DTO> environmentVariables);

    Collection<DTO> getAllPreviousSimulationData();
}
