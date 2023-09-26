package javafx.tab.details.general;

import execution.simulation.api.PredictionsLogic;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GeneralController {

    @FXML
    private Label ticksNumberLabel;
    @FXML
    private Label secondsNumberLabel;
    @FXML
    private Label gridColumnNumberLabel;
    @FXML
    private Label gridRowsNumberLabel;
    @FXML
    private Label terminateByUserLabel;

    private StringProperty numOfTicks = new SimpleStringProperty();
    private StringProperty numOfSeconds = new SimpleStringProperty();
    private StringProperty numOfCols = new SimpleStringProperty();
    private StringProperty numOfRows = new SimpleStringProperty();

    private PredictionsLogic engine;

    @FXML
    private void initialize() {
        ticksNumberLabel.textProperty().bind(numOfTicks);
        secondsNumberLabel.textProperty().bind(numOfSeconds);
        gridColumnNumberLabel.textProperty().bind(numOfCols);
        gridRowsNumberLabel.textProperty().bind(numOfRows);
        terminateByUserLabel.visibleProperty().bind(numOfTicks.isEmpty().and(numOfSeconds.isEmpty()));
    }

    public void setPropertiesFromEngine() {
        boolean isTerminateByTicks = engine
                .getLoadedSimulationDetails()
                .getTermination()
                .isTerminateByTicks();
        boolean isTerminateBySeconds = engine
                .getLoadedSimulationDetails()
                .getTermination()
                .isTerminateBySeconds();

        numOfTicks.set(
                isTerminateByTicks ?
                        String.valueOf(engine
                                .getLoadedSimulationDetails()
                                .getTermination()
                                .getTicksToTerminate()) :
                        "");

        numOfSeconds.set(
                isTerminateBySeconds ?
                        String.valueOf(engine
                                .getLoadedSimulationDetails()
                                .getTermination()
                                .getSecondsToTerminate()) :
                        "");

        numOfCols.set(String.valueOf(engine.getLoadedSimulationDetails().getGridNumOfCols()));
        numOfRows.set(String.valueOf(engine.getLoadedSimulationDetails().getGridNumOfRows()));
    }

    public void setEngine(PredictionsLogic engine) {
        this.engine = engine;
    }
}
