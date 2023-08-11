package execution.simulation.impl;

import execution.simulation.api.Data;
import execution.simulation.api.PredictionsLogic;
import execution.simulation.manager.SimulationManager;
import execution.world.api.World;
import execution.world.impl.WorldImpl;
import temporary.SomeObject;

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
    public SomeObject getEnvironmentVariablesToSet() {
        return null;
    }

    @Override
    public void setEnvironmentVariablesValues(SomeObject variablesValues) {
        world.setEnvironmentVariablesValues(variablesValues);
    }

    @Override
    public SomeObject getSimulationDetails() {
        return null;
    }

    @Override
    public SomeObject runNewSimulation() {
        return allSimulations.runNewSimulation(world);
    }

    @Override
    public SomeObject getAllPreviousSimulationData() {
        return null;
    }
}
