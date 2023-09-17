package javafx.tab.results.progress;

import execution.simulation.api.PredictionsLogic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.mainScene.main.PredictionsMainAppController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;

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

    @FXML
    void initialize() {
    }

    private PredictionsLogic engine;
    private PredictionsMainAppController mainController;

    @FXML
    void pauseOnClick(ActionEvent event) {
        //mainController.restoreTiles(engine.getSimulation)
        //goto newExeScreen
    }

    public void setEngine(PredictionsLogic engine) {
        this.engine = engine;
    }

    public void setMainController(PredictionsMainAppController mainController) {
        this.mainController = mainController;
    }
}
