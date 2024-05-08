package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Role;

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
    void showAddCommande() {
        try {
            Node act = FXMLLoader.load(getClass().getResource("/BackOffice/BackCommande.fxml"));
            mainContainer.setCenter(act);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, for example, by showing an error message
        }
    }

    @FXML
    void showAddNFT() {
        try {
            Node act = FXMLLoader.load(getClass().getResource("/BackOffice/BackNft.fxml"));
            mainContainer.setCenter(act);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, for example, by showing an error message
        }
    }
    @FXML
    void Prediction() {
        try {
            Node act = FXMLLoader.load(getClass().getResource("/BackOffice/stats.fxml"));
            mainContainer.setCenter(act);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, for example, by showing an error message
        }
    }
    @FXML
    void NavigateToCategory(){
        try {
            Node act = FXMLLoader.load(getClass().getResource("/fxml/Admin/ListcategoryAdmin.fxml"));
            mainContainer.setCenter(act);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, for example, by showing an error message
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


    @FXML
    private void initialize() {
        showHome();
    }
}
