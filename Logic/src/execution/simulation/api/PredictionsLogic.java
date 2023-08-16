package execution.simulation.api;

import api.DTO;
import impl.WorldDTO;

import java.util.*;

public interface PredictionsLogic {
    boolean loadXML(String path);

    List<DTO> getEnvironmentVariablesToSet();

    WorldDTO getSimulationDetails();

    void runNewSimulation(List<DTO> environmentVariables);

    Collection<DTO> getAllPreviousSimulationData();

    //TODO: DELETE! ONLY FOR DEBUGGING.
    void hardCodeWorldInit();
}
