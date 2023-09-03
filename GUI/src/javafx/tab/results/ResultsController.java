package javafx.tab.results;

import execution.simulation.api.PredictionsLogic;
import impl.SimulationDTO;
import impl.SimulationDataDTO;
import impl.SimulationRunDetailsDTO;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.tab.results.helper.Category;

import java.util.HashMap;
import java.util.Map;

public class ResultsController {
    @FXML
    private ChoiceBox<Category> simulationChoiceBox;

    @FXML
    private ChoiceBox<String> propertyChoiceBox;

    @FXML
    private RadioButton showByAmountRadioButton;

    @FXML
    private BarChart<String, Integer> histogramBarChart;

    @FXML
    private CategoryAxis categoryAxis;

    @FXML
    private NumberAxis NumberAxis;

    private ObjectProperty<Category> selectedSimulation = new SimpleObjectProperty<>();
    private BooleanProperty isViewingByAmount = new SimpleBooleanProperty();
    private PredictionsLogic engine;
    private StringProperty propertyToView = new SimpleStringProperty();
    private StringProperty entityToView = new SimpleStringProperty();

    @FXML
    void initialize() {
        selectedSimulation.bind(simulationChoiceBox.valueProperty());
        propertyToView.bind(propertyChoiceBox.valueProperty());
//
//        simulationChoiceBox.getItems().add("Eyal");
//        simulationChoiceBox.getItems().add("Shpater");
//
        simulationChoiceBox.setOnAction(this::onSelectSimulation);
        propertyChoiceBox.setOnAction(this::onSelectProperty);
        showByAmountRadioButton.setOnAction(this::onToggleRadioButton);

//        XYChart.Series series1 = new XYChart.Series<>();
//        series1.setName("Food");
//
//        series1.getData().add(new XYChart.Data("Hamburger", 2000));
//        series1.getData().add(new XYChart.Data("Pizza", 2300));
//        series1.getData().add(new XYChart.Data("Mushed Potato", 1000));
//
//        XYChart.Series series2 = new XYChart.Series<>();
//        series2.setName("Drinks");
//
//        series2.getData().add(new XYChart.Data("Hamburger", 300));
//        series2.getData().add(new XYChart.Data("Kola", 190));
//        series2.getData().add(new XYChart.Data("FuzeTea", 500));
//
//        histogramBarChart.getData().addAll(series1, series2);
    }

    private void onSelectProperty(ActionEvent event) {
        SimulationDataDTO data = engine.getSimulationData(simulationChoiceBox.getValue().getId(), entityToView.get(), propertyToView.get());

        histogramBarChart.getData().clear();

        XYChart.Series series1 = new XYChart.Series<>();
        series1.setName(propertyToView.get());

        Map<Object, Integer> valueCountMap = new HashMap<>();

        for (Object obj : data.getPropertyOfEntitySortedByValues()) {
            valueCountMap.put(obj, valueCountMap.getOrDefault(obj, 0) + 1);
        }

        valueCountMap.forEach((key, value) -> series1.getData().add(new XYChart.Data(key.toString(), value)));

        histogramBarChart.getData().add(series1);
    }

    private void setSimulationChoiceBox() {
        simulationChoiceBox.getItems().clear(); // todo: inefficient

        engine.getPreviousSimulationsAsDTO()
                .stream()
                .map(simulation -> new Category(simulation.getStartDate(), simulation.getSerialNumber()))
                .forEach(simulationChoiceBox.getItems()::add);

    }

    private void setPropertyChoiceBox() {
        propertyChoiceBox.getItems().clear();

        SimulationDTO simulation = engine.getSimulationDTOBySerialNumber(selectedSimulation.get().getId());
        simulation
                .getWorld()
                .getEntities()
                .get(0)
                .getProperties()
                .forEach(property -> propertyChoiceBox.getItems().add(property.getName()));

        entityToView.set(simulation.getWorld().getEntities().get(0).getName());
    }

    private void onSelectSimulation(ActionEvent event) {
        setPropertyChoiceBox();
    }

    private void onToggleRadioButton(ActionEvent event) {
        if (showByAmountRadioButton.isSelected()) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }

    public void setEngine(PredictionsLogic engine) {
        this.engine = engine;
    }

    public void onStartButtonClicked() {
        setSimulationChoiceBox();
    }
}
