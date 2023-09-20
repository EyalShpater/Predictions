package instance.entity.manager.impl;

import action.context.api.Context;
import definition.entity.api.EntityDefinition;
import grid.api.Location;
import grid.api.SphereSpace;
import instance.entity.api.EntityInstance;
import instance.entity.impl.EntityInstanceImpl;
import instance.entity.manager.api.EntityInstanceManager;
import instance.entity.manager.api.PopulationCounter;
import javafx.util.Pair;

import java.io.Serializable;
import java.util.*;
import java.util.stream.IntStream;

public class EntityInstanceManagerImpl implements EntityInstanceManager , Serializable {
    private int id;
    private Map<Integer, EntityInstance> instances;
    private PopulationCounter populationCounter;

    public EntityInstanceManagerImpl() {
        instances = new HashMap<>();
        populationCounter = new PopulationCounter(this);
        id = 1;
    }

    @Override
    public void createMultipleInstancesFromDefinition(EntityDefinition entityDefinition, int population, SphereSpace space) {
        IntStream.rangeClosed(1, population)
                .forEach(i -> createNewEntityInstanceFromScratch(entityDefinition, space));
    }


    @Override
    public void createNewEntityInstanceFromScratch(EntityDefinition entityToCreate, SphereSpace space) {
        EntityInstance newInstance = new EntityInstanceImpl(entityToCreate, id);
        Location placeInSpace = space.placeEntityRandomlyInWorld(newInstance);
        if (placeInSpace == null) {
            throw new IllegalArgumentException(
                    "There is no place to add "
                            + newInstance.getName()
                            + " id #"
                            + newInstance.getId()
                            + " to the sphere space."
            );
        }

        newInstance.setLocationInSpace(placeInSpace);
        newInstance.setSpace(space);
        instances.put(id, newInstance);

        id++;
    }

    @Override
    public void createNewEntityInstanceWithSamePropertyValues(EntityInstance entityToCopy, EntityDefinition entityToCreate, Context context) {
        EntityInstance newInstance = new EntityInstanceImpl(entityToCreate, id);
        Location placeInSpace = entityToCopy.getLocationInSpace();
        if (placeInSpace == null) {
            throw new IllegalArgumentException(
                    "There is no place to add "
                            + newInstance.getName()
                            + " id #"
                            + newInstance.getId()
                            + " to the sphere space."
            );
        }
        newInstance.copyPropertiesFrom(entityToCopy, context);

        newInstance.setLocationInSpace(placeInSpace);
        newInstance.setSpace(entityToCopy.getSpace());
        instances.put(id, newInstance);

        id++;
    }

    @Override
    public List<EntityInstance> getInstances() {
        return new ArrayList<>(instances.values());
    }

    @Override
    public void moveAllEntitiesInSpace(SphereSpace space) {
        instances
                .values()
                .stream()
                .filter(EntityInstance::isAlive)
                .forEach(space::makeRandomMove);
    }

    @Override
    public Map<String, Long> getPopulationCountByTick(int tick) {
        return populationCounter.getPopulationCounterByTick(tick);
    }

    @Override
    public Map<Integer, Map<String, Long>> getPopulationCountSortedByTick() {
        return populationCounter.getPopulationCounterByTick();
    }

    @Override
    public Map<String, Map<Integer, Long>> getPopulationCountSortedByByName() {
        return populationCounter.getPopulationCounterByName();
    }

    @Override
    public void updatePopulationCount(int tick, Map<String, Long> entitiesToPopulation) {
        populationCounter.update(tick, entitiesToPopulation);
    }

    @Override
    public Double getFinalNumericPropertyAvg(String entityName, String propertyName) {
        double sum = 0;
        int count = 0;

        for (EntityInstance entityInstance : instances.values()) {
            if (entityInstance.getName().equals(entityName) && entityInstance.isAlive()) {
                if (entityInstance.getPropertyByName(propertyName).getValue() instanceof Integer) {
                    sum += (Integer) entityInstance.getPropertyByName(propertyName).getValue();
                    count++;
                } else if (entityInstance.getPropertyByName(propertyName).getValue() instanceof Double) {
                    sum += (Double) entityInstance.getPropertyByName(propertyName).getValue();
                    count++;
                } else {
                    return null;
                }
            }
            
        }

        return sum / count;
    }
}
