package execution.simulation.api;

public interface PredictionsLogic {
    void loadXML(String path);

    /* need to think about return vale */ void getSimulationDetails();

    Data run();

    /* need to think about return vale */ Data getAllPreviousSimulationData();
}
