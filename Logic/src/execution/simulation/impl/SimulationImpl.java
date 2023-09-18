package execution.simulation.impl;

import action.api.Action;
import action.context.impl.ContextImpl;
import definition.entity.api.EntityDefinition;
import execution.simulation.api.Simulation;
import definition.world.api.World;
import execution.simulation.data.api.SimulationData;
import execution.simulation.data.impl.SimulationDataImpl;
import execution.simulation.termination.api.TerminateCondition;
import grid.SphereSpaceImpl;
import grid.api.SphereSpace;
import impl.*;
import instance.entity.api.EntityInstance;
import instance.entity.manager.api.EntityInstanceManager;
import instance.entity.manager.impl.EntityInstanceManagerImpl;
import instance.enviornment.api.ActiveEnvironment;
import instance.enviornment.impl.ActiveEnvironmentImpl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class SimulationImpl implements Simulation , Serializable {
    private final int serialNumber;
    private final Random random;

    private World world;
    private EntityInstanceManager entities;
    private ActiveEnvironment environmentVariables;
    private SimulationData data;
    private TerminateCondition endReason;
    private SphereSpace space;
    private SimulationInitDataFromUserDTO initData;

    private long startTime;
    private long runningTimeInSeconds;
    private long pauseDuration;
    private int tick;
    private boolean isStop;
    private boolean isPause;

    public SimulationImpl(World world, SimulationInitDataFromUserDTO initData, int serialNumber) {
        this.world = world;
        this.initData = initData;
        this.serialNumber = serialNumber;
        this.random = new Random();
        this.entities = null;
        this.environmentVariables = null;
        this.data = null;
        this.startTime = System.currentTimeMillis();
        ;
        this.runningTimeInSeconds = 0;
        this.pauseDuration = 0;
        this.space = new SphereSpaceImpl(world.getGridRows(), world.getGridCols());
        this.tick = 0;
    }

    @Override
    public int getSerialNumber() {
        return serialNumber;
    }

    @Override
    public void run() {
        startTime = System.currentTimeMillis();

        initEntities();
        initEnvironmentVariables();
        tick = 1;

        while ((endReason = world.isActive(tick, runningTimeInSeconds, isStop)) == null) {
            runningTimeInSeconds = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - startTime - pauseDuration);

            entities.moveAllEntitiesInSpace(space);
            executeRules(tick);
            tick++;

            if (isPause) {
                pauseDuration += pauseDuringRunning();
                runningTimeInSeconds = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - startTime - pauseDuration);
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("end of simulation #" + serialNumber);
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

    @Override
    public EntitiesAmountDTO createEntitiesAmountDTO() {
        return new EntitiesAmountDTO(createEntityNameToPopulationFromEntityInstanceManager());
    }

    @Override
    public boolean isStarted() {
        return tick >= 1;
    }

    private Map<String, Integer> createEntityNameToPopulationFromEntityInstanceManager() {
        List<EntityInstance> entityInstances = entities.getInstances();
        Map<String, Integer> entityNameToPopulation = new HashMap<>();

        for (EntityInstance entityInstance : entityInstances) {
            if (entityInstance.isAlive()) {
                String entityName = entityInstance.getName();
                int currentCount = entityNameToPopulation.getOrDefault(entityName, 0);
                entityNameToPopulation.put(entityName, currentCount + 1);
            }
        }

        return entityNameToPopulation;
    }

    public SimulationRunDetailsDTO createRunDetailDTO() {
        return new SimulationRunDetailsDTO(
//                endReason.equals(TerminateCondition.BY_SECONDS),
//                endReason.equals(TerminateCondition.BY_TICKS),
                false,
                false,
                serialNumber,
                tick,
                runningTimeInSeconds,
                getStartProgress(),
                getEndProgress()
        );
    }

    private double getEndProgress() {
        double progress = 0;
        TerminateCondition terminateCondition = world.getTerminationCondition();

        if (!terminateCondition.equals(TerminateCondition.BY_USER)) {
            progress = terminateCondition.equals(TerminateCondition.BY_SECONDS) ?
                    world.getTermination().getSecondsToTerminate() :
                    world.getTermination().getTicksToTerminate();
        }

        return progress;
    }

    private double getStartProgress() {
        double progress = 0;
        TerminateCondition terminateCondition = world.getTerminationCondition();

        if (!terminateCondition.equals(TerminateCondition.BY_USER)) {
            progress = terminateCondition.equals(TerminateCondition.BY_SECONDS) ?
                    runningTimeInSeconds :
                    tick;
        }

        return progress;
    }

    @Override
    public SimulationInitDataFromUserDTO getUserInputDTO() {
        return initData;
    }

    private void initEntities() {
        int population;
        EntityInstanceManager instances = new EntityInstanceManagerImpl();

        for (EntityDefinition entityDefinition : world.getEntities()) {
            population = initData
                    .getEntityNameToPopulation()
                    .get(entityDefinition.getName());
            instances.createInstancesFromDefinition(entityDefinition, population, space);
        }

        entities = instances;
    }

    private synchronized void initEnvironmentVariables() {
        environmentVariables = new ActiveEnvironmentImpl(initData.getEnvironmentVariables());
    }

    private void resetEnvironmentVariables() {
        world.getEnvironmentVariables()
                .forEach(propertyDefinition -> propertyDefinition.setRandom(true));
    }

    private void executeRules(int tick) {
        List<Action> activeActions = createActionToInvokeList(tick);

        entities.getInstances().stream()
                .filter(EntityInstance::isAlive)
                .forEach(entity -> invokeActionsOnEntity(entity, activeActions));
    }

    @Override
    public World getWorld() {
        return world;
    }

    //todo: maybe delete
    @Override
    public double getProgress() {
        double progress = 0;
        TerminateCondition terminateCondition = world.getTerminationCondition();

        if (!terminateCondition.equals(TerminateCondition.BY_USER)) {
            progress = terminateCondition.equals(TerminateCondition.BY_SECONDS) ?
                    (System.currentTimeMillis() - startTime - pauseDuration) :
                    world.getTermination().getTicksToTerminate() - tick;
        }

        return progress;
    }


    @Override
    public SimulationDTO convertToDTO() {
        return new SimulationDTO(startTime, serialNumber, world.convertToDTO());
    }

    @Override
    public void pause() {
        isPause = true;
        System.out.println("pause " + serialNumber);
    }

    @Override
    public void stop() {
        System.out.println("stop " + serialNumber);
        isStop = true;
        resume();
    }

    @Override
    public void resume() {
        isPause = false;
        System.out.println("resume " + serialNumber);
        synchronized (this) {
            this.notifyAll();
        }
    }

    @Override
    public boolean isPaused() {
        return isPause;
    }

    @Override
    public boolean isStop() {
        return isStop;
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

    @Override
    public boolean isEnded() {
        return endReason != null;
    }

    private List<Action> createActionToInvokeList(int tick) {
        return world.getRules().stream()
                .filter(rule -> rule.isActive(tick, random.nextDouble()))
                .flatMap(rule -> rule.getActions().stream())
                .collect(Collectors.toList());
    }

    private void invokeActionsOnEntity(EntityInstance entity, List<Action> activeActions) {
        activeActions.stream()
                .filter(action -> action
                        .applyOn()
                        .getName()
                        .equals(entity.getName())
                )
                .forEach(action -> action.invoke(new ContextImpl(entity, entities, environmentVariables, tick)));
    }
}
