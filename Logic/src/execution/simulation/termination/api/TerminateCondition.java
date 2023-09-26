package execution.simulation.termination.api;

import java.io.Serializable;

public enum TerminateCondition implements Serializable {
    BY_TICKS,
    BY_SECONDS,
    BY_USER,
    BY_ERROR;
}
