package impl;

import api.DTO;

import java.util.Objects;

public class RequestedSimulationDataDTO implements DTO {
    private final int requestSerialNumber;
    private final int numOfSimulationInstances;
    private int numOfRunningSimulations;
    private int numOfEndedSimulations;
    private final String worldName;
    private String status;
    private final String userName;
    private final TerminationDTO termination;

    public RequestedSimulationDataDTO(int requestSerialNumber, int numOfSimulationInstances, int numOfRunningSimulations, int numOfEndedSimulations, String worldName, String status, String userName, TerminationDTO termination) {
        this.requestSerialNumber = requestSerialNumber;
        this.numOfSimulationInstances = numOfSimulationInstances;
        this.numOfRunningSimulations = numOfRunningSimulations;
        this.numOfEndedSimulations = numOfEndedSimulations;
        this.worldName = worldName;
        this.status = status;
        this.userName = userName;
        this.termination = termination;
    }

    public int getRequestSerialNumber() {
        return requestSerialNumber;
    }

    public int getNumOfSimulationInstances() {
        return numOfSimulationInstances;
    }

    public int getNumOfRunningSimulations() {
        return numOfRunningSimulations;
    }

    public int getNumOfEndedSimulations() {
        return numOfEndedSimulations;
    }

    public String getWorldName() {
        return worldName;
    }

    public String getStatus() {
        return status;
    }

    public String getUserName() {
        return userName;
    }

    public TerminationDTO getTermination() {
        return termination;
    }

    public void setRunningAndEndedAmount(int numOfRunningSimulations, int numOfEndedSimulations) {
        this.numOfRunningSimulations = numOfRunningSimulations;
        this.numOfEndedSimulations = numOfEndedSimulations;
    }

    public void setStatus(String Status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestedSimulationDataDTO that = (RequestedSimulationDataDTO) o;
        return requestSerialNumber == that.requestSerialNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestSerialNumber);
    }
}
