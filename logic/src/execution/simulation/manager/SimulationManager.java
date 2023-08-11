package execution.simulation.manager;

import execution.simulation.api.Simulation;
import execution.simulation.impl.SimulationImpl;
import execution.world.api.World;
import temporary.SomeObject;

import java.util.*;

public class SimulationManager {
    private int serialNumber;
    private Map<Integer, Simulation> simulations;

    public SimulationManager() {
        serialNumber = 1;
        simulations = new HashMap<>();
    }

    public SomeObject runNewSimulation(World world) {
        Simulation simulation = new SimulationImpl(world, serialNumber);
        SomeObject simulationResult;

        simulationResult = simulation.run();
        simulations.put(serialNumber, simulation);
        serialNumber++;

        return simulationResult;
    }
}
