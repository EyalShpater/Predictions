package execution.simulation.manager;

import api.DTO;
import api.DTOConvertible;
import execution.simulation.api.Simulation;
import execution.simulation.impl.SimulationImpl;
import definition.world.api.World;
import execution.simulation.termination.api.TerminateCondition;
import impl.PropertyDefinitionDTO;
import impl.SimulationDTO;
import impl.SimulationDataDTO;
import impl.SimulationRunDetailsDTO;
import instance.property.api.PropertyInstance;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class SimulationManager implements Serializable {
    private int serialNumber;
    private Map<Integer, Simulation> simulations;
    private ExecutorService threadPoll;
    private World world;

    public SimulationManager(World world) {
        this.serialNumber = 1;
        this.simulations = new HashMap<>();
        this.world = world;
        this.threadPoll = Executors.newFixedThreadPool(world.getThreadPoolSize());
    }

    public void runNewSimulation(World world, List<PropertyDefinitionDTO> environmentVariables) {
        Simulation simulation;
        TerminateCondition stopReason;
        SimulationRunDetailsDTO dto;

        updateEnvironmentVariablesFromDTO(world, environmentVariables);
        simulation = new SimulationImpl(world, serialNumber);
        serialNumber++;

        System.out.println("Adding simulation #" + (serialNumber - 1) + " to thread pool");
        threadPoll.execute(simulation::run);
        //stopReason = simulation.getEndReason();

        simulations.put(simulation.getSerialNumber(), simulation);
//        dto = createRunDetailDTO(stopReason, simulation.getSerialNumber());
//        return dto;
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

    private SimulationRunDetailsDTO createRunDetailDTO(TerminateCondition condition, int serialNumber) {
        return new SimulationRunDetailsDTO(
                condition.equals(TerminateCondition.BY_SECONDS),
                condition.equals(TerminateCondition.BY_TICKS),
                serialNumber
        );
    }

    public void clearAllSimulations() {
        simulations.clear();
    }
}
