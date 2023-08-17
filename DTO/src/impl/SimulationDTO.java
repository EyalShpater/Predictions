package impl;

import api.DTO;

public class SimulationDTO implements DTO {
    long startDate;
    int serialNumber;

    public SimulationDTO(long startDate, int serialNumber) {
        this.startDate = startDate;
        this.serialNumber = serialNumber;
    }

    public long getStartDate() {
        return startDate;
    }

    public int getSerialNumber() {
        return serialNumber;
    }
}
