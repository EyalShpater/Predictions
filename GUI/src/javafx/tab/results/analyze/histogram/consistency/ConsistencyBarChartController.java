package javafx.tab.results.analyze.histogram.consistency;

import impl.SimulationDataDTO;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;

import java.util.*;

public class ConsistencyBarChartController {

    @FXML
    private BarChart<String, Double> consistencyChart;

    @FXML
    private void initialize() {

    }

    public void setChart(Map<String, Double> data) {
        XYChart.Series<String, Double> series = new XYChart.Series<>();

        consistencyChart.getData().clear();

//        series.setName("");

        data.forEach((key, value) -> series.getData().add(new XYChart.Data<>(key, value)));

        if (!series.getData().isEmpty()) {
            consistencyChart.getData().add(series);
        }
    }
}