package admin.impl;

import admin.api.RequestManager;
import admin.api.RequestStatus;
import admin.api.UserRequest;
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

    public void addNewUserRequest(RunRequestDTO newRequest) {
        requestManager.addNewRequest(newRequest);
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
        UserRequest request = requestManager.getRequest(serialNumber);

        //add requests to user

        request.changeStatus(RequestStatus.APPROVED);
    }

    public void declineRequest(int serialNumber) {
        setRequestStatus(serialNumber, RequestStatus.DECLINE);
    }

    private void setRequestStatus(int serialNumber, RequestStatus status) {
        requestManager
                .getRequest(serialNumber)
                .changeStatus(status);
    }
}
