package impl;

import api.DTO;

public class TerminationDTO implements DTO {
    private final int ticksToTerminate;
    private final int secondsToTerminate;
    private final boolean isTerminateBySeconds;
    private final boolean isTerminateByTicks;

    public TerminationDTO(int ticksToTerminate, int secondsToTerminate, boolean isTerminateBySeconds, boolean isTerminateByTicks) {
        this.ticksToTerminate = ticksToTerminate;
        this.secondsToTerminate = secondsToTerminate;
        this.isTerminateBySeconds = isTerminateBySeconds;
        this.isTerminateByTicks = isTerminateByTicks;
    }

    public int getTicksToTerminate() {
        return ticksToTerminate;
    }

    public int getSecondsToTerminate() {
        return secondsToTerminate;
    }

    public boolean isTerminateBySeconds() {
        return isTerminateBySeconds;
    }

    public boolean isTerminateByTicks() {
        return isTerminateByTicks;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (!isTerminateByTicks && !isTerminateBySeconds) {
            sb.append("User");
        } else {
            if (isTerminateBySeconds) {
                sb.append(String.format("Seconds: %d", secondsToTerminate));
            }

            if (isTerminateByTicks) {
                if (isTerminateBySeconds) {
                    sb.append(System.lineSeparator());
                }

                sb.append(String.format("Ticks: %d", ticksToTerminate));
            }
        }

        return sb.toString();
    }
}
