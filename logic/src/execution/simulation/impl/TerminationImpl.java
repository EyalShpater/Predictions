package execution.simulation.impl;

import execution.simulation.api.Termination;

import java.util.*;

public class TerminationImpl implements Termination {
    int ticksToTerminate;
    int secondsToTerminate;
    Date secondsSinceStart;

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
