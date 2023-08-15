package execution.simulation.manager;

import api.DTO;
import definition.environment.api.EnvironmentVariableManager;
import definition.environment.impl.EnvironmentVariableManagerImpl;
import execution.simulation.api.Simulation;
import execution.simulation.impl.SimulationImpl;
import definition.world.api.World;
import java.util.*;

public class SimulationManager {
    private int serialNumber;
    private Map<Integer, Simulation> simulations;

    public SimulationManager() {
        serialNumber = 1;
        simulations = new HashMap<>();
    }

    public DTO runNewSimulation(World world, List<DTO> environmentVariables) {
        Simulation simulation;
        DTO simulationResult;

        updateEnvironmentVariablesFromDTO(world, environmentVariables);
        simulation = new SimulationImpl(world, serialNumber);
        simulationResult = simulation.run();
        simulations.put(serialNumber, simulation);
        serialNumber++;

        return simulationResult;
    }

    private void updateEnvironmentVariablesFromDTO(World world, List<DTO> environmentVariables) {
        world.setEnvironmentVariablesValues(environmentVariables);
    }
}
