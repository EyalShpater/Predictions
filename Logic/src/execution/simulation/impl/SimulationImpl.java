package execution.simulation.impl;

import action.context.impl.ContextImpl;
import definition.entity.api.EntityDefinition;
import execution.simulation.api.Simulation;
import definition.world.api.World;
import execution.simulation.data.api.SimulationData;
import execution.simulation.data.impl.SimulationDataImpl;
import impl.SimulationDTO;
import impl.SimulationDataDTO;
import instance.entity.api.EntityInstance;
import instance.entity.manager.api.EntityInstanceManager;
import instance.entity.manager.impl.EntityInstanceManagerImpl;
import instance.enviornment.api.ActiveEnvironment;
import rule.api.Rule;

import java.util.Random;

public class SimulationImpl implements Simulation {
    private final int serialNumber;
    private final Random random;

    private World world;
    private EntityInstanceManager entities;
    private ActiveEnvironment environmentVariables;
    private SimulationData data;
    private long startTime;

    public SimulationImpl(World world, int serialNumber) {
        this.world = world;
        this.serialNumber = serialNumber;
        this.random = new Random();
        this.entities = null;
        this.environmentVariables = null;
        this.data = null;
        this.startTime = 0;
    }

    @Override
    public int getSerialNumber() {
        return serialNumber;
    }

    @Override
    public void run() {
        int tick = 1;

        startTime = System.currentTimeMillis();
        initEntities();
        initEnvironmentVariables();

        while (world.isActive(tick, startTime)) {
            executeRules(tick);
            tick++;
        }

        data = new SimulationDataImpl(serialNumber, startTime, world.getEntities(), entities);
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

    private void initEntities() {
        EntityInstanceManager instances = new EntityInstanceManagerImpl();

        for (EntityDefinition entityDefinition : world.getEntities()) {
            for (int i = 1; i <= entityDefinition.getPopulation(); i++) {
                instances.create(entityDefinition);
            }
        }

        entities = instances;
    }

    private void initEnvironmentVariables() {
        this.environmentVariables = world.createActiveEnvironment();
    }

    private void executeRules(int tick) {
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
    public SimulationDTO convertToDTO() {
        return new SimulationDTO(startTime, serialNumber, world.convertToDTO());
    }
}
