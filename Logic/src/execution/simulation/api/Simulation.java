package execution.simulation.api;

import api.DTO;

public interface Simulation {
    int getSerialNumber();
    DTO run();
}
