package execution.simulation.api;

import definition.property.api.PropertyDefinition;
import impl.*;
import instance.property.api.PropertyInstance;

import javax.xml.bind.JAXBException;
import java.util.*;

public interface PredictionsLogic {
    void loadXML(String path) throws JAXBException;

    List<PropertyDefinitionDTO> getEnvironmentVariablesToSet();

    List<PropertyDefinitionDTO> setEnvironmentVariables(List<PropertyDefinitionDTO> variables);

    WorldDTO getLoadedSimulationDetails();

    SimulationRunDetailsDTO runNewSimulation(List<PropertyDefinitionDTO> environmentVariables);

    List<SimulationDTO> getPreviousSimulationsAsDTO();

    SimulationDataDTO getSimulationData(int serialNumber, String entityName, String propertyName);

    SimulationDTO getSimulationDTOBySerialNumber(int serialNumber);

    List<PropertyDefinitionDTO> getEntityPropertiesByEntityName(int serialNumber, String name);
}
