package user.impl;

import definition.simulation.SimulationDefinition;
import definition.simulation.manager.SimulationDefinitionManager;
import impl.RequestedSimulationDataDTO;

import java.util.ArrayList;
import java.util.List;

public class User {
    private boolean isConnected;
    private final String name;
    private List<Integer> simulationsSerialNumber;
    private List<Integer> requestsID;
    private SimulationDefinitionManager simulationDefinitionManager;

    public User(String name) {
        this.name = name;
        this.isConnected = true;
        this.simulationsSerialNumber = new ArrayList<>();
        this.requestsID = new ArrayList<>();
        this.simulationDefinitionManager = new SimulationDefinitionManager();
    }

    public void connect() {
        isConnected = true;
    }

    public void disconnect() {
        isConnected = false;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public String getName() {
        return name;
    }

    public void addSimulationDefinition(int requestSerialNumber, SimulationDefinition simulation, int numOfInstances) {
        simulationDefinitionManager.add(requestSerialNumber, simulation, numOfInstances);
    }

    public void addActivatedSimulationSerialNumber(int SimulationSerialNumber, int requestSerialNumber) {
        simulationsSerialNumber.add(SimulationSerialNumber);
        simulationDefinitionManager.decreaseSimulationNumOfInstances(requestSerialNumber);
    }

    public void addRequestSerialNumber(int serialNumber) {
        requestsID.add(serialNumber);
    }

    public List<Integer> getRequestsSerialNumbers() {
        return new ArrayList<>(requestsID);
    }

    public List<Integer> getSimulationsSerialNumber() {
        return new ArrayList<>(simulationsSerialNumber);
    }
}
