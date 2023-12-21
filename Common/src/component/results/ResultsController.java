package component.results;

import component.results.analyze.AnalyzePaginationController;
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
    private TableView<EntityPopulationData> entitiesPopulationTableView;

    @FXML
    private TableColumn<EntityPopulationData, String> entitiesCol;

    @FXML
    private TableColumn<EntityPopulationData, Integer> populationCol;

    @FXML
    private ChoiceBox<String> entityChoiceBox;

    @FXML
    private ChoiceBox<String> propertyChoiceBox;

    @FXML
    private TitledPane entityAmountTitlePane;

    @FXML
    private TitledPane dataAnalyzeTitlePane;

    @FXML
    private Pagination analyzePagination;

    @FXML
    private AnalyzePaginationController analyzePaginationController;

    @FXML
    private ProgressController progressController;

    @FXML
    private SimulationsListController simulationsListViewController;

    private String userName;
    private TabPane tabPane;

    private ObjectProperty<Category> selectedSimulation;
    private IntegerProperty selectedSimulationSerialNumber;
    private StringProperty propertyToView;
    private StringProperty entityToView;
    private BooleanProperty isSelectedSimulationEnded;
    private BooleanProperty isNewFileLoaded;
    private Queue<Category> waitingQueue = new LinkedList<>();

    public ResultsController() {
        selectedSimulation = new SimpleObjectProperty<>();
        propertyToView = new SimpleStringProperty();
        entityToView = new SimpleStringProperty();
        isSelectedSimulationEnded = new SimpleBooleanProperty();
        isNewFileLoaded = new SimpleBooleanProperty();
        selectedSimulationSerialNumber = new SimpleIntegerProperty();
    }

    @FXML
    private void initialize() {
        propertyToView.bind(propertyChoiceBox.valueProperty());
        entityToView.bind(entityChoiceBox.valueProperty());
        simulationsListViewController.setOnSelectionChange(this::onSelectedSimulationChange);
        isSelectedSimulationEnded.bind(progressController.isStopProperty());
        progress.disableProperty().bind(Bindings.isNull(selectedSimulation));
        dataAnalyzeTitlePane.disableProperty().bind(isSelectedSimulationEnded.not());

        isSelectedSimulationEnded.addListener((observable, oldValue, newValue) -> onSelectedSimulationStop());
        entityToView.addListener((observable, oldValue, newValue) -> onSelectedEntity(newValue));
        isNewFileLoaded.addListener((observable, oldValue, newValue) -> clear());

        entitiesCol.setCellValueFactory(new PropertyValueFactory<>("entityName"));
        populationCol.setCellValueFactory(new PropertyValueFactory<>("population"));

        progressController.setTableView(this.entitiesPopulationTableView);
        analyzePaginationController.setResultsController(this);
    }

    private void clear() {
        progressController.isStopProperty().set(false);
    }

    private void onSelectedSimulationChange(Category newValue) {
        if (newValue != null && (selectedSimulation.get() == null || newValue.getId() != selectedSimulation.get().getId())) {
            selectedSimulation.set(newValue);
            progressController.onSelectedSimulationChange(newValue);
            selectedSimulationSerialNumber.set(newValue.getId());
            analyzePaginationController.onSelectedSimulationChange(selectedSimulationSerialNumber.get());
        }
    }

    public SimulationDataDTO getSimulationData() throws IOException {
        return selectedSimulation.isNotNull().get() ?
                RequestHandler.getSimulationData(
                        selectedSimulation.get().getId(),
                        entityToView.get(),
                        propertyChoiceBox.getValue()
                ) :
                null;
    }

    private void onSelectedEntity(String newValue) {
        try {
            setPropertyChoiceBox();
            analyzePaginationController.setConsistency(RequestHandler.getConsistencyByEntityName(selectedSimulationSerialNumber.get(), newValue));
        } catch (Exception ignored) {
        }
    }

//    public void onStartButtonClicked(int newSimulationSerialNumber) {
//        try {
//            progressController.setTableView(this.entitiesPopulationTableView);
//            SimulationDTO lastSimulation = RequestHandler.getSimulationDTOBySerialNumber(newSimulationSerialNumber);
//
//            Category simulationInfo = new Category(lastSimulation.getWorld().getName(), lastSimulation.getStartDate(), lastSimulation.getSerialNumber());
//            waitingQueue.add(simulationInfo);
//        } catch (Exception ignored) {
//        }
//    }

    private void onSelectedSimulationStop() {
//        analyzePaginationController.setPopulationData(engine.getPopulationCountSortedByName(selectedSimulation.get().getId()));
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

    public String getPropertyToView() {
        return propertyToView.get();
    }

    public StringProperty propertyToViewProperty() {
        return propertyToView;
    }

    public String getEntityToView() {
        return entityToView.get();
    }

    public StringProperty entityToViewProperty() {
        return entityToView;
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

    private void setPropertyChoiceBox() {
        propertyChoiceBox.getItems().clear();

        try {
            if (entityToView.isNotNull().get()) {
                WorldDTO simulation = RequestHandler.getWorld("");
                simulation
                        .getEntities()
                        .stream()
                        .filter(entity -> entity.getName().equals(entityToView.get()))
                        .findAny()
                        .get()
                        .getProperties()
                        .forEach(property -> propertyChoiceBox.getItems().add(property.getName()));
            }
        } catch (Exception ignored) {
        }
    }

    private void setEntitiesChoiceBox() {
        entityChoiceBox.getItems().clear();

        try {
            WorldDTO simulationDTO = RequestHandler.getWorld("");
            simulationDTO
                    .getEntities()
                    .forEach(entity -> entityChoiceBox.getItems().add(entity.getName()));
        } catch (Exception ignored) {
        }
    }

    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
        progressController.setTabPane(tabPane);
    }

    public void setDisableEntityChoiceBoxValue(boolean disable) {
        String value = entityChoiceBox.getValue();

        entityChoiceBox.setDisable(disable);
        entityChoiceBox.valueProperty().set(disable ? null : value);

    }

    public void setDisablePropertyChoiceBoxValue(boolean disable) {
        String value = propertyChoiceBox.getValue();

        propertyChoiceBox.setDisable(disable);
        propertyChoiceBox.valueProperty().set(disable ? null : value);
    }

    public Map<String, Double> getConsistency() {
        try {
            return selectedSimulation.isNotNull().get() ?
                    RequestHandler.getConsistencyByEntityName(selectedSimulationSerialNumber.get(), entityToView.get()) :
                    null;
        } catch (Exception e) {
            return null;
        }
    }

    public Map<String, Map<Integer, Long>> getPopulationData() {
        try {
            return RequestHandler.getPopulationCountSortedByName(selectedSimulationSerialNumber.get());
        } catch (Exception e) {
            return null;
        }
    }

    public Double getAverageProperty() {
        try {
            return RequestHandler.getFinalNumericPropertyAvg(selectedSimulationSerialNumber.get(), entityToView.get(), propertyToView.get());
        } catch (Exception e) {
            return null;
        }
    }

    public void setUserName(String userName) {
        this.userName = userName;
        simulationsListViewController.setUserName(userName);
    }
}