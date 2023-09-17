package javafx.tab.results.progress;

import execution.simulation.api.PredictionsLogic;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.mainScene.main.PredictionsMainAppController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.tab.results.ResultsController;
import javafx.tab.results.helper.Category;

public class ProgressController {

    @FXML
    private Button pauseButton;

    @FXML
    private ImageView pauseButtonIcon;

    @FXML
    private Button stopButton;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label secondsLabel;

    @FXML
    private Label ticksLabel;


    private Category selectedSimulation;
    private BooleanProperty isPause = new SimpleBooleanProperty();
    private BooleanProperty isStop = new SimpleBooleanProperty();

    PredictionsMainAppController predictionsMainAppController;
    private PredictionsLogic engine;
    private ResultsController resultsController;

    @FXML
    void initialize() {
        isPause.set(false);
        isStop.set(false);
        isPause.addListener((observable, oldValue, newValue) -> changePauseButtonIcon());
        isStop.addListener((observable, oldValue, newValue) -> toggleButtons());
    }

    private void toggleButtons() {
        if (isStop.get()) {
            engine.stopSimulationBySerialNumber(selectedSimulation.getId());
            pauseButtonIcon.setImage(new Image("javafx/tab/results/resources/rerun.png"));
            stopButton.setDisable(true);
        } else {
            if (isPause.get()) {
                pauseButtonIcon.setImage(new Image("javafx/tab/results/resources/pause.png"));
            } else {
                pauseButtonIcon.setImage(new Image("javafx/tab/results/resources/play-button.png"));
                stopButton.setDisable(true);
            }
            stopButton.setDisable(false);
        }
    }

    @FXML
    void pauseOnClick(ActionEvent event) {
        if (isStop.get()) {
            predictionsMainAppController.restoreDataValuesToTiles(engine.getUserInputOfSimulationBySerialNumber(selectedSimulation.getId()));
        } else if (isPause.get()) {
            engine.resumeSimulationBySerialNumber((selectedSimulation.getId()));
        } else {
            engine.pauseSimulationBySerialNumber(selectedSimulation.getId());
        }

        isPause.set(!isPause.get());
    }

    @FXML
    void stopOnClick(ActionEvent event) {
        isStop.set(true);
    }

    public void setEngine(PredictionsLogic engine) {
        this.engine = engine;
    }

    private void changePauseButtonIcon() {
        if (isStop.get()) {
            //
        } else if (isPause.get()) {
            pauseButtonIcon.setImage(new Image("javafx/tab/results/resources/play-button.png"));
        } else {
            pauseButtonIcon.setImage(new Image("javafx/tab/results/resources/pause.png"));
        }
    }

    public void onSelectedPropertyChange(Category newValue) {
        selectedSimulation = newValue;
        isPause.set(engine.isPaused(newValue.getId()));
        isStop.set(engine.isStop(newValue.getId()));
    }

    public void setPredictionsMainAppController(PredictionsMainAppController predictionsMainAppController) {
        this.predictionsMainAppController = predictionsMainAppController;
    }
}
