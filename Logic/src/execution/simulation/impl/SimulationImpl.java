package execution.simulation.impl;

import action.context.impl.ContextImpl;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import definition.entity.api.EntityDefinition;
import definition.entity.impl.EntityDefinitionImpl;
import definition.world.impl.WorldImpl;
import execution.simulation.api.Simulation;
import definition.world.api.World;
import execution.simulation.data.api.SimulationData;
import execution.simulation.data.impl.SimulationDataImpl;
import execution.simulation.termination.api.TerminateCondition;
import execution.simulation.termination.impl.TerminationImpl;
import grid.SphereSpaceImpl;
import grid.api.SphereSpace;
import impl.SimulationDTO;
import impl.SimulationDataDTO;
import instance.entity.api.EntityInstance;
import instance.entity.manager.api.EntityInstanceManager;
import instance.entity.manager.impl.EntityInstanceManagerImpl;
import instance.enviornment.api.ActiveEnvironment;
import rule.api.Rule;

import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimulationImpl implements Simulation , Serializable {
    private final int serialNumber;
    private final Random random;

    private World world;
    private EntityInstanceManager entities;
    private ActiveEnvironment environmentVariables;
    private SimulationData data;
    private TerminateCondition endReason;
    private SphereSpace space;
    private long startTime;
    private int tick;
    private boolean userRequestedStop;
    private boolean userRequestedPause;

    public SimulationImpl(World world, int serialNumber) {
        this.world = world;
        this.serialNumber = serialNumber;
        this.random = new Random();
        this.entities = null;
        this.environmentVariables = null;
        this.data = null;
        this.startTime = 0;
        this.space = new SphereSpaceImpl(world.getGridRows(), world.getGridCols());
        this.tick = 0;
    }

    @Override
    public int getSerialNumber() {
        return serialNumber;
    }

    @Override
    public synchronized void run() {
        System.out.println("start simulation " + serialNumber);

        long startPauseTime;
        long endPauseTime;
        startTime = System.currentTimeMillis();

        initEntities();
        initEnvironmentVariables();
        tick = 1;

        while ((endReason = world.isActive(tick, startTime, userRequestedStop)) == null) {
            entities.moveAllEntitiesInSpace(space);
            executeRules(tick);
            tick++;

            boolean print = false;

            startPauseTime = endPauseTime = System.currentTimeMillis();
            while (userRequestedPause && !userRequestedStop) {
                endPauseTime = System.currentTimeMillis();
                print = true;
            }

            if (print) {
                System.out.println("waited for " + (endPauseTime - startPauseTime) + " seconds");
            }
        }

        data = new SimulationDataImpl(serialNumber, startTime, world.getEntities(), entities);
        resetEnvironmentVariables();

        System.out.println("end simulation " + serialNumber);
    }

    @Override
    public TerminateCondition getEndReason() {
        return endReason;
    }

    @Override
    public long getRunStartTime() {
        return startTime;
    }

    @Override
    public ActiveEnvironment setEnvironmentVariables() {
        this.environmentVariables = world.createActiveEnvironment();

        return this.environmentVariables;
    }

    @Override
    public SimulationDataDTO getResultAsDTO(String entityName, String propertyName) {
        return new SimulationDataDTO(
                world.getEntities().size(),
                data.getStarterPopulationQuantity(entityName),
                data.getFinalPopulationQuantity(entityName),
                propertyName != null ?
                        data.getPropertyOfEntityPopulationSortedByValues(entityName, propertyName) :
                        null
                );
    }

    //todo: the loop needs to be inside the manager.
    private void initEntities() {
        EntityInstanceManager instances = new EntityInstanceManagerImpl();

        for (EntityDefinition entityDefinition : world.getEntities()) {
            instances.createInstancesFromDefinition(entityDefinition, space);
        }

        entities = instances;
    }

    private void initEnvironmentVariables() {
        this.environmentVariables = world.createActiveEnvironment();
    }

    private void resetEnvironmentVariables() {
        world.getEnvironmentVariables()
                .forEach(propertyDefinition -> propertyDefinition.setRandom(true));
    }

    //TODO: change this simulation loop
    private void executeRules(int tick) {
        //2)TODO: Execute rules
        for (EntityInstance entity : entities.getInstances()) {
            for (Rule rule : world.getRules()) {
                double probability = random.nextDouble();

                if (rule.isActive(tick, probability)) {
                    rule.invoke(new ContextImpl(entity, entities, environmentVariables));
                }
            }
        }
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public SimulationDTO convertToDTO() {
        return new SimulationDTO(startTime, serialNumber, world.convertToDTO());
    }

    @Override
    public void pause() {
        System.out.println("pause");
        userRequestedPause = true;
    }

    @Override
    public void stop() {
        userRequestedStop = true;
        System.out.println("stop");
    }

    @Override
    public void resume() {
        userRequestedPause = false;
        System.out.println("resuming");
    }

    //todo: only for debug, need to delete!
    public static void main(String[] args) {
        World world = new WorldImpl();
        ExecutorService pool = Executors.newFixedThreadPool(3);

        world.setGridRows(100);
        world.setGridCols(100);
        world.setThreadPoolSize(3);
        world.setTermination(new TerminationImpl());
        world.addEntity(new EntityDefinitionImpl("ent-1", 50));

        SimulationImpl simulation = new SimulationImpl(world, 1);

        pool.execute(simulation::run);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        simulation.pause();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        simulation.resume();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        simulation.stop();

    }
}
