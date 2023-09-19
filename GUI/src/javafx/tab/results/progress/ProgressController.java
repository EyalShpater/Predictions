package javafx.tab.results.progress;

import execution.simulation.api.PredictionsLogic;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.mainScene.main.PredictionsMainAppController;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.tab.results.ResultsController;
import javafx.tab.results.helper.Category;
import task.helper.EntityPopulationData;
import task.UpdateEntitiesAmountTask;
import task.UpdateSimulationDetailsTask;

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

    private UpdateSimulationDetailsTask currentDetailsTask;
    private UpdateEntitiesAmountTask currentEntitiesAmountData;


    private TabPane tabPane;
    private TableView<EntityPopulationData> entityPopulationDataTableView;


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
        secondsLabel.textProperty().bind(Bindings.format("%d", seconds));
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
            goToResultsTabByName("New Execution");
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
            //todo
        } else if (isPause.get()) {
            pauseButtonIcon.setImage(new Image("javafx/tab/results/resources/play-button.png"));
        } else {
            pauseButtonIcon.setImage(new Image("javafx/tab/results/resources/pause.png"));
        }
    }

    private void goToResultsTabByName(String tabName) {
        Tab resultsTab = null;

        for (Tab tab : tabPane.getTabs()) {
            if (tab.getText().equals(tabName)) {
                resultsTab = tab;
            }
        }
        if (resultsTab != null) {
            tabPane.getSelectionModel().select(resultsTab);
        }
    }


    public void onSelectedSimulationChange(Category newValue) {
        if (newValue == null) {
            return;
        }
        selectedSimulation = newValue;
        isStop.set(engine.isStop(newValue.getId()) || engine.isEnded(newValue.getId()));
        isPause.set(engine.isPaused(newValue.getId()));

        if (currentEntitiesAmountData != null && currentDetailsTask != null) {
            currentDetailsTask.cancel();
            currentEntitiesAmountData.cancel();
            progress.unbind();
        }

        currentDetailsTask = new UpdateSimulationDetailsTask(engine, selectedSimulation.getId(), ticks::set, seconds::set, isStop::set);
        progress.bind(currentDetailsTask.progressProperty());

        currentEntitiesAmountData = new UpdateEntitiesAmountTask(engine, selectedSimulation.getId(), entityPopulationDataTableView);


        new Thread(currentDetailsTask).start();
        new Thread(currentEntitiesAmountData).start();
    }

    public void setPredictionsMainAppController(PredictionsMainAppController predictionsMainAppController) {
        this.predictionsMainAppController = predictionsMainAppController;
    }


    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    public void setTableView(TableView<EntityPopulationData> entitesPopulationTableView) {
        this.entityPopulationDataTableView = entitesPopulationTableView;
    }
}
