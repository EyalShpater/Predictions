package task;

import execution.simulation.api.PredictionsLogic;
import impl.SimulationRunDetailsDTO;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.tab.results.progress.ProgressController;

import java.util.function.Consumer;

public class UpdateSimulationDetailsTask extends Task<Boolean> {

    private static final int TIME_TO_SLEEP = 100;


    private PredictionsLogic engine;
    private int serialNumber;

    private int ticks;
    private long seconds;

    private Consumer<Integer> setTicks;
    private Consumer<Long> setSeconds;

    private ProgressController controller;

    public UpdateSimulationDetailsTask(PredictionsLogic engine, int serialNumber, Consumer<Integer> setTicks, Consumer<Long> setSeconds) {
        this.engine = engine;
        this.serialNumber = serialNumber;
        this.ticks = 0;
        this.seconds = 0;
        this.setTicks = setTicks;
        this.setSeconds = setSeconds;
    }

    @Override
    protected Boolean call() throws Exception {
        do {
            SimulationRunDetailsDTO runDetails = engine.getSimulationRunDetail(serialNumber);
            updateProgress(runDetails.getStartProgress(), runDetails.getEndProgress());

            Platform.runLater(() -> setTicks.accept(runDetails.getTickNumber()));
            Platform.runLater(() -> setSeconds.accept(runDetails.getRunningTime()));

            updateProgress(runDetails.getStartProgress(), runDetails.getEndProgress());

            Thread.sleep(TIME_TO_SLEEP);
        } while (!engine.isEnded(serialNumber));

        //updateProgress(1, 1);

        return true;
    }
}
