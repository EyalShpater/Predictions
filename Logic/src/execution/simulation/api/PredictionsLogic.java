package execution.simulation.api;

import impl.*;

import javax.xml.bind.JAXBException;
import java.io.InputStream;
import java.util.*;

public interface PredictionsLogic {
    void loadXML(String path) throws JAXBException;

    void loadXML(InputStream file) throws JAXBException;

    List<PropertyDefinitionDTO> getEnvironmentVariablesToSet(String worldName);

    List<PropertyDefinitionDTO> setEnvironmentVariables(List<PropertyDefinitionDTO> variables);

    Map<String, Integer> getEntitiesToPopulation(String worldName);

    WorldDTO getWorldDetails(String worldName);

    Map<Integer, Map<String, Long>> getPopulationPerTickData(int serialNumber);

    Map<String, Map<Integer, Long>> getPopulationCountSortedByName(int serialNumber);

    int runNewSimulation(SimulationInitDataFromUserDTO initData, String worldName, String userName);

    void pauseSimulationBySerialNumber(int serialNumber);

    void stopSimulationBySerialNumber(int serialNumber);

    void resumeSimulationBySerialNumber(int serialNumber);

    boolean isPaused(int serialNumber);

    boolean isStop(int serialNumber);

    boolean isEnded(int serialNumber);

    List<SimulationDTO> getPreviousSimulationsAsDTO();

    SimulationDataDTO getSimulationData(int serialNumber, String entityName, String propertyName);

    SimulationDTO getSimulationDTOBySerialNumber(int serialNumber);

    List<PropertyDefinitionDTO> getEntityPropertiesByEntityName(int serialNumber, String name);

    SimulationInitDataFromUserDTO getUserInputOfSimulationBySerialNumber(int serialNumber);

    SimulationRunDetailsDTO getSimulationRunDetail(int serialNumber);

    EntitiesAmountDTO getSimulationEntitiesAmountMap(int serialNumber);

    SimulationQueueDto getSimulationQueueDetails();

    boolean hasStarted(int serialNumber);

    Map<String, Double> getConsistencyByEntityName(int serialNumber, String entityName);

    Double getFinalNumericPropertyAvg(String entityName, String propertyName, int serialNumber);

    List<String> getAllWorldsNames();

    List<WorldDTO> getAllWorldsDTO();

    GridDTO getGridInformation(String worldName);

    void setThreadPoolSize(int size);

    List<RequestedSimulationDataDTO> getRequestsSimulationDataByUser(String userName);

    void addNewUserRequest(RunRequestDTO request);

    boolean isUserLoggedIn(String userName);

    boolean isUserExist(String userName);

    void loginUser(String userName);

    void logOutUser(String userName);
}
