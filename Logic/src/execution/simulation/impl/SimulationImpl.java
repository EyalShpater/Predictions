package execution.simulation.impl;

import action.context.api.Context;
import action.context.impl.ContextImpl;
import api.DTO;
import execution.simulation.api.Simulation;
import definition.world.api.World;
import instance.entity.api.EntityInstance;
import instance.entity.manager.api.EntityInstanceManager;
import instance.entity.manager.impl.EntityInstanceManagerImpl;
import instance.enviornment.api.ActiveEnvironment;
import instance.enviornment.impl.ActiveEnvironmentImpl;
import instance.property.impl.PropertyInstanceImpl;
import rule.api.Rule;

import java.util.Random;

public class SimulationImpl implements Simulation {
    private final int serialNumber;
    private final Random random;
    private World world;
    private EntityInstanceManager entities;
    private ActiveEnvironment environmentVariables;

    public SimulationImpl(World world, int serialNumber) {
        this.world = world;
        this.serialNumber = serialNumber;
        this.random = new Random();
        this.entities = null;
        this.environmentVariables = null;
    }

    @Override
    public int getSerialNumber() {
        return serialNumber;
    }

    @Override
    public DTO run() {
        DTO simulationResult = runSimulation();

        return simulationResult;
    }

    private void initEntities() {
        EntityInstanceManager instances = new EntityInstanceManagerImpl();

        world.getEntities().forEach(instances::create);
        entities = instances;
    }

    private void initEnvironmentVariables() {
        this.environmentVariables = world.createActiveEnvironment();
    }

    //TODO: impl
    private DTO runSimulation() {
        long startTime = System.currentTimeMillis();
        int tick = 0;

        while (world.isActive(tick, startTime)) {
            if (tick == 0) {
                initEntities();
                initEnvironmentVariables();
            }

            executeRules(tick);



            tick++;
        }

        return null;
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
}
