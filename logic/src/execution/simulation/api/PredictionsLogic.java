package execution.simulation.api;

public interface PredictionsLogic {
    void loadXML(String path);

    /* need to think about return vale */ void getSimulationDetails();

    Data runNewSimulation();

    /* need to think about return vale */ Data getAllPreviousSimulationData();
}
