package admin.api;

import api.DTOConvertible;
import execution.simulation.termination.api.Termination;
import impl.RequestedSimulationDataDTO;

public class UserRequest implements DTOConvertible<RequestedSimulationDataDTO> {
    private final String worldName;
    private final String userName;
    private final int serialNumber;
    private final int numOfRequestedRuns;
    private final Termination termination;
    private RequestStatus status;
    private int numOfRunningSimulations;
    private int numOfEndedSimulations;

    public UserRequest(int serialNumber, String worldName, String userName, int numOfRequests, Termination termination) {
        this.serialNumber = serialNumber;
        this.worldName = worldName;
        this.userName = userName;
        this.numOfRequestedRuns = numOfRequests;
        this.termination = termination;
        this.status = RequestStatus.WAITING;
        this.numOfRunningSimulations = 0;
        this.numOfEndedSimulations = 0;
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

    public void setNumOfRunningSimulations(int numOfRunningSimulations) {
        this.numOfRunningSimulations = numOfRunningSimulations;
    }

    public void setNumOfEndedSimulations(int numOfEndedSimulations) {
        this.numOfEndedSimulations = numOfEndedSimulations;
    }

    public void increaseRunningCounter() {
        numOfRunningSimulations++;
    }

    public void decreaseNumOfRunningCounter() {
        numOfRunningSimulations--;
        numOfEndedSimulations++;
    }

    @Override
    public RequestedSimulationDataDTO convertToDTO() {
        return new RequestedSimulationDataDTO(
                serialNumber,
                numOfRequestedRuns,
                numOfRunningSimulations,
                numOfEndedSimulations,
                worldName,
                status.toString(),
                userName,
                termination.convertToDTO());
    }
}
