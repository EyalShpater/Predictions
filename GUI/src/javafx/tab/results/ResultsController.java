package javafx.tab.results;

import execution.simulation.api.PredictionsLogic;
import impl.SimulationDTO;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    private ObjectProperty<Category> selectedSimulation = new SimpleObjectProperty<>();
    private BooleanProperty isViewingByAmount = new SimpleBooleanProperty();
    private PredictionsLogic engine;
    private PredictionsMainAppController predictionsMainAppController;
    private StringProperty propertyToView = new SimpleStringProperty();
    private StringProperty entityToView = new SimpleStringProperty();
    private boolean isFirstStart = true;
    private TabPane tabPane;

    private ObservableList<EntityPopulationData> entityPopulationList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        selectedSimulation.bind(simulationsListView.getSelectionModel().selectedItemProperty());
        selectedSimulation.addListener((observable, oldValue, newValue) -> progressController.onSelectedSimulationChange(newValue));
        progress.disableProperty().bind(Bindings.isNull(selectedSimulation));
        /*entitiesCol.setCellValueFactory(new PropertyValueFactory<>("entityName"));
        populationCol.setCellValueFactory(new PropertyValueFactory<>("population"));
        entitiesPopulationTableView.setItems(entityPopulationList);*/
        //progressController.bindSelectedSimulationProperty();
        //System.out.println(progressController);
        // selectedSimulation.addListener((observable, oldValue, newValue) -> progressController.onChosenSimulationChange(newValue));

        //progressController.bindSelectedSimulationProperty();
//        propertyToView.bind(propertyChoiceBox.valueProperty());
//
//        simulationChoiceBox.setOnAction(this::onSelectSimulation);
//        propertyChoiceBox.setOnAction(this::onSelectProperty);
//        showByAmountRadioButton.setOnAction(this::onToggleRadioButton);


        entitiesCol.setCellValueFactory(new PropertyValueFactory<>("entityName"));
        populationCol.setCellValueFactory(new PropertyValueFactory<>("population"));
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
        //setSimulationChoiceBox();
        //example
        progressController.setTableView(this.entitiesPopulationTableView);
        System.out.println(progressController);
        SimulationDTO lastSimulation = engine.getSimulationDTOBySerialNumber(newSimulationSerialNumber);
        simulationsListView.getItems().add(new Category(lastSimulation.getStartDate(), lastSimulation.getSerialNumber()));
    }
/*
//    private void onSelectSimulation(ActionEvent event) {
//        if (isFirstStart) {
//            setPropertyChoiceBox();
//            isFirstStart = false;
//        } else {
//            onSelectProperty(event);
//        }
//    }
//

//    private void onSelectProperty(ActionEvent event) { //todo: fix exception here
//        SimulationDataDTO data = engine.getSimulationData(
//                simulationChoiceBox.getValue().getId(),
//                entityToView.get(),
//                propertyToView.get()
//        );
//
//        setChart(data);
//    }

//    private void setSimulationChoiceBox() {
//        simulationChoiceBox.getItems().clear(); // todo: inefficient
//
//        engine.getPreviousSimulationsAsDTO()
//                .stream()
//                .map(simulation -> new Category(simulation.getStartDate(), simulation.getSerialNumber()))
//                .forEach(simulationChoiceBox.getItems()::add);
//
//    }

//    private void setPropertyChoiceBox() {
//        propertyChoiceBox.getItems().clear();
//
//        SimulationDTO simulation = engine.getSimulationDTOBySerialNumber(selectedSimulation.get().getId());
//        simulation
//                .getWorld()
//                .getEntities()
//                .get(0)
//                .getProperties()
//                .forEach(property -> propertyChoiceBox.getItems().add(property.getName()));
//
//        entityToView.set(simulation.getWorld().getEntities().get(0).getName());
//    }
//
//
 */

    public void onNewFileLoaded() {
//        histogramBarChart.getData().clear();
//        simulationChoiceBox.getItems().clear();
//        propertyChoiceBox.getItems().clear();
//        isFirstStart = true;
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
