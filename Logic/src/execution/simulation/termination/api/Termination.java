package execution.simulation.termination.api;

import api.DTOConvertible;
import impl.TerminationDTO;

public interface Termination extends DTOConvertible<TerminationDTO> {
    TerminateCondition isTerminate(int currentTick, long startTimeInMillis, long pauseDuration, boolean userRequestedStop);

    boolean isTerminateBySeconds();

    boolean isTerminateByTicks();

    int getTicksToTerminate();
}
