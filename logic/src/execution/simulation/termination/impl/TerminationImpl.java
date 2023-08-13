package execution.simulation.termination.impl;

import execution.simulation.termination.api.Termination;

import java.util.*;

public class TerminationImpl implements Termination {
    private int ticksToTerminate;
    private int secondsToTerminate;
    private Date secondsSinceStart;

    public TerminationImpl(int ticksToTerminate, int secondsToTerminate) {
        this.ticksToTerminate = ticksToTerminate;
        this.secondsToTerminate = secondsToTerminate;
        secondsSinceStart = new Date(); // not real use
    }

    @Override
    public boolean isTerminate(int currentTick) {
        return ticksToTerminate == currentTick /* || secondsToTerminate == secondsSinceStart.getSeconds() */;
    }
}
