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

    int runNewSimulation(SimulationInitDataFromUserDTO initData);

    void pauseSimulationBySerialNumber(int serialNumber);

    void stopSimulationBySerialNumber(int serialNumber);

    void resumeSimulationBySerialNumber(int serialNumber);

    boolean isPaused(int serialNumber);

    boolean isStop(int serialNumber);

    boolean isEnded(int serialNumber);

    double getProgress(int serialNumber);

    List<SimulationDTO> getPreviousSimulationsAsDTO();

    SimulationDataDTO getSimulationData(int serialNumber, String entityName, String propertyName);

    SimulationDTO getSimulationDTOBySerialNumber(int serialNumber);

    List<PropertyDefinitionDTO> getEntityPropertiesByEntityName(int serialNumber, String name);

    SimulationInitDataFromUserDTO getUserInputOfSimulationBySerialNumber(int serialNumber);

    void initSampleInformation();

    SimulationRunDetailsDTO getSimulationRunDetail(int serialNumber);

    SimulationQueueDto getSimulationQueueDetails();
}
