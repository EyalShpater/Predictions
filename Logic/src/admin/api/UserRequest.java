package admin.api;

import execution.simulation.termination.api.Termination;

public class UserRequest {
    private final String worldName;
    private final String userName;
    private final int serialNumber;
    private final int numOfRequestedRuns;
    private final Termination termination;
    private RequestStatus status;

    public UserRequest(int serialNumber, String worldName, String userName, int numOfRequests, Termination termination) {
        this.serialNumber = serialNumber;
        this.worldName = worldName;
        this.userName = userName;
        this.numOfRequestedRuns = numOfRequests;
        this.termination = termination;
        this.status = RequestStatus.WAITING;
    }

    public void changeStatus(RequestStatus newStatus) {
        if (status.equals(RequestStatus.WAITING)) {
            status = newStatus;
        }
    }

    public String getWorldName() {
        return worldName;
    }

    public String getUserName() {
        return userName;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public int getNumOfRequestedRuns() {
        return numOfRequestedRuns;
    }

    public Termination getTermination() {
        return termination;
    }

    public RequestStatus getStatus() {
        return status;
    }
}
