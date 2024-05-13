package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Color;
import services.ServicePosts;

import java.net.URL;
import java.util.ResourceBundle;
public class Stats implements Initializable {


    private int totalLikes = 1000;


    @FXML
    private PieChart pieChart;

    @FXML
    private Label highEngagementLabel=new Label();

    @FXML
    private Label moderateEngagementLabel=new Label();

    @FXML
    private Label lowEngagementLabel=new Label();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ServicePosts Sp=new ServicePosts();

     //   int totalLikes = Sp.getTotalLikesFromDatabase();

        int[] engagementLevels = Sp.getEngagementLevels();



        int highEngagementCount = engagementLevels[0];
        int moderateEngagementCount = engagementLevels[1];
        int lowEngagementCount = engagementLevels[2];


        highEngagementLabel.setText("Highly Engaging: " + highEngagementCount);
        moderateEngagementLabel.setText("Moderately Engaging: " + moderateEngagementCount);
        lowEngagementLabel.setText("Low Engaging: " + lowEngagementCount);

        // Calculate engagement


        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Highly Engaging", highEngagementCount),
                        new PieChart.Data("Moderately Engaging", moderateEngagementCount),
                        new PieChart.Data("Low Engaging", lowEngagementCount));
        pieChart.setData(pieChartData);
        pieChart.setTitle("News Engagement");




    }





}

