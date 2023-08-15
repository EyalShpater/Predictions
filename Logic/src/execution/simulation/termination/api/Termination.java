package execution.simulation.termination.api;

public interface Termination {
    boolean isTerminate(int currentTick, long startTimeInMillis);
}
