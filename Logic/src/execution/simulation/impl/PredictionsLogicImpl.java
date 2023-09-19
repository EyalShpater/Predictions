package execution.simulation.impl;

import action.impl.IncreaseAction;
import action.impl.MultiplyAction;
import api.DTOConvertible;
import definition.entity.api.EntityDefinition;
import definition.entity.impl.EntityDefinitionImpl;
import definition.property.api.PropertyType;
import definition.property.impl.PropertyDefinitionImpl;
import execution.simulation.api.PredictionsLogic;
import execution.simulation.manager.SimulationManager;
import definition.world.api.World;
import definition.world.impl.WorldImpl;
import execution.simulation.termination.api.TerminateCondition;
import execution.simulation.termination.impl.TerminationImpl;
import execution.simulation.xml.reader.impl.XmlReader;
import execution.simulation.xml.validation.XmlValidator;
import impl.*;
import instance.entity.api.EntityInstance;
import instance.enviornment.api.ActiveEnvironment;
import instance.enviornment.impl.ActiveEnvironmentImpl;
import rule.api.Rule;
import rule.impl.ActivationImpl;
import rule.impl.RuleImpl;

import javax.xml.bind.JAXBException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PredictionsLogicImpl implements PredictionsLogic , Serializable {
    private static final int DEFAULT_START_POPULATION = 0;

    private SimulationManager allSimulations;
    private World world;

    @Override
    public void loadXML(String path) throws JAXBException {
        World newWorld = new WorldImpl();
        XmlValidator validator = new XmlValidator(path);
        XmlReader reader;

        validator.isValid();
        reader = new XmlReader(validator.getWorld());
        reader.readXml(newWorld);

        world = newWorld;
        allSimulations = new SimulationManager(world);
    }

    @Override
    public List<PropertyDefinitionDTO> getEnvironmentVariablesToSet() {
        return world.getEnvironmentVariablesDTO();
    }

    @Override
    public List<PropertyDefinitionDTO> setEnvironmentVariables(List<PropertyDefinitionDTO> variables) {
        ActiveEnvironment environmentInstances = new ActiveEnvironmentImpl(variables);

        return environmentInstances.convertToDTO();
    }

    @Override
    public Map<String, Integer> getEntitiesToPopulation() {
        Map<String, Integer> entitiesNameToPopulation = new HashMap<>();

        world
                .getEntities()
                .forEach(entity -> entitiesNameToPopulation.put(entity.getName(), DEFAULT_START_POPULATION));

        return entitiesNameToPopulation;
    }

    @Override
    public WorldDTO getLoadedSimulationDetails() {
        return world.convertToDTO();
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
    public int runNewSimulation(SimulationInitDataFromUserDTO initData) {
        return allSimulations.runNewSimulation(world, initData);
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
    public double getProgress(int serialNumber) {
        return allSimulations.getSimulationBySerialNumber(serialNumber).getProgress();
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
}