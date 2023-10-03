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

    public void add(int serialNumber, SimulationDefinition simulation, int numOfInstances) {
        requestIdToSimulationDefinitionCount.put(serialNumber, new SimulationDefinitionNode(simulation, numOfInstances));
    }

    public synchronized SimulationDefinition getSimulationAndDecreaseInstances(int serialNumber) {
        SimulationDefinitionNode simulationNode = requestIdToSimulationDefinitionCount.get(serialNumber);

        simulationNode.decreaseNumOfInstances();

        return simulationNode.getNumOfInstances() != 0 ?
                simulationNode.getSimulation() :
                null;
    }
}
