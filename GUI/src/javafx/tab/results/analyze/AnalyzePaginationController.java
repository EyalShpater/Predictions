package javafx.tab.results.analyze;

import impl.SimulationDataDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Pagination;
import javafx.scene.layout.StackPane;
import javafx.tab.results.ResultsController;
import javafx.tab.results.analyze.histogram.consistency.ConsistencyBarChartController;
import javafx.tab.results.analyze.histogram.population.PopulationBarChartController;

import java.net.URL;
import java.util.Map;

public class AnalyzePaginationController {
    private static final int POPULATION_PAGE_INDEX = 0;
    private static final int CONSISTENCY_PAGE_INDEX = 1;

    @FXML
    private Pagination analyzePaging;

    private ResultsController resultsController;
    private PopulationBarChartController populationBarChartController;
    private ConsistencyBarChartController consistencyBarChartController;

    public void setPopulationChart(String property, SimulationDataDTO data) {
        populationBarChartController.setChart(property, data);
    }

    public void setConsistencyChart(Map<String, Double> consistency) {
        if (analyzePaging.currentPageIndexProperty().get() == CONSISTENCY_PAGE_INDEX) {
            consistencyBarChartController.setChart(consistency);
        }
    }

    @FXML
    private void initialize() {
        setPagination();
    }

    private void setPagination() {
        analyzePaging.setPageFactory(pageIndex -> {
            switch (pageIndex) {
                case 0:
                    return createPopulationBarChart();
                case 1:
                    return createConsistencyChart();
//                case 2:
//                    return createCustomPage();
                default:
                    return null;
            }
        });
    }

    private Node createPopulationBarChart() {
        StackPane sp = new StackPane();

        try {
            URL resource = getClass().getResource("/javafx/tab/results/analyze/histogram/population/PopulationBarChart.fxml");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(resource);
            Parent resultsContent = loader.load();
            sp.getChildren().add(resultsContent);

            populationBarChartController = loader.getController();
        } catch (Exception ignored) {

        }

        return sp;
    }

    private Node createConsistencyChart() {
        StackPane sp = new StackPane();

        try {
            URL resource = getClass().getResource("/javafx/tab/results/analyze/histogram/consistency/ConsistencyBarChart.fxml");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(resource);
            Parent resultsContent = loader.load();
            sp.getChildren().add(resultsContent);

            consistencyBarChartController = loader.getController();
        } catch (Exception ignored) {

        }

        return sp;
    }

    public void setResultsController(ResultsController resultsController) {
        this.resultsController = resultsController;
    }
}
