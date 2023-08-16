package impl;

import api.DTO;

public class TerminationDTO implements DTO {
    private final int ticksToTerminate;
    private final long secondsToTerminate;

    public TerminationDTO(int ticksToTerminate, long secondsToTerminate) {
        this.ticksToTerminate = ticksToTerminate;
        this.secondsToTerminate = secondsToTerminate;
    }

    public int getTicksToTerminate() {
        return ticksToTerminate;
    }

    public long getSecondsToTerminate() {
        return secondsToTerminate;
    }
}
