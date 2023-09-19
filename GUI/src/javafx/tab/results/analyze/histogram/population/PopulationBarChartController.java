package javafx.tab.results.analyze.histogram.population;

import impl.SimulationDataDTO;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.ScrollPane;

import java.util.*;

public class PopulationBarChartController {

    @FXML
    private BarChart<String, Integer> histogramBarChart;

    private SimulationDataDTO data;

    @FXML
    private void initialize() {
    }

    public void setChart(String propertyToView, SimulationDataDTO data) {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        List<Map.Entry<Object, Integer>> values;

        histogramBarChart.getData().clear();

        series.setName(propertyToView);
        values = createSortedValuesToAmountMap(data.getPropertyOfEntitySortedByValues());

        values.forEach(entry -> series.getData().add(new XYChart.Data<>(entry.getKey().toString(), entry.getValue())));

        if (!series.getData().isEmpty()) {
            histogramBarChart.getData().add(series);
        }
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

    public void setData(SimulationDataDTO data) {
        this.data = data;
    }
}
