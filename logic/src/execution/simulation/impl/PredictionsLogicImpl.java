package execution.simulation.impl;

import api.DTO;
import execution.simulation.api.PredictionsLogic;
import execution.simulation.manager.SimulationManager;
import definition.world.api.World;
import definition.world.impl.WorldImpl;
import java.util.List;

public class PredictionsLogicImpl implements PredictionsLogic {
    private SimulationManager allSimulations;
    private World world;

    public PredictionsLogicImpl() {
        this.allSimulations = new SimulationManager();
    }

    @Override
    public void loadXML(String path) {
        world = new WorldImpl();
        //read data from file and put it in world.
    }

    @Override
    public List<DTO> getEnvironmentVariablesToSet() {
        return null;
    }

    @Override
    public void setEnvironmentVariablesValues(List<DTO> variablesValues) {
        world.setEnvironmentVariablesValues(variablesValues);
    }

    @Override
    public DTO getSimulationDetails() {
        return null;
    }

    @Override
    public DTO runNewSimulation(DTO environmentVariables) {
        return allSimulations.runNewSimulation(world, environmentVariables);
    }

    @Override
    public List<DTO> getAllPreviousSimulationData() {
        return null;
    }
}
