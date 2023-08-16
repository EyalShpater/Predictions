package execution.simulation.data.api;

import java.util.List;

public interface SimulationData {
    long getDataStartTime();
    int getSimulationId();
    int getNumOfDifferentEntities();
    int getStarterPopulationQuantity(String entityName);
    int getFinalPopulationQuantity(String entityName);
    List<Object> getPropertyOfEntityPopulationSortedByValues(String entityName, String propertyName);
}
