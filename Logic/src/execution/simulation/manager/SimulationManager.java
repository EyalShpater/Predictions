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

public class SimulationManager extends Observable implements Serializable, Observer {
    private final static int DEFAULT_THREAD_POOL_SIZE = 5;

    private int serialNumber;
    private Map<Integer, Simulation> simulations;
    private ExecutorService threadPool;
    private WorldManager worlds;

    private int poolSize;

    public SimulationManager() {
        this.serialNumber = 1;
        this.simulations = new HashMap<>();
        this.worlds = new WorldManager();
        this.poolSize = DEFAULT_THREAD_POOL_SIZE;
        this.threadPool = Executors.newFixedThreadPool(poolSize);
    }

    public int runNewSimulation(World world, SimulationInitDataFromUserDTO initData) {
        Simulation simulation;
//        TerminateCondition stopReason; todo: can i delete?
//        SimulationRunDetailsDTO dto;

        simulation = new SimulationImpl(world, initData, serialNumber);
        simulation.addObserver(this);
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
                threadPoolExecutor.getActiveCount(),
                poolSize
        );
    }

    public void clearAllSimulations() {
        simulations.clear();
    }

    public void setThreadPoolSize(int size) {
        this.poolSize = size;
        this.threadPool.shutdownNow();
        this.threadPool = Executors.newFixedThreadPool(poolSize);
    }

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }
}
