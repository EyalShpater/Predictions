package execution.simulation.api;

import impl.*;

import javax.xml.bind.JAXBException;
import java.util.*;

public interface PredictionsLogic {
    void loadXML(String path) throws JAXBException;

    List<PropertyDefinitionDTO> getEnvironmentVariablesToSet();

    List<PropertyDefinitionDTO> setEnvironmentVariables(List<PropertyDefinitionDTO> variables);

    Map<String, Integer> getEntitiesToPopulation();

    WorldDTO getLoadedSimulationDetails();

    /*SimulationRunDetailsDTO*/ void runNewSimulation(SimulationInitDataFromUserDTO initData);

    void pauseSimulationBySerialNumber(int serialNumber);

    void stopSimulationBySerialNumber(int serialNumber);

    void resumeSimulationBySerialNumber(int serialNumber);

    List<SimulationDTO> getPreviousSimulationsAsDTO();

    SimulationDataDTO getSimulationData(int serialNumber, String entityName, String propertyName);

    SimulationDTO getSimulationDTOBySerialNumber(int serialNumber);

    List<PropertyDefinitionDTO> getEntityPropertiesByEntityName(int serialNumber, String name);

    void initSampleInformation();

    SimulationRunDetailsDTO getSimulationRunDetail(int serialNumber);
}
