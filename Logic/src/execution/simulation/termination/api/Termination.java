package execution.simulation.termination.api;

import api.DTOConvertible;
import impl.TerminationDTO;

public interface Termination extends DTOConvertible<TerminationDTO> {
    TerminateCondition isTerminate(int currentTick, long startTimeInMillis);
    boolean isTerminateBySeconds();
    boolean isTerminateByTicks();
}
