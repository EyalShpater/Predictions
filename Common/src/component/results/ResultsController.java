package component.results;

import component.results.analyze.AnalyzePaginationController;
import component.results.details.DetailsController;
import component.results.helper.Category;
import component.results.list.SimulationsListController;
import component.results.progress.ProgressController;
import impl.SimulationDTO;
import impl.SimulationDataDTO;
import impl.WorldDTO;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import servlet.request.RequestHandler;
import component.results.task.helper.EntityPopulationData;


import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class ResultsController {

    @FXML
    private StackPane progress;

    @FXML
    private ProgressController progressController;

    @FXML
    private TableView<Category> simulationsListView;

    @FXML
    private SimulationsListController simulationsListViewController;

    @FXML
    private Accordion details;

    @FXML
    private DetailsController detailsController;

    private String userName;
    private TabPane tabPane;

    private ObjectProperty<Category> selectedSimulation;
    private IntegerProperty selectedSimulationSerialNumber;
    private BooleanProperty isSelectedSimulationEnded;
    private BooleanProperty isNewFileLoaded;
    private Queue<Category> waitingQueue = new LinkedList<>();

    public ResultsController() {
        selectedSimulation = new SimpleObjectProperty<>();
        isSelectedSimulationEnded = new SimpleBooleanProperty();
        isNewFileLoaded = new SimpleBooleanProperty();
        selectedSimulationSerialNumber = new SimpleIntegerProperty();
    }

    @FXML
    private void initialize() {
        simulationsListViewController.setOnSelectionChange(this::onSelectedSimulationChange);
        isSelectedSimulationEnded.bind(progressController.isStopProperty());
        progress.disableProperty().bind(Bindings.isNull(selectedSimulation));

        isSelectedSimulationEnded.addListener((observable, oldValue, newValue) -> onSelectedSimulationStop());
        isNewFileLoaded.addListener((observable, oldValue, newValue) -> clear());

        detailsController.setProgressController(progressController);
        progressController.setTableView(detailsController.getEntitiesPopulationTableView());
    }

    private void clear() {
        progressController.isStopProperty().set(false);
    }

    private void onSelectedSimulationChange(Category newValue) {
        if (newValue != null && (selectedSimulation.get() == null || newValue.getId() != selectedSimulation.get().getId())) {
            selectedSimulation.set(newValue);
            progressController.onSelectedSimulationChange(newValue);
            selectedSimulationSerialNumber.set(newValue.getId());
            detailsController.onSelectedSimulationChange(newValue);
        }
    }

    private void onSelectedSimulationStop() {
//        analyzePaginationController.setPopulationData(engine.getPopulationCountSortedByName(selectedSimulation.get().getId())); todo: delete
        detailsController.isSelectedSimulationEndedProperty().set(true);
    }

    public Category getSelectedSimulation() {
        return selectedSimulation.get();
    }

    public ObjectProperty<Category> getSelectedSimulationProperty() {
        return selectedSimulation;
    }

    public Queue<Category> getWaitingQueue() {
        return waitingQueue;
    }

    public boolean isNewFileLoaded() {
        return isNewFileLoaded.get();
    }

    public BooleanProperty isNewFileLoadedProperty() {
        return isNewFileLoaded;
    }

    public boolean isIsSelectedSimulationEnded() {
        return isSelectedSimulationEnded.get();
    }

    public BooleanProperty isSelectedSimulationEndedProperty() {
        return isSelectedSimulationEnded;
    }

    public ObjectProperty<Category> selectedSimulationProperty() {
        return selectedSimulation;
    }

    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
        progressController.setTabPane(tabPane);
    }

    public void setUserName(String userName) {
        this.userName = userName;
        simulationsListViewController.setUserName(userName);
    }
}