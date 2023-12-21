package component.results.progress;

import component.results.helper.Category;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import servlet.request.RequestHandler;
import component.results.task.UpdateEntitiesAmountTask;
import component.results.task.UpdateSimulationDetailsTask;
import component.results.task.helper.EntityPopulationData;


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

    private UpdateSimulationDetailsTask currentDetailsTask;
    private UpdateEntitiesAmountTask currentEntitiesAmountDataTask;


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
        stopButton.disableProperty().bind(isStopProperty()); //todo
    }

    //todo
    private void toggleButtons() {
        if (isStop.get()) {
            pauseButtonIcon.setImage(new Image("/component/results/resources/rerun.png"));
        } else if (isPause.get()) {
            pauseButtonIcon.setImage(new Image("/component/results/resources/play-button.png"));
        } else {
            pauseButtonIcon.setImage(new Image("/component/results/resources/pause.png"));
        }
    }


    @FXML
    void pauseOnClick(ActionEvent event) {
        try {
            if (isStop.get()) {
                goToResultsTabByName("New Execution");
                //predictionsMainAppController.restoreDataValuesToTiles(engine.getUserInputOfSimulationBySerialNumber(selectedSimulation.getId()));
            } else if (isPause.get()) {
                RequestHandler.resumeSimulationBySerialNumber((selectedSimulation.getId()));
            } else {
                RequestHandler.pauseSimulationBySerialNumber(selectedSimulation.getId());
            }
        } catch (Exception ignored) {
        }

        isPause.set(!isPause.get());
    }

    @FXML
    void stopOnClick(ActionEvent event) {
        isStop.set(true);
        try {
            RequestHandler.stopSimulationBySerialNumber(selectedSimulation.getId());
        } catch (Exception ignored) {
        }
    }

    private void changePauseButtonIcon() {
        if (!isStop.get()) {
            pauseButtonIcon.setImage(isPause.get()
                    ? new Image("/component/results/resources/play-button.png")
                    : new Image("/component/results/resources/pause.png"));
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
            System.out.println("PC-> new value is null");
            return;
        }

        try {
            selectedSimulation = newValue;
            isStop.set(RequestHandler.isStop(newValue.getId()) || RequestHandler.isEnded(newValue.getId()));
            isPause.set(RequestHandler.isPause(newValue.getId()));
        } catch (Exception ignored) {
        }

        if (currentEntitiesAmountDataTask != null && currentDetailsTask != null) {
            currentDetailsTask.cancel();
            currentEntitiesAmountDataTask.cancel();
            progress.unbind();
        }

        currentDetailsTask = new UpdateSimulationDetailsTask(selectedSimulation.getId(), ticks::set, seconds::set, isStop::set);
        progress.bind(currentDetailsTask.progressProperty());

        currentEntitiesAmountDataTask = new UpdateEntitiesAmountTask(selectedSimulation.getId(), entityPopulationDataTableView);

        new Thread(currentDetailsTask).start();
        new Thread(currentEntitiesAmountDataTask).start();
    }

    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    public void setTableView(TableView<EntityPopulationData> entitesPopulationTableView) {
        this.entityPopulationDataTableView = entitesPopulationTableView;
    }

    public BooleanProperty isStopProperty() {
        return isStop;
    }
}
