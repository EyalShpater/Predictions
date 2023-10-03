package user.impl;

import definition.simulation.manager.SimulationDefinitionManager;

import java.util.ArrayList;
import java.util.List;

public class User {
    private boolean isConnected;
    private final String name;
    private List<Integer> simulationsID;
    private List<Integer> requestsID;
    private SimulationDefinitionManager simulationDefinitionManager;

    public User(String name) {
        this.name = name;
        this.isConnected = false;
        this.simulationsID = new ArrayList<>();
        this.requestsID = new ArrayList<>();
        this.simulationDefinitionManager = new SimulationDefinitionManager();
    }


}
