package component.results.analyze.histogram.population;

import impl.SimulationDataDTO;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.util.Pair;

import java.util.*;

public class PopulationChartController {

    @FXML
    private AreaChart<Integer, Long> populationChart;

    @FXML
    private void initialize() {
        populationChart.setAnimated(false);
    }

    public void setChart(Map<String, Map<Integer, Long>> data) {
        populationChart.getData().clear();

        data.forEach((name, tickToPopulation) -> {
            XYChart.Series<Integer, Long> s = new XYChart.Series<>();
            s.setName(name);

            tickToPopulation.forEach((tick, population) -> {
                s.getData().add(new XYChart.Data<>(tick, population));
            });

            if (!s.getData().isEmpty()) {
                populationChart.getData().add(s);
            }
        });
    }
}