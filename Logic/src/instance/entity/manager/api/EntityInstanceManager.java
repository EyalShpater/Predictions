package instance.entity.manager.api;

import action.context.api.Context;
import definition.entity.api.EntityDefinition;
import grid.api.SphereSpace;
import instance.entity.api.EntityInstance;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public interface EntityInstanceManager {
    void createMultipleInstancesFromDefinition(EntityDefinition entityDefinition, int population, SphereSpace space);

    List<EntityInstance> getInstances();

    void moveAllEntitiesInSpace(SphereSpace space);

    void createNewEntityInstanceFromScratch(EntityDefinition entityToCreate, SphereSpace space);

    void createNewEntityInstanceWithSamePropertyValues(EntityInstance entityToCopy, EntityDefinition entityToCreate, Context context);

    Map<String, Long> getPopulationCountByTick(int tick);

    Map<Integer, Map<String, Long>> getPopulationCountSortedByTick();

    Map<String, Map<Integer, Long>> getPopulationCountSortedByByName();

    void updatePopulationCount(int tick, Map<String, Long> entitiesToPopulation);
}
