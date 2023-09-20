package javafx.tab.results.analyze.histogram.property;

import impl.SimulationDataDTO;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;

import java.util.*;

public class PropertyChartController {

    @FXML
    private BarChart<String, Integer> histogramBarChart;

    @FXML
    private Label averageLabel;

    private SimulationDataDTO data;

    public void setChart(String propertyToView, SimulationDataDTO data, Double average) {
        histogramBarChart.getData().clear();

        if (propertyToView != null && data != null) {
            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            List<Map.Entry<Object, Integer>> values;

            series.setName(propertyToView);
            values = createSortedValuesToAmountMap(data.getPropertyOfEntitySortedByValues());

            values.forEach(entry -> series.getData().add(new XYChart.Data<>(entry.getKey().toString(), entry.getValue())));

            if (!series.getData().isEmpty()) {
                histogramBarChart.getData().add(series);
            }

            if (average != null) {
                averageLabel.setVisible(true);
                averageLabel.setText(String.format("Average: %.2f", average));
            } else {
                averageLabel.setVisible(false);
            }
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
