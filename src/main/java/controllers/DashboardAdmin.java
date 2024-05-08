package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import models.Role;
import controllers.addUserByAdmin;
import services.userService;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DashboardAdmin {
    @FXML
    private Button add;

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


    public void logout(){
        Stage stage = (Stage) pieChart.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void addTep()throws IOException  {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/addUserByAdmin.fxml"));
        Parent root = loader.load();
        addUserByAdmin addController = loader.getController(); // Get the controller instance associated with the FXML
        Role role = Role.ROLE_USER;
        addController.initData(role); // Call initData on the correct controller instance
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();


    }


    public void openUsersList(ActionEvent actionEvent)throws IOException  {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListUsers.fxml"));
        Parent root = loader.load();
        ListUsers addController = loader.getController(); // Get the controller instance associated with the FXML
        Role role = Role.ROLE_USER;
        addController.initData(role); // Call initData on the correct controller instance
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
    }


    @FXML
    void ListNFT(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/BackOffice/BackNft.fxml"));
        Parent root = loader.load();
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
    }


    @FXML
    void openCommandeList(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/BackOffice/BackCommande.fxml"));
        Parent root = loader.load();
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
    }


    @FXML
    void ListPublication(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Dashboard.fxml"));
        Parent root = loader.load();
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
    }

    @FXML
    void ListTags(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/HomeView.fxml"));
        Parent root = loader.load();
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
    }
}
