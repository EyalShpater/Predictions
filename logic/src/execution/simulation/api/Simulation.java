package execution.simulation.api;

import api.DTO;

public interface Simulation {
    int getSerialNumber();
    Data getData();

    DTO run(DTO environmentVariables);
}
