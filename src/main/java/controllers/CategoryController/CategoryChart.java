package controllers.CategoryController;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import services.CategoryService;

import java.sql.SQLException;
import java.util.Map;

public class CategoryChart {

    @FXML
    private LineChart<String, Number> lineChartCategory;

    public void initialize() throws SQLException {
        CategoryService categoryService = new CategoryService();


        Map<String, Integer> projectsPerCategory = categoryService.getProjectsPerCategory();
        XYChart.Series<String, Number> categorySeries = new XYChart.Series<>();
        categorySeries.setName("Number of Projects per Category");
        for (Map.Entry<String, Integer> entry : projectsPerCategory.entrySet()) {
            categorySeries.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        lineChartCategory.getData().add(categorySeries);
    }
}
