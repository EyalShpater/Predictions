package impl;

public class SimulationRunDetailsDTO {
    private final boolean isTerminateBySeconds;
    private final boolean isTerminateByTicks;
    private final int serialNumber;
    private final int tickNumber;
    private final long runningTime;
    private final double startProgress;
    private final double endProgress;

    public SimulationRunDetailsDTO(boolean isTerminateBySeconds, boolean isTerminateByTicks, int serialNumber, int tickNumber, long runningTime, double startProgress, double endProgress) {
        this.isTerminateBySeconds = isTerminateBySeconds;
        this.isTerminateByTicks = isTerminateByTicks;
        this.serialNumber = serialNumber;
        this.tickNumber = tickNumber;
        this.runningTime = runningTime;
        this.startProgress = startProgress;
        this.endProgress = endProgress;
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

    public double getStartProgress() {
        return startProgress;
    }

    public double getEndProgress() {
        return endProgress;
    }
}
