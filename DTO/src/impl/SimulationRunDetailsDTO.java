package impl;

public class SimulationRunDetailsDTO {
    private final boolean isTerminateBySeconds;
    private final boolean isTerminateByTicks;
    private final int serialNumber;
    private final int tickNumber;
    private final long runningTime;

    public SimulationRunDetailsDTO(boolean isTerminateBySeconds, boolean isTerminateByTicks, int serialNumber, int tickNumber, long runningTime) {
        this.isTerminateBySeconds = isTerminateBySeconds;
        this.isTerminateByTicks = isTerminateByTicks;
        this.serialNumber = serialNumber;
        this.tickNumber = tickNumber;
        this.runningTime = runningTime;
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

    public int getTickNumber() {
        return tickNumber;
    }

    public long getRunningTime() {
        return runningTime;
    }
}
