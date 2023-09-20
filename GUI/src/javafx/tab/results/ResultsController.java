package javafx.tab.results;

import execution.simulation.api.PredictionsLogic;
import impl.SimulationDTO;
import impl.SimulationDataDTO;
import impl.WorldDTO;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.mainScene.main.PredictionsMainAppController;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.tab.results.analyze.AnalyzePaginationController;
import javafx.tab.results.helper.Category;
import javafx.tab.results.progress.ProgressController;
import task.helper.EntityPopulationData;

import java.util.LinkedList;
import java.util.Queue;

public class ResultsController {

    @FXML
    private StackPane progress;

    @FXML
    private ListView<Category> simulationsListView;

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

    private TabPane tabPane; //todo: why do we need it?

    private PredictionsLogic engine;
    private PredictionsMainAppController predictionsMainAppController;

    private ObjectProperty<Category> selectedSimulation;
    private StringProperty propertyToView;
    private StringProperty entityToView;
    private BooleanProperty isSelectedSimulationEnded;
    private BooleanProperty isNewFileLoaded;

    private ObservableList<EntityPopulationData> entityPopulationList = FXCollections.observableArrayList();

    private Queue<Category> waitingQueue = new LinkedList<>();

    public ResultsController() {
        selectedSimulation = new SimpleObjectProperty<>();
        propertyToView = new SimpleStringProperty();
        entityToView = new SimpleStringProperty();
        isSelectedSimulationEnded = new SimpleBooleanProperty();
        isNewFileLoaded = new SimpleBooleanProperty();
    }

    @FXML
    private void initialize() {
        propertyToView.bind(propertyChoiceBox.valueProperty());
        entityToView.bind(entityChoiceBox.valueProperty());
        selectedSimulation.bind(simulationsListView.getSelectionModel().selectedItemProperty());
        isSelectedSimulationEnded.bind(progressController.isStopProperty());
        progress.disableProperty().bind(Bindings.isNull(selectedSimulation));
        dataAnalyzeTitlePane.disableProperty().bind(isSelectedSimulationEnded.not());

        selectedSimulation.addListener((observable, oldValue, newValue) -> progressController.onSelectedSimulationChange(newValue));
        selectedSimulation.addListener((observable, oldValue, newValue) -> onSelectedSimulationChange(newValue));

        entityChoiceBox.setOnAction(this::onSelectedEntity);
        propertyChoiceBox.setOnAction(this::onSelectedProperty);

        entitiesCol.setCellValueFactory(new PropertyValueFactory<>("entityName"));
        populationCol.setCellValueFactory(new PropertyValueFactory<>("population"));

        analyzePaginationController.setResultsController(this);
    }

    private void onSelectedProperty(ActionEvent actionEvent) {
        if (selectedSimulation.isNotNull().get()) {
            SimulationDataDTO data = engine.getSimulationData(
                    selectedSimulation.get().getId(),
                    entityToView.get(),
                    propertyChoiceBox.getValue()
            );

            setPopulationData();

            if (!propertyChoiceBox.getItems().isEmpty()) {
                analyzePaginationController.setPropertiesChart(propertyToView.get(), data);
            }
        }
    }

    private void onSelectedEntity(ActionEvent actionEvent) {
        setPropertyChoiceBox();
        analyzePaginationController.setConsistencyChart(engine.getConsistencyByEntityName(selectedSimulation.get().getId(), entityToView.get()));
    }

    private void onSelectedSimulationChange(Category newSimulation) {
        if (newSimulation != null && engine.isStop(newSimulation.getId())) {
            setEntitiesChoiceBox();
            setPropertyChoiceBox();
        }
    }

    public void onStartButtonClicked(int newSimulationSerialNumber) {
        progressController.setTableView(this.entitiesPopulationTableView);
        System.out.println(progressController);
        SimulationDTO lastSimulation = engine.getSimulationDTOBySerialNumber(newSimulationSerialNumber);

        Category simulationInfo = new Category(lastSimulation.getStartDate(), lastSimulation.getSerialNumber());
        simulationsListView.getItems().add(simulationInfo);
        waitingQueue.add(simulationInfo);
    }

    public void onNewFileLoaded() {
//        histogramBarChart.getData().clear();
//        simulationChoiceBox.getItems().clear();
        simulationsListView.getItems().clear();
        setEntitiesChoiceBox();
        setPropertyChoiceBox();

        //propertyChoiceBox.getItems().clear();
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

    public void setEngine(PredictionsLogic engine) {
        this.engine = engine;
        progressController.setEngine(engine);
    }

    public void setPredictionsMainAppController(PredictionsMainAppController predictionsMainAppController) {
        this.predictionsMainAppController = predictionsMainAppController;
        progressController.setPredictionsMainAppController(predictionsMainAppController);
        isNewFileLoaded.bind(predictionsMainAppController.isFileSelectedProperty());
    }

    private void setPropertyChoiceBox() {
        propertyChoiceBox.getItems().clear();

        if (entityToView.isNotNull().get()) {
            WorldDTO simulation = engine.getLoadedSimulationDetails();
            simulation
                    .getEntities()
                    .stream()
                    .filter(entity -> entity.getName().equals(entityToView.get()))
                    .findAny()
                    .get()
                    .getProperties()
                    .forEach(property -> propertyChoiceBox.getItems().add(property.getName()));
        }
    }

    private void setEntitiesChoiceBox() {
        entityChoiceBox.getItems().clear();

        WorldDTO simulationDTO = engine.getLoadedSimulationDetails();
        simulationDTO
                .getEntities()
                .forEach(entity -> entityChoiceBox.getItems().add(entity.getName()));
    }

    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
        progressController.setTabPane(tabPane);
    }

    public void setPopulationData() {
        analyzePaginationController.setPopulationData(engine.getPopulationCountSortedByName(selectedSimulation.get().getId()));
    }
}