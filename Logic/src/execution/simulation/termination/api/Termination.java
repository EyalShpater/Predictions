package execution.simulation.termination.api;

import api.DTOConvertible;
import impl.TerminationDTO;

public interface Termination extends DTOConvertible<TerminationDTO> {
    boolean isTerminate(int currentTick, long startTimeInMillis);
}
