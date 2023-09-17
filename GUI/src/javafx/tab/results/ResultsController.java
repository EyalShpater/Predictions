package javafx.tab.results;

import execution.simulation.api.PredictionsLogic;
import impl.SimulationDTO;
import impl.SimulationDataDTO;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.tab.results.helper.Category;
import javafx.tab.results.progress.ProgressController;

import java.util.*;

public class ResultsController {

    @FXML
    private ListView<Category> simulationsListView;

    @FXML
    private TableView<String> entitiesPopulationTableView;

    @FXML
    private TableColumn<String, String> entitiesCol;

    @FXML
    private TableColumn<String, Integer> populationCol;

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
    private StringProperty propertyToView = new SimpleStringProperty();
    private StringProperty entityToView = new SimpleStringProperty();
    private boolean isFirstStart = true;

    public void setEngine(PredictionsLogic engine) {
        this.engine = engine;
    }

    public void onStartButtonClicked(int newSimulationSerialNumber) {
        //setSimulationChoiceBox();
        //example
        SimulationDTO lastSimulation = engine.getSimulationDTOBySerialNumber(newSimulationSerialNumber);
        simulationsListView.getItems().add(new Category(lastSimulation.getStartDate(), lastSimulation.getSerialNumber()));
    }

    @FXML
    private void initialize() {
//        selectedSimulation.bind(simulationChoiceBox.valueProperty());
//        propertyToView.bind(propertyChoiceBox.valueProperty());
//
//        simulationChoiceBox.setOnAction(this::onSelectSimulation);
//        propertyChoiceBox.setOnAction(this::onSelectProperty);
//        showByAmountRadioButton.setOnAction(this::onToggleRadioButton);
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
//    private void setChart(SimulationDataDTO data) {
//        XYChart.Series series = new XYChart.Series<>();
//        List<Map.Entry<Object, Integer>> values;
//
//        histogramBarChart.getData().clear();
//
//        series.setName(propertyToView.get());
//        values = createSortedValuesToAmountMap(data.getPropertyOfEntitySortedByValues());
//
//        values.forEach(entry -> series.getData().add(new XYChart.Data(entry.getKey().toString(), entry.getValue())));
//        histogramBarChart.getData().add(series);
//    }

    //    private List<Map.Entry<Object, Integer>> createSortedValuesToAmountMap(List<Object> values) {
//        Map<Object, Integer> valueCountMap = new HashMap<>();
//        List<Map.Entry<Object, Integer>> dataList;
//
//        for (Object obj : values) {
//            valueCountMap.put(obj, valueCountMap.getOrDefault(obj, 0) + 1);
//        }
//
//        dataList = new ArrayList<>(valueCountMap.entrySet());
//        dataList.sort(Comparator.comparing(entry -> entry.getKey().hashCode()));
//
//        return dataList;
//    }
//
    public void onNewFileLoaded() {
//        histogramBarChart.getData().clear();
//        simulationChoiceBox.getItems().clear();
//        propertyChoiceBox.getItems().clear();
//        isFirstStart = true;
    }
}
