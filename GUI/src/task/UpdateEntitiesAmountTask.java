package task;

import execution.simulation.api.PredictionsLogic;
import impl.EntitiesAmountDTO;
import impl.SimulationRunDetailsDTO;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.concurrent.Task;
import javafx.tab.results.ResultsController;
import javafx.tab.results.progress.ProgressController;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class UpdateEntitiesAmountTask extends Task<Boolean> {

    private PredictionsLogic engine;
    private int serialNumber;
    private ResultsController resultsController;

    private IntegerProperty entityAmount;
    private StringProperty entityName;

    public UpdateEntitiesAmountTask(PredictionsLogic engine, int serialNumber) {
        this.engine = engine;
        this.serialNumber = serialNumber;
        this.entityAmount = new SimpleIntegerProperty();
        this.entityName = new SimpleStringProperty();
    }

    @Override
    protected Boolean call() throws Exception {
        while (!engine.isEnded(serialNumber)) {
            EntitiesAmountDTO entitiesAmountDTO = engine.getSimulationEntitiesAmountMap(serialNumber);

            Map<String, Integer> entityNameToAmount = entitiesAmountDTO.getEntityToPopulationMap();

            /*Platform.runLater(() -> ticks.set(runDetails.getTickNumber()));
            Platform.runLater(() -> seconds.set(runDetails.getRunningTime()));

            updateProgress(runDetails.getStartProgress(), runDetails.getEndProgress());

            System.out.println("ticks: " + ticks.get());
            System.out.println("seconds: " + TimeUnit.MILLISECONDS.toSeconds(seconds.get()));*/

            Thread.sleep(200);
        }

        return true;
    }

    public void setController(ResultsController controller) {
        this.resultsController = controller;
        //controller.bindTaskProperties(this);
    }
}
