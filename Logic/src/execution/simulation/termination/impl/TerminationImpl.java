package execution.simulation.termination.impl;

import api.DTO;
import execution.simulation.termination.api.TerminateCondition;
import execution.simulation.termination.api.Termination;
import impl.TerminationDTO;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class TerminationImpl implements Termination , Serializable {
    private final int ticksToTerminate;
    private final int secondsToTerminate;
    private boolean isTerminateByTicks = false;
    private boolean isTerminateBySeconds = false;
    private boolean isTerminateByUser = false;

    public TerminationImpl(int ticksToTerminate, int secondsToTerminate) {
        this.ticksToTerminate = ticksToTerminate;
        this.secondsToTerminate = secondsToTerminate;
        isTerminateBySeconds = true;
        isTerminateByTicks = true;
    }

    public TerminationImpl(int terminate, TerminateCondition condition) {
        this.isTerminateByTicks = condition.equals(TerminateCondition.BY_TICKS);
        this.isTerminateBySeconds = condition.equals(TerminateCondition.BY_SECONDS);
        this.ticksToTerminate = isTerminateByTicks ?
                terminate :
                Integer.MAX_VALUE;
        this.secondsToTerminate = isTerminateBySeconds ?
                terminate :
                Integer.MAX_VALUE;
    }

    public TerminationImpl() {
        ticksToTerminate = Integer.MAX_VALUE;
        secondsToTerminate = Integer.MAX_VALUE;
        isTerminateByUser = true;
    }

    public TerminationImpl(TerminationDTO dto) {
        this(dto.getTicksToTerminate(), dto.getSecondsToTerminate());
        this.isTerminateByTicks = dto.isTerminateByTicks();
        this.isTerminateBySeconds = dto.isTerminateBySeconds();
        this.isTerminateByUser = !isTerminateBySeconds() && !isTerminateByTicks();
    }

    @Override
    public TerminateCondition isTerminate(int currentTick, long secondsDuration, boolean userRequestedStop) {
        TerminateCondition terminateReason;

        if (isTerminateByTicks && currentTick >= ticksToTerminate) {
            terminateReason = TerminateCondition.BY_TICKS;
        } else if (isTerminateBySeconds && secondsDuration >= secondsToTerminate) {
            terminateReason = TerminateCondition.BY_SECONDS;
        } else if (userRequestedStop) {
            terminateReason = TerminateCondition.BY_USER;
        } else {
            terminateReason = null;
        }

        return terminateReason;
    }

    @Override
    public boolean isTerminateBySeconds() {
        return isTerminateBySeconds;
    }

    @Override
    public boolean isTerminateByTicks() {
        return isTerminateByTicks;
    }

    @Override
    public int getTicksToTerminate() {
        return ticksToTerminate;
    }

    @Override
    public int getSecondsToTerminate() {
        return secondsToTerminate;
    }

    @Override
    public TerminationDTO convertToDTO() {
        return new TerminationDTO(
                ticksToTerminate,
                secondsToTerminate,
                isTerminateBySeconds,
                isTerminateByTicks
        );
    }
}