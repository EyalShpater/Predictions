package execution.simulation.impl;

import execution.simulation.api.Data;
import execution.simulation.api.PredictionsLogic;
import execution.simulation.manager.SimulationManager;
import execution.world.api.World;
import execution.world.impl.WorldImpl;

public class PredictionsLogicImpl implements PredictionsLogic {
    SimulationManager allSimulations;
    World world;

    public PredictionsLogicImpl() {
        this.allSimulations = new SimulationManager();
    }

    @Override
    public void loadXML(String path) {
        world = new WorldImpl();
        //read data from file and put it in world.
    }

    @Override
    public void getSimulationDetails() {

    }

    @Override
    public Data runNewSimulation() {
        allSimulations.runNewSimulation(world);
        return null;
    }

    @Override
    public Data getAllPreviousSimulationData() {
        return null;
    }
}
