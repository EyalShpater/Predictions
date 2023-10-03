package definition.simulation.manager.api;

import definition.simulation.SimulationDefinition;

public class SimulationDefinitionNode {
    private final SimulationDefinition simulation;
    private int numOfSimulations;

    public SimulationDefinitionNode(SimulationDefinition simulation, int numOfSimulations) {
        this.simulation = simulation;
        this.numOfSimulations = numOfSimulations;
    }

    public SimulationDefinition getSimulation() {
        return simulation;
    }

    public int getNumOfInstances() {
        return numOfSimulations;
    }

    public void decreaseNumOfInstances() {
        if (numOfSimulations != 0) {
            numOfSimulations--;
        }
    }
}
