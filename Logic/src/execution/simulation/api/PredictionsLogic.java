package execution.simulation.api;

import api.DTO;
import impl.PropertyDefinitionDTO;
import impl.WorldDTO;

import java.util.*;

public interface PredictionsLogic {
    boolean loadXML(String path);

    List<PropertyDefinitionDTO> getEnvironmentVariablesToSet();

    WorldDTO getSimulationDetails();

    void runNewSimulation(List<PropertyDefinitionDTO> environmentVariables);

    Collection<DTO> getAllPreviousSimulationData();

    //TODO: DELETE! ONLY FOR DEBUGGING.
    void hardCodeWorldInit();
}
