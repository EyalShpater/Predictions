package execution.simulation.impl;

import execution.simulation.api.PredictionsLogic;
import execution.simulation.manager.SimulationManager;
import definition.world.api.World;
import definition.world.impl.WorldImpl;
import execution.simulation.manager.WorldManager;
import execution.simulation.xml.reader.impl.XmlReader;
import execution.simulation.xml.validation.XmlValidator;
import impl.*;
import instance.enviornment.api.ActiveEnvironment;
import instance.enviornment.impl.ActiveEnvironmentImpl;
import javafx.util.Pair;

import javax.xml.bind.JAXBException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PredictionsLogicImpl implements PredictionsLogic , Serializable {
    private static final int DEFAULT_START_POPULATION = 0;

    private SimulationManager allSimulations = new SimulationManager();
    private WorldManager worlds = new WorldManager();

    @Override
    public void loadXML(String path) throws JAXBException {
        World newWorld = new WorldImpl();
        XmlValidator validator = new XmlValidator(path);
        XmlReader reader;

        validator.isValid();
        reader = new XmlReader(validator.getWorld());
        reader.readXml(newWorld);

        worlds.addWorld(newWorld);
    }

    public void loadXML(InputStream file) throws JAXBException {
        XmlValidator validator = new XmlValidator(file);
        processXmlData(validator);
    }

    @Override
    public List<PropertyDefinitionDTO> getEnvironmentVariablesToSet(String worldName) {
        return worlds
                .getWorld(worldName)
                .getEnvironmentVariablesDTO();
    }

    @Override
    public List<PropertyDefinitionDTO> setEnvironmentVariables(List<PropertyDefinitionDTO> variables) {
        ActiveEnvironment environmentInstances = new ActiveEnvironmentImpl(variables);

        return environmentInstances.convertToDTO();
    }

    @Override
    public Map<String, Integer> getEntitiesToPopulation(String worldName) {
        Map<String, Integer> entitiesNameToPopulation = new HashMap<>();

        worlds
                .getWorld(worldName)
                .getEntities()
                .forEach(entity -> entitiesNameToPopulation.put(entity.getName(), DEFAULT_START_POPULATION));

        return entitiesNameToPopulation;
    }

    @Override
    public WorldDTO getLoadedSimulationDetails(String worldName) {
        return worlds
                .getWorld(worldName)
                .convertToDTO();
    }

    @Override
    public boolean hasStarted(int serialNumber) {
        return allSimulations.getSimulationBySerialNumber(serialNumber).isStarted();
    }

    @Override
    public Map<String, Double> getConsistencyByEntityName(int serialNumber, String entityName) {
        return allSimulations.getSimulationBySerialNumber(serialNumber).getConsistencyByEntityName(entityName);
    }

    @Override
    public Map<Integer, Map<String, Long>> getPopulationPerTickData(int serialNumber) {
        return allSimulations.getSimulationBySerialNumber(serialNumber).getPopulationPerTickData();
    }

    @Override
    public Map<String, Map<Integer, Long>> getPopulationCountSortedByName(int serialNumber) {
        return allSimulations.getSimulationBySerialNumber(serialNumber).getPopulationCountSortedByName();
    }

    @Override
    public int runNewSimulation(SimulationInitDataFromUserDTO initData, String worldName) {
        return allSimulations.runNewSimulation(worlds.getWorld(worldName), initData);
    }

    @Override
    public void pauseSimulationBySerialNumber(int serialNumber) {
        allSimulations.getSimulationBySerialNumber(serialNumber).pause();
    }

    @Override
    public void stopSimulationBySerialNumber(int serialNumber) {
        allSimulations.getSimulationBySerialNumber(serialNumber).stop();
    }

    @Override
    public void resumeSimulationBySerialNumber(int serialNumber) {
        allSimulations.getSimulationBySerialNumber(serialNumber).resume();
    }

    @Override
    public boolean isPaused(int serialNumber) {
        return allSimulations
                .getSimulationBySerialNumber(serialNumber)
                .isPaused();
    }

    @Override
    public boolean isStop(int serialNumber) {
        return allSimulations
                .getSimulationBySerialNumber(serialNumber)
                .isStop();
    }

    @Override
    public boolean isEnded(int serialNumber) {
        return allSimulations
                .getSimulationBySerialNumber(serialNumber)
                .isEnded();
    }

    @Override
    public List<SimulationDTO> getPreviousSimulationsAsDTO() {
        return allSimulations.getAllSimulationsDTO();
    }

    @Override
    public SimulationDataDTO getSimulationData(int serialNumber, String entityName, String propertyName) {
        return allSimulations
                .getSimulationBySerialNumber(serialNumber)
                .getResultAsDTO(entityName, propertyName);
    }

    @Override
    public SimulationDTO getSimulationDTOBySerialNumber(int serialNumber) {
        return allSimulations
                .getSimulationBySerialNumber(serialNumber)
                .convertToDTO();
    }

    @Override
    public List<PropertyDefinitionDTO> getEntityPropertiesByEntityName(int serialNumber, String name) {
        return allSimulations
                .getSimulationBySerialNumber(serialNumber)
                .getWorld()
                .getEntityByName(name)
                .convertToDTO()
                .getProperties();
    }

    @Override
    public SimulationInitDataFromUserDTO getUserInputOfSimulationBySerialNumber(int serialNumber) {
        return allSimulations
                .getSimulationBySerialNumber(serialNumber)
                .getUserInputDTO();
    }

    @Override
    public EntitiesAmountDTO getSimulationEntitiesAmountMap(int serialNumber) {
        return allSimulations
                .getSimulationBySerialNumber(serialNumber)
                .createEntitiesAmountDTO();
    }


    @Override
    public SimulationRunDetailsDTO getSimulationRunDetail(int serialNumber) {
        return allSimulations
                .getSimulationBySerialNumber(serialNumber)
                .createRunDetailDTO();
    }

    @Override
    public SimulationQueueDto getSimulationQueueDetails() {

        return allSimulations == null ? null : allSimulations.getSimulationQueueDetails();
    }

    @Override
    public Double getFinalNumericPropertyAvg(String entityName, String propertyName, int serialNumber) {
        return allSimulations != null ?
                allSimulations.getSimulationBySerialNumber(serialNumber).getFinalNumericPropertyAvg(entityName, propertyName)
                : null;

    }

    @Override
    public List<String> getAllWorldsNames() {
        return worlds.getAllWorldsNames();
    }

    @Override
    public List<WorldDTO> getAllWorldsDTO() {
        return worlds.getAllWorldsAsDTO();
    }

    @Override
    public GridDTO getGridInformation(String worldName) {
        World slectedWorld = worlds.getWorld(worldName);

        return new GridDTO(slectedWorld.getGridRows(), slectedWorld.getGridCols());
    }

    private void processXmlData(XmlValidator validator) throws JAXBException {
        World newWorld = new WorldImpl();
        XmlReader reader;

        validator.isValid();
        reader = new XmlReader(validator.getWorld());
        reader.readXml(newWorld);

        boolean isAccepted = worlds.addWorld(newWorld);

        if (!isAccepted) {
            throw new IllegalArgumentException("World name \"" + newWorld.getName() + "\" is already exist!");
        }
    }
}