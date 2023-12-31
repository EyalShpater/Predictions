package admin.impl;

import admin.api.RequestManager;
import admin.api.RequestStatus;
import admin.api.UserRequest;
import definition.simulation.SimulationDefinition;
import impl.RequestedSimulationDataDTO;
import impl.RunRequestDTO;
import user.impl.User;

import java.util.ArrayList;
import java.util.List;

public class Admin {
    private RequestManager requestManager;
    private List<Integer> endedSimulationsID;
    private boolean isConnected;

    public Admin() {
        requestManager = new RequestManager();
        endedSimulationsID = new ArrayList<>();
        isConnected = false;
    }

    public void addNewUserRequest(RunRequestDTO newRequest, User user) {
        int requestSerialNumber = requestManager.addNewRequest(newRequest);

        user.addRequestSerialNumber(requestSerialNumber);
    }

    public void addEndedSimulation(int serialNumber) {
        endedSimulationsID.add(serialNumber);
    }

    public void connect() {
        isConnected = true;
    }

    public void disconnect() {
        isConnected = false;
    }

    public void acceptRequest(int serialNumber, User user) {
        SimulationDefinition simulationDefinition;
        UserRequest request = requestManager.getRequest(serialNumber);
        request.changeStatus(RequestStatus.APPROVED);

        simulationDefinition = new SimulationDefinition(request.getWorldName(), request.getTermination());
        user.addSimulationDefinition(serialNumber, simulationDefinition, request.getNumOfRequestedRuns());
    }

    public void declineRequest(int serialNumber) {
        setRequestStatus(serialNumber, RequestStatus.DECLINE);
    }

    private void setRequestStatus(int serialNumber, RequestStatus status) {
        requestManager
                .getRequest(serialNumber)
                .changeStatus(status);
    }

    public UserRequest getRequest(int serialNumber) {
        return requestManager.getRequest(serialNumber);
    }

    public void increaseNumOfRunningSimulation(int requestSerialNumber) {
        requestManager.getRequest(requestSerialNumber).increaseRunningCounter();
    }

    public void decreaseNumOfRunningSimulation(int requestSerialNumber) {
        requestManager.getRequest(requestSerialNumber).decreaseNumOfRunningCounter();
    }

    public List<UserRequest> getAllRequests() {
        return requestManager.getAllRequests();
    }


}
