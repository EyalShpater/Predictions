package execution.simulation.manager;

import api.DTO;
import api.DTOConvertible;
import execution.simulation.api.Simulation;
import execution.simulation.impl.SimulationImpl;
import definition.world.api.World;
import execution.simulation.termination.api.TerminateCondition;
import impl.*;
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

    // todo: handle the void situation, maybe its need to return the simulation id?
    public void runNewSimulation(World world, SimulationInitDataFromUserDTO initData) {
        Simulation simulation;
        TerminateCondition stopReason;
        SimulationRunDetailsDTO dto;

        //updateEnvironmentVariablesFromDTO(world, initData.getEnvironmentVariables());
        simulation = new SimulationImpl(world, initData, serialNumber);
        serialNumber++;

        threadPoll.execute(simulation::run);
        //stopReason = simulation.getEndReason();

        simulations.put(simulation.getSerialNumber(), simulation);
//        dto = createRunDetailDTO(stopReason, simulation.getSerialNumber());
//        return dto;
    }

    private void resetEnvironmentVariables() {
        world.getEnvironmentVariables()
                .forEach(propertyDefinition -> propertyDefinition.setRandom(true));
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
