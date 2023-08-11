package execution.simulation.api;

import temporary.SomeObject;

public interface PredictionsLogic {
    void loadXML(String path);

    SomeObject getEnvironmentVariablesToSet();

    void setEnvironmentVariablesValues(SomeObject variablesValues);

    SomeObject getSimulationDetails();

    SomeObject runNewSimulation();

    SomeObject getAllPreviousSimulationData();
}
