package impl;

public class SimulationRunDetailsDTO {
    private final boolean isTerminateBySeconds;
    private final boolean isTerminateByTicks;
    private final int serialNumber;

    public SimulationRunDetailsDTO(boolean isTerminateBySeconds, boolean isTerminateByTicks, int serialNumber) {
        this.isTerminateBySeconds = isTerminateBySeconds;
        this.isTerminateByTicks = isTerminateByTicks;
        this.serialNumber = serialNumber;
    }

    public boolean isTerminateBySeconds() {
        return isTerminateBySeconds;
    }

    public boolean isTerminateByTicks() {
        return isTerminateByTicks;
    }

    public int getSerialNumber() {
        return serialNumber;
    }
}
