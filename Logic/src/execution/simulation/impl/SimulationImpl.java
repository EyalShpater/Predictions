package execution.simulation.impl;

import api.DTO;
import definition.entity.api.EntityDefinition;
import definition.environment.api.EnvironmentVariableManager;
import definition.environment.impl.EnvironmentVariableManagerImpl;
import environment.variable.EnvironmentVariableDTO;
import execution.simulation.api.Data;
import execution.simulation.api.Simulation;
import definition.world.api.World;
import instance.entity.api.EntityInstance;
import instance.entity.impl.EntityInstanceImpl;
import instance.enviornment.api.ActiveEnvironment;
import instance.enviornment.impl.ActiveEnvironmentImpl;
import instance.property.impl.PropertyInstanceImpl;

import java.util.ArrayList;
import java.util.List;

public class SimulationImpl implements Simulation {
    private final int serialNumber;
    private World world;
    private Data data;

    public SimulationImpl(World world, int serialNumber) {
        this.world = world;
        this.serialNumber = serialNumber;
        data = null;
    }

    @Override
    public int getSerialNumber() {
        return serialNumber;
    }

    @Override
    public Data getData() {
        return data;
    }

    @Override
    public DTO run(DTO environmentVariables) {
        List<EntityInstance> instances = createEntityInstancesFromWorld();
        ActiveEnvironment environmentVariableInstances = createActiveEnvironmentVariables(environmentVariables);
        DTO simulationResult = runSimulation(instances, environmentVariableInstances);

        return simulationResult;
    }

    private List<EntityInstance> createEntityInstancesFromWorld() {
        List<EntityInstance> instances = new ArrayList<>();

        for (EntityDefinition entity : world.getEntities()) {
            for (int i = 1, population = entity.getPopulation(); i <= population; i++) {
                instances.add(new EntityInstanceImpl(entity, i));
            }
        }

        return instances;
    }

    private ActiveEnvironment createActiveEnvironmentVariables(DTO environmentVariables) {
        EnvironmentVariableManager variableDefinitions = new EnvironmentVariableManagerImpl();
        ActiveEnvironment variableInstances = new ActiveEnvironmentImpl();

        variableDefinitions.mapEnvironmentVariableDTOtoEnvironmentVariableManager(environmentVariables);
        variableDefinitions
                .getEnvironmentVariables()
                .forEach(propertyDefinition ->
                        variableInstances.addPropertyInstance(new PropertyInstanceImpl(propertyDefinition)));

        return variableInstances;
    }

    //TODO: impl
    private DTO runSimulation(List<EntityInstance> entities, ActiveEnvironment environmentVariables) {
        long startTime = System.currentTimeMillis();
        int tick = 0;

        while (world.isActive(tick, startTime)) {

        }

        return null;
    }
}
