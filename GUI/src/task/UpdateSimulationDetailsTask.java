package task;

import execution.simulation.api.PredictionsLogic;
import impl.SimulationRunDetailsDTO;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.concurrent.Task;
import javafx.tab.results.progress.ProgressController;

import java.util.concurrent.TimeUnit;

public class UpdateSimulationDetailsTask extends Task<Boolean> {

    private PredictionsLogic engine;
    private int serialNumber;

    private IntegerProperty ticks;
    private LongProperty seconds;

    private ProgressController controller;


    public UpdateSimulationDetailsTask(PredictionsLogic engine, int serialNumber) {
        this.engine = engine;
        this.serialNumber = serialNumber;
        this.ticks = new SimpleIntegerProperty();
        this.seconds = new SimpleLongProperty();
    }

    @Override
    protected Boolean call() throws Exception {
        while (!engine.isEnded(serialNumber)) {
            SimulationRunDetailsDTO runDetails = engine.getSimulationRunDetail(serialNumber);

            System.out.println("runDetails");

            Platform.runLater(() -> ticks.set(runDetails.getTickNumber()));
            Platform.runLater(() -> seconds.set(runDetails.getRunningTime()));

            updateProgress(runDetails.getStartProgress(), runDetails.getEndProgress());

            System.out.println("ticks: " + ticks.get());
            System.out.println("seconds: " + TimeUnit.MILLISECONDS.toSeconds(seconds.get()));

            Thread.sleep(200);
        }

        return true;
    }

    public int getTicks() {
        return ticks.get();
    }

    public IntegerProperty ticksProperty() {
        return ticks;
    }

    public long getSeconds() {
        return seconds.get();
    }

    public LongProperty secondsProperty() {
        return seconds;
    }

    public void setController(ProgressController controller) {
        this.controller = controller;
        controller.bindTaskProperties(this);
    }
}
