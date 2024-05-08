package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class NavBar {

    @FXML
    private BorderPane mainContainer;





    @FXML
    public void showDisplayPublications() {
        try {
            Node displayPubs = FXMLLoader.load(getClass().getResource("/Front/Publication/affichagePub.fxml"));
            mainContainer.setCenter(displayPubs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showDisplaynft() {
        try {
            Node displayPubs = FXMLLoader.load(getClass().getResource("/FrontOffice/List_NFTS.fxml"));
            mainContainer.setCenter(displayPubs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showDisplayConsultations() {
        try {
            Node displayCons = FXMLLoader.load(getClass().getResource("/Front/Consultation/affichageConsultation.fxml"));
            mainContainer.setCenter(displayCons);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, for example, by showing an error message
        }
    }





    @FXML
    public void showHome() {
        try {
            Node home = FXMLLoader.load(getClass().getResource("/Front/HomeView.fxml"));
            mainContainer.setCenter(home);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        showHome();


    }


}