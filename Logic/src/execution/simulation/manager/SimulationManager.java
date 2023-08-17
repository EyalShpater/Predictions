package execution.simulation.manager;

import api.DTO;
import execution.simulation.api.Simulation;
import execution.simulation.data.api.SimulationData;
import execution.simulation.impl.SimulationImpl;
import definition.world.api.World;
import impl.PropertyDefinitionDTO;

import java.util.*;

public class SimulationManager {
    private int serialNumber;
    private Map<Integer, Simulation> simulations;

    public SimulationManager() {
        serialNumber = 1;
        simulations = new HashMap<>();
    }

    public void runNewSimulation(World world, List<PropertyDefinitionDTO> environmentVariables) {
        Simulation simulation;

        updateEnvironmentVariablesFromDTO(world, environmentVariables);
        simulation = new SimulationImpl(world, serialNumber);
        simulation.run();
        simulations.put(serialNumber, simulation);
        serialNumber++;
    }

    private void updateEnvironmentVariablesFromDTO(World world, List<PropertyDefinitionDTO> environmentVariables) {
        world.setEnvironmentVariablesValues(environmentVariables);
    }
}
