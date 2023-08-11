package execution.simulation.api;

import temporary.SomeObject;

public interface Simulation {
    int getSerialNumber();
    Data getData();

    SomeObject run();
}
