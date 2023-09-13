package execution.simulation.impl;

import action.context.impl.ContextImpl;
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
    private long timeOfActive;
    private int tick;
    private boolean isStop;
    private boolean isPause;

    public SimulationImpl(World world, int serialNumber) {
        this.world = world;
        this.serialNumber = serialNumber;
        this.random = new Random();
        this.entities = null;
        this.environmentVariables = null;
        this.data = null;
        this.startTime = 0;
        this.timeOfActive = 0;
        this.space = new SphereSpaceImpl(world.getGridRows(), world.getGridCols());
        this.tick = 0;
    }

    @Override
    public int getSerialNumber() {
        return serialNumber;
    }

    @Override
    public void run() {
        timeOfActive = startTime = System.currentTimeMillis();

        initEntities();
        initEnvironmentVariables();
        tick = 1;

        while ((endReason = world.isActive(tick, timeOfActive, isStop)) == null) {
            entities.moveAllEntitiesInSpace(space);
            executeRules(tick);
            tick++;

            if (isPause) {
                long pauseDuration = pauseDuringRunning();
                timeOfActive += pauseDuration;
            }
        }

        data = new SimulationDataImpl(serialNumber, startTime, world.getEntities(), entities);
        resetEnvironmentVariables();
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

                if (entity.isAlive() && rule.isActive(tick, probability)) {
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
        isPause = true;
    }

    @Override
    public void stop() {
        isStop = true;
        isPause = false;

        synchronized (this) {
            this.notifyAll();
        }
    }

    @Override
    public void resume() {
        isPause = false;

        synchronized (this) {
            this.notifyAll();
        }
    }

    private long pauseDuringRunning() {
        long startTime = System.currentTimeMillis();

        synchronized (this) {
            while (isPause) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    stop();
                }
            }
        }

        return System.currentTimeMillis() - startTime;
    }
}
