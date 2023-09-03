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

import java.util.*;

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
    private boolean isFirstStart = true;

    public void setEngine(PredictionsLogic engine) {
        this.engine = engine;
    }

    public void onStartButtonClicked() {
        setSimulationChoiceBox();
    }

    @FXML
    private void initialize() {
        selectedSimulation.bind(simulationChoiceBox.valueProperty());
        propertyToView.bind(propertyChoiceBox.valueProperty());

        simulationChoiceBox.setOnAction(this::onSelectSimulation);
        propertyChoiceBox.setOnAction(this::onSelectProperty);
        showByAmountRadioButton.setOnAction(this::onToggleRadioButton);
    }

    private void onSelectSimulation(ActionEvent event) {
        if (isFirstStart) {
            setPropertyChoiceBox();
            isFirstStart = false;
        } else {
            onSelectProperty(event);
        }
    }

    private void onToggleRadioButton(ActionEvent event) {
        if (showByAmountRadioButton.isSelected()) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }

    private void onSelectProperty(ActionEvent event) {
        SimulationDataDTO data = engine.getSimulationData(
                simulationChoiceBox.getValue().getId(),
                entityToView.get(),
                propertyToView.get()
        );

        setChart(data);
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

    private void setChart(SimulationDataDTO data) {
        XYChart.Series series = new XYChart.Series<>();
        List<Map.Entry<Object, Integer>> values;

        histogramBarChart.getData().clear();

        series.setName(propertyToView.get());
        values = createSortedValuesToAmountMap(data.getPropertyOfEntitySortedByValues());

        values.forEach(entry -> series.getData().add(new XYChart.Data(entry.getKey().toString(), entry.getValue())));
        histogramBarChart.getData().add(series);
    }

    private List<Map.Entry<Object, Integer>> createSortedValuesToAmountMap(List<Object> values) {
        Map<Object, Integer> valueCountMap = new HashMap<>();
        List<Map.Entry<Object, Integer>> dataList;

        for (Object obj : values) {
            valueCountMap.put(obj, valueCountMap.getOrDefault(obj, 0) + 1);
        }

        dataList = new ArrayList<>(valueCountMap.entrySet());
        dataList.sort(Comparator.comparing(entry -> entry.getKey().hashCode()));

        return dataList;
    }
}
