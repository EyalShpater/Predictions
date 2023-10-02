//package task;
//
//import execution.simulation.api.PredictionsLogic;
//import impl.SimulationRunDetailsDTO;
//import javafx.application.Platform;
//import javafx.beans.binding.Bindings;
//import javafx.concurrent.Task;
//import javafx.scene.control.ProgressIndicator;
//import javafx.tab.results.progress.ProgressController;
//
//import java.util.function.Consumer;
//
//public class UpdateSimulationDetailsTask extends Task<Boolean> {
//    private static final int TIME_TO_SLEEP = 100;
//
//    private PredictionsLogic engine;
//    private int serialNumber;
//
//    private Consumer<Integer> setTicks;
//    private Consumer<Long> setSeconds;
//    private Consumer<Boolean> atEnd;
//
//    public UpdateSimulationDetailsTask(PredictionsLogic engine, int serialNumber, Consumer<Integer> setTicks, Consumer<Long> setSeconds, Consumer<Boolean> atEnd) {
//        this.engine = engine;
//        this.serialNumber = serialNumber;
//        this.setTicks = setTicks;
//        this.setSeconds = setSeconds;
//        this.atEnd = atEnd;
//    }
//
//    @Override
//    protected Boolean call() throws Exception {
//        do {
//            SimulationRunDetailsDTO runDetails = engine.getSimulationRunDetail(serialNumber);
//            updateProgress(runDetails.getStartProgress(), runDetails.getEndProgress());
//
//            Platform.runLater(() -> setTicks.accept(runDetails.getTickNumber()));
//            Platform.runLater(() -> setSeconds.accept(runDetails.getRunningTime()));
//
//            updateProgress(runDetails.getStartProgress(), runDetails.getEndProgress());
//
//            Thread.sleep(TIME_TO_SLEEP);
//        } while (!engine.isEnded(serialNumber));
//
//        updateProgress(1, 1);
//        atEnd.accept(true);
//
//        return true;
//    }
//}
