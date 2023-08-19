package execution.simulation.api;

import impl.PropertyDefinitionDTO;
import impl.SimulationDTO;
import impl.SimulationDataDTO;
import impl.WorldDTO;

import java.util.*;

public interface PredictionsLogic {
    void loadXML(String path);

    List<PropertyDefinitionDTO> getEnvironmentVariablesToSet();

    WorldDTO getLoadedSimulationDetails();

    void runNewSimulation(List<PropertyDefinitionDTO> environmentVariables);

    List<SimulationDTO> getPreviousSimulationsAsDTO();

    SimulationDataDTO getSimulationData(int serialNumber, String entityName, String propertyName);

    SimulationDTO getSimulationDTOBySerialNumber(int serialNumber);

    List<PropertyDefinitionDTO> getEntityPropertiesByEntityName(String name);

    //TODO: DELETE! ONLY FOR DEBUGGING.
    void hardCodeWorldInit();
}
