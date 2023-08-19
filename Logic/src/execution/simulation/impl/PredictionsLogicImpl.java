package execution.simulation.impl;

import execution.simulation.api.PredictionsLogic;
import execution.simulation.manager.SimulationManager;
import definition.world.api.World;
import definition.world.impl.WorldImpl;
import execution.simulation.xml.reader.impl.XmlReader;
import execution.simulation.xml.validation.XmlValidator;
import impl.*;

import java.util.List;

public class PredictionsLogicImpl implements PredictionsLogic {
    private SimulationManager allSimulations;
    private World world;

    public PredictionsLogicImpl() {
        this.allSimulations = new SimulationManager();
    }

    @Override
    public void loadXML(String path) {
        World newWorld = new WorldImpl();
        XmlValidator validator = new XmlValidator(path);
        XmlReader reader;

        validator.isValid();
        reader = new XmlReader(validator.getWorld());
        reader.readXml(newWorld);
        world = newWorld;
    }

    @Override
    public List<PropertyDefinitionDTO> getEnvironmentVariablesToSet() {
        return world.getEnvironmentVariablesDTO();
    }

    @Override
    public WorldDTO getLoadedSimulationDetails() {
        return (WorldDTO) world.convertToDTO();
    }

    @Override
    public SimulationRunDetailsDTO runNewSimulation(List<PropertyDefinitionDTO> environmentVariables) {
        return allSimulations.runNewSimulation(world, environmentVariables);
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
    public List<PropertyDefinitionDTO> getEntityPropertiesByEntityName(String name) {
        return world.getEntityByName(name)
                .convertToDTO()
                .getProperties();
    }
}