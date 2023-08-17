package execution.simulation.manager;

import api.DTOConvertible;
import execution.simulation.api.Simulation;
import execution.simulation.impl.SimulationImpl;
import definition.world.api.World;
import impl.PropertyDefinitionDTO;
import impl.SimulationDTO;
import impl.SimulationDataDTO;

import java.util.*;
import java.util.stream.Collectors;

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

    public List<SimulationDTO> getAllSimulationsDTO() {
        return simulations.values()
                .stream()
                .map(DTOConvertible::convertToDTO)
                .collect(Collectors.toList());
    }


    private void updateEnvironmentVariablesFromDTO(World world, List<PropertyDefinitionDTO> environmentVariables) {
        world.setEnvironmentVariablesValues(environmentVariables);
    }

    public Simulation getSimulationBySerialNumber(int serialNumber){
        return simulations.get(serialNumber);
    }
}
