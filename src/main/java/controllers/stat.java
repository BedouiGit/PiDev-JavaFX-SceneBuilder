package controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import services.userService;

import java.util.Map;

public class stat {
    @FXML
    private PieChart pieChart;

    @FXML
    private LineChart<String, Number> lineChart;
    public void initialize() {
        userService userService = new userService();
        pieChart.getData().clear();

        // Add data points to the pie chart
        Map<String, Integer> userDataByStatus = userService.getUserDataByStatus();
        int total = userDataByStatus.values().stream().mapToInt(Integer::intValue).sum();

        for (Map.Entry<String, Integer> entry : userDataByStatus.entrySet()) {
            double percentage = ((double) entry.getValue() / total) * 100;
            PieChart.Data slice = new PieChart.Data(entry.getKey() + " (" + String.format("%.1f", percentage) + "%)", entry.getValue());
            pieChart.getData().add(slice);
        }

        // Add data points to the line chart
        Map<String, Integer> userDataByAddress = userService.getUserDataByAddress();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("User Data by Address");

        for (Map.Entry<String, Integer> entry : userDataByAddress.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        lineChart.getData().add(series);
    }
}
