package javafx.tab.results.analyze;

import execution.simulation.api.PredictionsLogic;
import impl.SimulationDTO;
import impl.SimulationDataDTO;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Pagination;
import javafx.scene.layout.StackPane;
import javafx.tab.results.ResultsController;
import javafx.tab.results.analyze.histogram.consistency.ConsistencyBarChartController;
import javafx.tab.results.analyze.histogram.population.PopulationChartController;
import javafx.tab.results.analyze.histogram.property.PropertyChartController;

import java.net.URL;
import java.util.Map;
import java.util.function.Consumer;

public class AnalyzePaginationController {
    private static final int PROPERTIES_PAGE_INDEX = 0;
    private static final int CONSISTENCY_PAGE_INDEX = 1;
    private static final int POPULATION_PADE_INDEX = 2;

    @FXML
    private Pagination analyzePaging;

    private ResultsController resultsController;
    private PropertyChartController propertyChartController;
    private ConsistencyBarChartController consistencyBarChartController;
    private PopulationChartController populationChartController;

    private BooleanProperty isSelectedSimulationEnded = new SimpleBooleanProperty();
    private IntegerProperty currentPage = new SimpleIntegerProperty();
    private StringProperty selectedProperty = new SimpleStringProperty();
    private StringProperty selectedEntity = new SimpleStringProperty();

    private PredictionsLogic engine;
    private int currentSimulationSerialNumber;

    @FXML
    private void initialize() {
        setPagination();

        isSelectedSimulationEnded.addListener(((observable, oldValue, newValue) -> onSelectedSimulationEnd(newValue)));
        currentPage.bind(analyzePaging.currentPageIndexProperty());
        selectedProperty.addListener((observable, oldValue, newValue) -> setPropertiesChart(selectedProperty.get()));
        selectedEntity.addListener((observable, oldValue, newValue) -> setConsistencyChart(resultsController.getConsistency()));
    }

    private void setPagination() {
        analyzePaging.setPageFactory(pageIndex -> {
            switch (pageIndex) {
                case PROPERTIES_PAGE_INDEX:
                    return createPropertiesChart();
                case CONSISTENCY_PAGE_INDEX:
                    return createConsistencyChart();
                case POPULATION_PADE_INDEX:
                    return createPopulationChart();
                default:
                    return null;
            }
        });
    }


    private Node createPropertiesChart() {
        StackPane sp = new StackPane();
        if (resultsController != null) {
            setEntityAndPropertyChoiceBoxDisableProperty(false, false);
        }

        try {
            URL resource = getClass().getResource("/javafx/tab/results/analyze/histogram/property/PropertyChart.fxml");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(resource);
            Parent resultsContent = loader.load();
            sp.getChildren().add(resultsContent);

            System.out.println("page" + currentPage.get());
            propertyChartController = loader.getController();
        } catch (Exception ignored) {
        }

        return sp;
    }

    private Node createConsistencyChart() {
        StackPane sp = new StackPane();

        if (resultsController != null) {
            setEntityAndPropertyChoiceBoxDisableProperty(false, true);
        }

        try {
            URL resource = getClass().getResource("/javafx/tab/results/analyze/histogram/consistency/ConsistencyBarChart.fxml");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(resource);
            Parent resultsContent = loader.load();
            sp.getChildren().add(resultsContent);

            consistencyBarChartController = loader.getController();

            if (resultsController != null) {
                System.out.println("page" + currentPage.get());
                setConsistencyChart(resultsController.getConsistency());
            }
        } catch (Exception ignored) {

        }

        return sp;
    }

    private Node createPopulationChart() {
        StackPane sp = new StackPane();

        if (resultsController != null) {
            setEntityAndPropertyChoiceBoxDisableProperty(true, true);
        }

        try {
            URL resource = getClass().getResource("/javafx/tab/results/analyze/histogram/population/PopulationChart.fxml");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(resource);
            Parent resultsContent = loader.load();
            sp.getChildren().add(resultsContent);

            populationChartController = loader.getController();

            if (resultsController != null) {
                System.out.println("page" + currentPage.get());

                setPopulationChart(resultsController.getPopulationData());
            }
        } catch (Exception ignored) {
        }

        return sp;
    }

    private void onSelectedSimulationEnd(Boolean newValue) {

    }

    public void onSelectedSimulationChange(int newSerialNumber) {
        currentSimulationSerialNumber = newSerialNumber;

        if (engine.isEnded(newSerialNumber) && selectedEntity.isNotNull().get()) {
            switch (currentPage.get()) {
                case PROPERTIES_PAGE_INDEX:
//                propertyChartController.setChart(selectedProperty.get(), engine.getSimulationData(newSerialNumber, selectedEntity.get(), selectedProperty.get()));
                    if (selectedProperty.isNotNull().get()) {
                        setPropertiesChart(selectedProperty.get());
                    }
                    break;
                case CONSISTENCY_PAGE_INDEX:
                    setConsistencyChart(engine.getConsistencyByEntityName(newSerialNumber, selectedEntity.get()));
            }
        }

        if (currentPage.get() == POPULATION_PADE_INDEX) {
            setPopulationChart(engine.getPopulationCountSortedByName(newSerialNumber));
        }
    }

    public void setResultsController(ResultsController resultsController) {
        this.resultsController = resultsController;
        isSelectedSimulationEnded.bind(resultsController.isSelectedSimulationEndedProperty());
        selectedProperty.bind(resultsController.propertyToViewProperty());
        selectedEntity.bind(resultsController.entityToViewProperty());
    }

    private void setPopulationChart(Map<String, Map<Integer, Long>> data) {
        populationChartController.setChart(data);
    }


    public void setPropertiesChart(String property) {
        if (currentPage.get() == PROPERTIES_PAGE_INDEX) {
            SimulationDataDTO simulationData = resultsController.getSimulationData();
            propertyChartController.setChart(property, simulationData, resultsController.getAverageProperty());
        }
    }

    public void setConsistencyChart(Map<String, Double> consistency) {
        if (analyzePaging.currentPageIndexProperty().get() == CONSISTENCY_PAGE_INDEX) {
            consistencyBarChartController.setChart(consistency);
        }
    }

    public void setConsistency(Map<String, Double> consistency) {
        if (currentPage.get() == CONSISTENCY_PAGE_INDEX) {
            setConsistencyChart(consistency);
        }
    }

    private void setEntityAndPropertyChoiceBoxDisableProperty(boolean entity, boolean property) {
        resultsController.setDisableEntityChoiceBoxValue(entity);
        resultsController.setDisablePropertyChoiceBoxValue(property);
    }

    public void setEngine(PredictionsLogic engine) {
        this.engine = engine;
    }
}
