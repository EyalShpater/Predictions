package execution.simulation.api;

import impl.*;

import java.util.*;

public interface PredictionsLogic {
    void loadXML(String path);

    List<PropertyDefinitionDTO> getEnvironmentVariablesToSet();

    WorldDTO getLoadedSimulationDetails();

    SimulationRunDetailsDTO runNewSimulation(List<PropertyDefinitionDTO> environmentVariables);

    List<SimulationDTO> getPreviousSimulationsAsDTO();

    SimulationDataDTO getSimulationData(int serialNumber, String entityName, String propertyName);

    SimulationDTO getSimulationDTOBySerialNumber(int serialNumber);

    List<PropertyDefinitionDTO> getEntityPropertiesByEntityName(String name);
}
