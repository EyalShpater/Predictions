package impl;

import api.DTO;

public class SimulationDTO implements DTO {
    long startDate;
    int serialNumber;
    WorldDTO world;

    public SimulationDTO(long startDate, int serialNumber, WorldDTO world) {
        this.startDate = startDate;
        this.serialNumber = serialNumber;
        this.world = world;
    }

    public long getStartDate() {
        return startDate;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public WorldDTO getWorld() {
        return world;
    }
}
