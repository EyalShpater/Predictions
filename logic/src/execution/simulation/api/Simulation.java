package execution.simulation.api;

public interface Simulation {
    int getSerialNumber();

    Data getData();

    void run();
}
