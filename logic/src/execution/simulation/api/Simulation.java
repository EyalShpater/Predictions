package execution.simulation.api;

import api.DTO;
import temporary.SomeObject;

public interface Simulation {
    int getSerialNumber();
    Data getData();

    DTO run();
}
