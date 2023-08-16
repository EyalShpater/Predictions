package execution.simulation.termination.api;

import api.DTOConvertible;

public interface Termination extends DTOConvertible {
    boolean isTerminate(int currentTick, long startTimeInMillis);
}
