package execution.simulation.api;

import definition.entity.api.EntityDefinition;
import instance.entity.api.EntityInstance;

import java.util.*;

public interface Data {
    List<EntityDefinition> getEntities();
    String getEndOfSimulationReason();
    Date getRunTime();
    double getPropertyValueAverage(EntityInstance entity, String propertyName);
}
