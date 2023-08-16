package execution.simulation.api;

import execution.simulation.data.api.SimulationData;

public interface Simulation {
    int getSerialNumber();
    void run();
}
