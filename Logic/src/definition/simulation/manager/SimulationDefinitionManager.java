package definition.simulation.manager;

import definition.simulation.SimulationDefinition;
import definition.simulation.manager.api.SimulationDefinitionNode;

import java.util.HashMap;
import java.util.Map;

public class SimulationDefinitionManager {
    private Map<Integer, SimulationDefinitionNode> requestIdToSimulationDefinitionCount;

    public SimulationDefinitionManager() {
        requestIdToSimulationDefinitionCount = new HashMap<>();
    }

    public synchronized void add(int serialNumber, SimulationDefinition simulation, int numOfInstances) {
        requestIdToSimulationDefinitionCount.put(serialNumber, new SimulationDefinitionNode(simulation, numOfInstances));
    }

    public synchronized void decreaseSimulationNumOfInstances(int serialNumber) {
        requestIdToSimulationDefinitionCount
                .get(serialNumber)
                .decreaseNumOfInstances();
    }
}
