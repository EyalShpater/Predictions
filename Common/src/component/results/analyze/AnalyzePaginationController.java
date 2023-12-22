package component.results.analyze;

import component.results.analyze.histogram.consistency.ConsistencyBarChartController;
import component.results.analyze.histogram.population.PopulationChartController;
import component.results.analyze.histogram.property.PropertyChartController;
import component.results.details.DetailsController;
import impl.SimulationDataDTO;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Pagination;
import javafx.scene.layout.StackPane;
import servlet.request.RequestHandler;

import java.net.URL;
import java.util.Map;

public class AnalyzePaginationController {
    private static final int PROPERTIES_PAGE_INDEX = 0;
    private static final int CONSISTENCY_PAGE_INDEX = 1;
    private static final int POPULATION_PADE_INDEX = 2;

    @FXML
    private Pagination analyzePaging;

    private DetailsController resultsController;
    private PropertyChartController propertyChartController;
    private ConsistencyBarChartController consistencyBarChartController;
    private PopulationChartController populationChartController;

    private BooleanProperty isSelectedSimulationEnded = new SimpleBooleanProperty();
    private IntegerProperty currentPage = new SimpleIntegerProperty();
    private StringProperty selectedProperty = new SimpleStringProperty();
    private StringProperty selectedEntity = new SimpleStringProperty();

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
            URL resource = getClass().getResource("/component/results/analyze/histogram/property/PropertyChart.fxml"); //todo
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(resource);
            Parent resultsContent = loader.load();
            sp.getChildren().add(resultsContent);

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
            URL resource = getClass().getResource("/component/results/analyze/histogram/consistency/ConsistencyBarChart.fxml"); //todo
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(resource);
            Parent resultsContent = loader.load();
            sp.getChildren().add(resultsContent);

            consistencyBarChartController = loader.getController();

            if (resultsController != null) {
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
            URL resource = getClass().getResource("/component/results/analyze/histogram/population/PopulationChart.fxml"); //todo
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(resource);
            Parent resultsContent = loader.load();
            sp.getChildren().add(resultsContent);

            populationChartController = loader.getController();

            if (resultsController != null) {
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

        try {
            if (RequestHandler.isEnded(newSerialNumber) && selectedEntity.isNotNull().get()) {
                switch (currentPage.get()) {
                    case PROPERTIES_PAGE_INDEX:
                        if (selectedProperty.isNotNull().get()) {
                            setPropertiesChart(selectedProperty.get());
                        }

                        break;
                    case CONSISTENCY_PAGE_INDEX:
                        setConsistencyChart(RequestHandler.getConsistencyByEntityName(newSerialNumber, selectedEntity.get()));
                }
            }

            if (currentPage.get() == POPULATION_PADE_INDEX) {
                setPopulationChart(RequestHandler.getPopulationCountSortedByName(newSerialNumber));
            }
        } catch (Exception ignored) {
        }
    }

    public void setDetailsController(DetailsController detailsController) {
        this.resultsController = detailsController;
        isSelectedSimulationEnded.bind(detailsController.isSelectedSimulationEndedProperty());
        selectedProperty.bind(detailsController.propertyToViewProperty());
        selectedEntity.bind(detailsController.entityToViewProperty());
    }

    private void setPopulationChart(Map<String, Map<Integer, Long>> data) {
        populationChartController.setChart(data);
    }

    public void setPropertiesChart(String property) {
        try {
            if (currentPage.get() == PROPERTIES_PAGE_INDEX && property != null) {
                SimulationDataDTO simulationData = resultsController.getSimulationData();
                propertyChartController.setChart(property, simulationData, resultsController.getAverageProperty());
            }
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }

    public void setConsistencyChart(Map<String, Double> consistency) {
        if (analyzePaging.currentPageIndexProperty().get() == CONSISTENCY_PAGE_INDEX && consistency != null) {
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

    public void clear() {
        setPagination();
    }
}
