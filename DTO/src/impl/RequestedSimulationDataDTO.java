package impl;

import api.DTO;

public class RequestedSimulationDataDTO implements DTO {
    private final int requestSerialNumber;
    private final int numOfSimulationInstances;
    private final int numOfRunningSimulations;
    private final int numOfEndedSimulations;
    private final String worldName;
    private final String status;

    public RequestedSimulationDataDTO(int requestSerialNumber, int numOfSimulationInstances, int numOfRunningSimulations, int numOfEndedSimulations, String worldName, String status) {
        this.requestSerialNumber = requestSerialNumber;
        this.numOfSimulationInstances = numOfSimulationInstances;
        this.numOfRunningSimulations = numOfRunningSimulations;
        this.numOfEndedSimulations = numOfEndedSimulations;
        this.worldName = worldName;
        this.status = status;
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
}
