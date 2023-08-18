package execution.simulation.termination.impl;

import api.DTO;
import execution.simulation.termination.api.Termination;
import impl.TerminationDTO;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class TerminationImpl implements Termination {
    private final int ticksToTerminate;
    private final long secondsToTerminate;

    public TerminationImpl(int ticksToTerminate, long secondsToTerminate) {
        this.ticksToTerminate = ticksToTerminate;
        this.secondsToTerminate = secondsToTerminate;
    }

    public TerminationImpl(TerminationDTO dto) {
        this(dto.getTicksToTerminate(), dto.getSecondsToTerminate());
    }

    @Override
    public boolean isTerminate(int currentTick, long startTimeInMillis) {
        long secondsSinceStart = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - startTimeInMillis);
        if (ticksToTerminate == Integer.MAX_VALUE){
            return secondsSinceStart >= secondsToTerminate;
        } else if (secondsToTerminate == Long.MAX_VALUE) {
            return currentTick >= ticksToTerminate;
        }
        return currentTick >= ticksToTerminate || secondsSinceStart >= secondsToTerminate;
    }

    @Override
    public TerminationDTO convertToDTO() {
        return new TerminationDTO(ticksToTerminate, secondsToTerminate);
    }
}