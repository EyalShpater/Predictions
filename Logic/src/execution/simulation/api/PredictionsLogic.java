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

    /*SimulationRunDetailsDTO*/ void runNewSimulation(List<PropertyDefinitionDTO> environmentVariables);

    void pauseSimulationBySerialNumber(int serialNumber);

    void stopSimulationBySerialNumber(int serialNumber);

    void resumeSimulationBySerialNumber(int serialNumber);

    List<SimulationDTO> getPreviousSimulationsAsDTO();

    SimulationDataDTO getSimulationData(int serialNumber, String entityName, String propertyName);

    SimulationDTO getSimulationDTOBySerialNumber(int serialNumber);

    List<PropertyDefinitionDTO> getEntityPropertiesByEntityName(int serialNumber, String name);

    void initSampleInformation();
}
