package component.results.task;

import impl.SimulationRunDetailsDTO;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressIndicator;
import servlet.request.RequestHandler;

import java.util.function.Consumer;

public class UpdateSimulationDetailsTask extends Task<Boolean> {
    private static final int TIME_TO_SLEEP = 100;

    private int serialNumber;

    private Consumer<Integer> setTicks;
    private Consumer<Long> setSeconds;
    private Consumer<Boolean> atEnd;

    public UpdateSimulationDetailsTask(int serialNumber, Consumer<Integer> setTicks, Consumer<Long> setSeconds, Consumer<Boolean> atEnd) {
        this.serialNumber = serialNumber;
        this.setTicks = setTicks;
        this.setSeconds = setSeconds;
        this.atEnd = atEnd;
    }

    @Override
    protected Boolean call() throws Exception {
        int count = 0;
        do {
            SimulationRunDetailsDTO runDetails = RequestHandler.getSimulationRunDetail(serialNumber);
            updateProgress(runDetails.getStartProgress(), runDetails.getEndProgress());

            Platform.runLater(() -> setTicks.accept(runDetails.getTickNumber()));
            Platform.runLater(() -> setSeconds.accept(runDetails.getRunningTime()));

            updateProgress(runDetails.getStartProgress(), runDetails.getEndProgress());

            Thread.sleep(TIME_TO_SLEEP);
        } while (!RequestHandler.isEnded(serialNumber));

        updateProgress(1, 1);
        atEnd.accept(true);

        return true;
    }
}
