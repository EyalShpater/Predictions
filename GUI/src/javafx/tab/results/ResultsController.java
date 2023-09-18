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
import javafx.tab.results.helper.Category;
import javafx.tab.results.progress.ProgressController;
import task.helper.EntityPopulationData;

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
    private Pagination analyzePaging;

    @FXML
    private ProgressController progressController;

    private TabPane tabPane; //todo: why do we need it?

    private PredictionsLogic engine;

    private ObjectProperty<Category> selectedSimulation = new SimpleObjectProperty<>();
    private PredictionsMainAppController predictionsMainAppController;

    private StringProperty propertyToView;
    private StringProperty entityToView;

    private ObservableList<EntityPopulationData> entityPopulationList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        propertyToView = new SimpleStringProperty();
        entityToView = new SimpleStringProperty();

        propertyToView.bind(propertyChoiceBox.valueProperty());
        entityToView.bind(entityChoiceBox.valueProperty());
        selectedSimulation.bind(simulationsListView.getSelectionModel().selectedItemProperty());
        selectedSimulation.addListener((observable, oldValue, newValue) -> progressController.onSelectedSimulationChange(newValue));
        progress.disableProperty().bind(Bindings.isNull(selectedSimulation));

        selectedSimulation.addListener((observable, oldValue, newValue) -> onSelectedSimulationChange(newValue));
        entityChoiceBox.setOnAction(this::onSelectedEntity);
//        propertyChoiceBox.setOnAction(this::onSelectedProperty);

//
//        simulationChoiceBox.setOnAction(this::onSelectSimulation);
//        propertyChoiceBox.setOnAction(this::onSelectProperty);
//        showByAmountRadioButton.setOnAction(this::onToggleRadioButton);


        entitiesCol.setCellValueFactory(new PropertyValueFactory<>("entityName"));
        populationCol.setCellValueFactory(new PropertyValueFactory<>("population"));
    }

    private void onSelectedEntity(ActionEvent actionEvent) {
        setPropertyChoiceBox();
    }

    private void onSelectedSimulationChange(Category newSimulation) {
        if (newSimulation != null && engine.isStop(newSimulation.getId())) {
            setEntitiesChoiceBox();
            setPropertyChoiceBox();
        }
    }

    public void setEngine(PredictionsLogic engine) {
        this.engine = engine;
        progressController.setEngine(engine);
    }

    public void setPredictionsMainAppController(PredictionsMainAppController predictionsMainAppController) {
        this.predictionsMainAppController = predictionsMainAppController;
        progressController.setPredictionsMainAppController(predictionsMainAppController);
    }

    public void onStartButtonClicked(int newSimulationSerialNumber) {
        progressController.setTableView(this.entitiesPopulationTableView);
        System.out.println(progressController);
        SimulationDTO lastSimulation = engine.getSimulationDTOBySerialNumber(newSimulationSerialNumber);
        simulationsListView.getItems().add(new Category(lastSimulation.getStartDate(), lastSimulation.getSerialNumber()));
    }

//    private void onSelectSimulation(ActionEvent event) {
//        if (isFirstStart) {
//            setPropertyChoiceBox();
//            isFirstStart = false;
//        } else {
//            onSelectProperty(event);
//        }
//    }
//

    private void onSelectProperty(ActionEvent event) { //todo: fix exception here
        SimulationDataDTO data = engine.getSimulationData(
                selectedSimulation.getValue().getId(),
                entityToView.get(),
                propertyToView.get()
        );

        //setChart(data);
    }

//    private void setSimulationChoiceBox() {
//        simulationChoiceBox.getItems().clear(); // todo: inefficient
//
//        engine.getPreviousSimulationsAsDTO()
//                .stream()
//                .map(simulation -> new Category(simulation.getStartDate(), simulation.getSerialNumber()))
//                .forEach(simulationChoiceBox.getItems()::add);
//
//    }

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

    public void onNewFileLoaded() {
//        histogramBarChart.getData().clear();
//        simulationChoiceBox.getItems().clear();
        simulationsListView.getItems().clear();
        setEntitiesChoiceBox();
        setPropertyChoiceBox();

        //propertyChoiceBox.getItems().clear();
    }

    private void setEntitiesChoiceBox() {
        entityChoiceBox.getItems().clear();

        WorldDTO simulationDTO = engine.getLoadedSimulationDetails();
        simulationDTO
                .getEntities()
                .forEach(entity -> entityChoiceBox.getItems().add(entity.getName()));
    }

    public Category getSelectedSimulation() {
        return selectedSimulation.get();
    }

    public ObjectProperty<Category> getSelectedSimulationProperty() {
        return selectedSimulation;
    }

    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
        progressController.setTabPane(tabPane);
    }
}
