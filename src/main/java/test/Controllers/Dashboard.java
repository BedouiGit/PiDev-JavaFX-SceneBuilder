package test.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class Dashboard {

    @FXML
    private BorderPane mainContainer;

    @FXML
    public void showAddPublication() {
        try {
            Node addPub = FXMLLoader.load(getClass().getResource("/Back/Publication/ajoutPub.fxml"));
            mainContainer.setCenter(addPub);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, for example, by showing an error message
        }
    }

    @FXML
    public void showDisplayPublications() {
        try {
            Node displayPubs = FXMLLoader.load(getClass().getResource("/Back/Publication/affichagePub.fxml"));
            mainContainer.setCenter(displayPubs);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, for example, by showing an error message
        }
    }
    @FXML
    public void showDisplayTags() {
        try {
            Node displayPub = FXMLLoader.load(getClass().getResource("/Back/Tags/affichageTag.fxml"));
            mainContainer.setCenter(displayPub);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, for example, by showing an error message
        }
    }
    @FXML
    public void showHome() {
        try {
            Node home = FXMLLoader.load(getClass().getResource("/Back/HomeView.fxml"));
            mainContainer.setCenter(home);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, for example, by showing an error message
        }
    }
    @FXML
    public void showAddTag() {
        try {
            Node addPub = FXMLLoader.load(getClass().getResource("/Back/Tags/ajoutPub.fxml"));
            mainContainer.setCenter(addPub);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, for example, by showing an error message
        }
    }

    @FXML
    public void displaylistAct() {
        try {
            Node act = FXMLLoader.load(getClass().getResource("/Back/Activite/AllbackListes.fxml"));
            mainContainer.setCenter(act);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, for example, by showing an error message
        }
    }


    @FXML
    public void diplayaddAct() {
        try {
            Node acti = FXMLLoader.load(getClass().getResource("/Back/Activite/ajoutActivite.fxml"));
            mainContainer.setCenter(acti);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, for example, by showing an error message
        }
    }

    @FXML
    private void initialize() {
        showHome();
    }
}
