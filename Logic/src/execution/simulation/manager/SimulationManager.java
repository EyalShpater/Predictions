package execution.simulation.manager;

import api.DTOConvertible;
import execution.simulation.api.Simulation;
import execution.simulation.impl.SimulationImpl;
import definition.world.api.World;
import execution.simulation.termination.api.TerminateCondition;
import impl.*;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class SimulationManager implements Serializable {
    private int serialNumber;
    private Map<Integer, Simulation> simulations;
    private ExecutorService threadPool;
    private WorldManager worlds;

    public SimulationManager() {
        this.serialNumber = 1;
        this.simulations = new HashMap<>();
        this.worlds = new WorldManager();
        this.threadPool = Executors.newFixedThreadPool(5); // todo: default value? world.getThreadPoolSize());
    }

    public int runNewSimulation(World world, SimulationInitDataFromUserDTO initData) {
        Simulation simulation;
        TerminateCondition stopReason;
        SimulationRunDetailsDTO dto;

        simulation = new SimulationImpl(world, initData, serialNumber);
        serialNumber++;

        threadPool.execute(simulation::run);

        simulations.put(simulation.getSerialNumber(), simulation);

        return simulation.getSerialNumber();
    }

    public List<SimulationDTO> getAllSimulationsDTO() {
        return simulations.values()
                .stream()
                .map(DTOConvertible::convertToDTO)
                .collect(Collectors.toList());
    }

    public Simulation getSimulationBySerialNumber(int serialNumber){
        return simulations.get(serialNumber);
    }

    public SimulationQueueDto getSimulationQueueDetails() {
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) threadPool;

        return new SimulationQueueDto(
                simulations.size(),
                threadPoolExecutor.getQueue().size(),
                threadPoolExecutor.getActiveCount()
        );
    }

    public void clearAllSimulations() {
        simulations.clear();
    }
}
