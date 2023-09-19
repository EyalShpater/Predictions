package javafx.tab.results.analyze;

import impl.SimulationDataDTO;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.StackPane;
import javafx.tab.results.ResultsController;
import javafx.tab.results.analyze.histogram.population.PopulationBarChartController;

import java.net.URL;

public class AnalyzePaginationController {

    @FXML
    private Pagination analyzePaging;

    private ResultsController resultsController;
    private PopulationBarChartController populationBarChartController;

    public void setPopulationChart(String property, SimulationDataDTO data) {
        populationBarChartController.setChart(property, data);
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
                    return test();
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

    private Node test() {
        StackPane sp = new StackPane();

        sp.getChildren().add(new Label("Page 2"));

        return sp;
    }

    public void setResultsController(ResultsController resultsController) {
        this.resultsController = resultsController;
    }
}
