package execution.simulation.manager;

import api.DTO;
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

    public DTO runNewSimulation(World world) {
        Simulation simulation = new SimulationImpl(world, serialNumber);
        DTO simulationResult;

        simulationResult = simulation.run();
        simulations.put(serialNumber, simulation);
        serialNumber++;

        return simulationResult;
    }
}
