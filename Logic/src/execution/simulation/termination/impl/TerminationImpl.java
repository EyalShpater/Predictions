package execution.simulation.termination.impl;

import execution.simulation.termination.api.Termination;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class TerminationImpl implements Termination {
    private final int ticksToTerminate;
    private final long secondsToTerminate;

    public TerminationImpl(int ticksToTerminate, int secondsToTerminate) {
        this.ticksToTerminate = ticksToTerminate;
        this.secondsToTerminate = secondsToTerminate;
    }

    @Override
    public boolean isTerminate(int currentTick, long startTimeInMillis) {
        long secondsSinceStart = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - startTimeInMillis);

        return ticksToTerminate >= currentTick || secondsSinceStart >= secondsToTerminate;
    }
}