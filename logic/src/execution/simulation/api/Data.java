package execution.simulation.api;

import definition.entity.api.EntityInstance;

import java.util.*;

public interface Data {
    List<EntityInstance> getEntities();
    String getEndOfSimulationReason();
    Date getRunTime();

    double getPropertyValueAverage(instance.entity.api.EntityInstance entity, String propertyName);
}
