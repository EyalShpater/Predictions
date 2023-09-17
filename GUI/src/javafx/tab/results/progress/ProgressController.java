package javafx.tab.results.progress;

import execution.simulation.api.PredictionsLogic;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.concurrent.Task;
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
import task.UpdateSimulationDetailsTask;

import java.util.concurrent.TimeUnit;

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

    private IntegerProperty ticks;
    private LongProperty seconds;
    private DoubleProperty progress;

    private PredictionsMainAppController predictionsMainAppController;
    private PredictionsLogic engine;
    private ResultsController resultsController;
    private UpdateSimulationDetailsTask detailsTask;

    @FXML
    void initialize() {
        isPause.set(false);
        isStop.set(false);
        isPause.addListener((observable, oldValue, newValue) -> changePauseButtonIcon());
        isStop.addListener((observable, oldValue, newValue) -> toggleButtons());

        ticks = new SimpleIntegerProperty(0);
        seconds = new SimpleLongProperty(0);
        progress = new SimpleDoubleProperty(0);

        ticksLabel.textProperty().bind(Bindings.format("%d", ticks));
        secondsLabel.textProperty().bind(Bindings.format("%d", TimeUnit.MILLISECONDS.toSeconds(seconds.get())));
        progressBar.progressProperty().bind(progress);
    }

    private void toggleButtons() {
        if (isStop.get()) {
            pauseButtonIcon.setImage(new Image("javafx/tab/results/resources/rerun.png"));
        } else if (isPause.get()) {
            pauseButtonIcon.setImage(new Image("javafx/tab/results/resources/play-button.png"));
        } else {
            pauseButtonIcon.setImage(new Image("javafx/tab/results/resources/pause.png"));
        }

        stopButton.setDisable(isStop.get());
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
        engine.stopSimulationBySerialNumber(selectedSimulation.getId());
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
        isStop.set(engine.isStop(newValue.getId()));
        isPause.set(engine.isPaused(newValue.getId()));

        UpdateSimulationDetailsTask task = new UpdateSimulationDetailsTask(engine, selectedSimulation.getId());

        new Thread(task).start();

    }

    public void setPredictionsMainAppController(PredictionsMainAppController predictionsMainAppController) {
        this.predictionsMainAppController = predictionsMainAppController;
    }

    public void bindTaskProperties(UpdateSimulationDetailsTask task) {
        ticks.unbind();
        seconds.unbind();
        progress.unbind();

        ticks.bind(task.ticksProperty());
        seconds.bind(task.secondsProperty());
        progress.bind(task.progressProperty());
    }
}
