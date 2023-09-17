package javafx.tab.results.progress;

import execution.simulation.api.PredictionsLogic;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.tab.results.ResultsController;
import javafx.tab.results.helper.Category;

import java.net.URL;

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

    private PredictionsLogic engine;
    private ResultsController resultsController;

    @FXML
    void initialize() {
        isPause.set(false);
        isStop.set(false);
        isPause.addListener((observable, oldValue, newValue) -> chanePauseButtonIcon());
    }

    @FXML
    void pauseOnClick(ActionEvent event) {
        //mainController.restoreTiles(engine.getSimulation)
        //goto newExeScreen
        if (isStop.get()) {
            //shavit code
        } else if (isPause.get()) {
            engine.resumeSimulationBySerialNumber((selectedSimulation.getId()));
        } else {
            engine.pauseSimulationBySerialNumber(selectedSimulation.getId());
        }

        isPause.set(!isPause.get());
    }

    @FXML
    void stopOnClick(ActionEvent event) {
        engine.stopSimulationBySerialNumber(selectedSimulation.getId());
        pauseButtonIcon.setImage(new Image("javafx/tab/results/resources/rerun.png"));
        stopButton.setDisable(true);
        isStop.set(true);
    }

    public void setEngine(PredictionsLogic engine) {
        this.engine = engine;
    }

    private void chanePauseButtonIcon() {
        if (isStop.get()) {
            //
        } else if (isPause.get()) {
            pauseButtonIcon.setImage(new Image("javafx/tab/results/resources/play-button.png"));
        } else {
            pauseButtonIcon.setImage(new Image("javafx/tab/results/resources/pause.png"));
        }
    }

//    public void bindSelectedSimulationProperty(ReadOnlyObjectProperty<Category> selectedSimulation) {
//        this.selectedSimulation.bind(selectedSimulation);
//    }

    public void onSelectedPropertyChange(Category newValue) {
        selectedSimulation = newValue;
        System.out.println("new value: " + newValue);
    }
}
