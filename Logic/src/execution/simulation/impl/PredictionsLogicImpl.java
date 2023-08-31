package execution.simulation.impl;

import execution.simulation.api.PredictionsLogic;
import execution.simulation.manager.SimulationManager;
import definition.world.api.World;
import definition.world.impl.WorldImpl;
import execution.simulation.xml.reader.impl.XmlReader;
import execution.simulation.xml.validation.XmlValidator;
import impl.*;
import instance.enviornment.api.ActiveEnvironment;

import javax.xml.bind.JAXBException;
import java.io.Serializable;
import java.util.List;

public class PredictionsLogicImpl implements PredictionsLogic , Serializable {
    private SimulationManager allSimulations;
    private World world;

    public PredictionsLogicImpl() {
        this.allSimulations = new SimulationManager();
    }

    @Override
    public void loadXML(String path) throws JAXBException {
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
    public List<PropertyDefinitionDTO> setEnvironmentVariables(List<PropertyDefinitionDTO> variables) {
        ActiveEnvironment environmentInstances;

        world.setEnvironmentVariablesValues(variables);
        environmentInstances = world.createActiveEnvironment();

        return environmentInstances.convertToDTO();
    }

    @Override
    public WorldDTO getLoadedSimulationDetails() {
        return world.convertToDTO();
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
    public List<PropertyDefinitionDTO> getEntityPropertiesByEntityName(int serialNumber, String name) {
        return allSimulations
                .getSimulationBySerialNumber(serialNumber)
                .getWorld()
                .getEntityByName(name)
                .convertToDTO()
                .getProperties();
    }
}