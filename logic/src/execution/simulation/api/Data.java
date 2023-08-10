package execution.simulation.api;

import definition.entity.api.EntityDefinition;

import java.util.*;

public interface Data {
    List<EntityDefinition> getEntities();
    String getEndOfSimulationReason();
    Date getRunTime();

    double getPropertyValueAverage(instance.entity.api.EntityInstance entity, String propertyName);
}
