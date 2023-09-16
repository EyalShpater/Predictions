package execution.simulation.data.impl;

import definition.entity.api.EntityDefinition;
import execution.simulation.data.api.SimulationData;
import impl.SimulationInitDataFromUserDTO;
import instance.entity.api.EntityInstance;
import instance.entity.manager.api.EntityInstanceManager;
import instance.property.api.PropertyInstance;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SimulationDataImpl implements SimulationData , Serializable {

    int id;
    long startTime;
    Map<String, EntityDefinition> entityDefinitions;
    EntityInstanceManager entityInstances;
    SimulationInitDataFromUserDTO initData;

    public SimulationDataImpl(int id, long startTime, List<EntityDefinition> entityDefinitions, EntityInstanceManager entityInstances) {
        this.id = id;
        this.startTime = startTime;
        this.entityDefinitions = new HashMap<>();
        entityDefinitions.forEach(entity -> this.entityDefinitions.put(entity.getName(), entity));
        this.entityInstances = entityInstances;
    }

    @Override
    public long getDataStartTime() {
        return startTime;
    }

    @Override
    public int getSimulationId() {
        return id;
    }

    @Override
    public int getNumOfDifferentEntities() {
        return entityDefinitions.size();
    }

    @Override
    public int getStarterPopulationQuantity(String entityName) {
        return initData
                .getEntityNameToPopulation()
                .get(entityName);
    }

    @Override
    public int getFinalPopulationQuantity(String entityName) {
        return (int) entityInstances.
                getInstances().
                stream().
                filter(entityInstance -> entityInstance.getName().equals(entityName)).
                filter(EntityInstance::isAlive).
                count();
    }

    @Override
    public List<Object> getPropertyOfEntityPopulationSortedByValues(String entityName, String propertyName) {
        EntityDefinition entity = getEntityByName(entityName);

        if (entity == null) {
            throw new IllegalArgumentException(entityName + " does not exist!");
        }

        if (!isPropertyNameValid(entity, propertyName)) {
            throw new IllegalArgumentException(propertyName + " does not exist");
        }

        return entityInstances.
                getInstances().
                stream().
                filter(EntityInstance::isAlive).
                map(entityInstance -> entityInstance.getPropertyByName(propertyName)).
                map(PropertyInstance::getValue)
                .sorted().
                collect(Collectors.toList());
    }

    private EntityDefinition getEntityByName(String name) {
        return entityDefinitions.get(name);
    }

    private boolean isPropertyNameValid(EntityDefinition entity, String propertyName) {
        return entity.getPropertyByName(propertyName) != null;
    }
}
