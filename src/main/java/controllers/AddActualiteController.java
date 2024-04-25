package controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

import entities.actualite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ActualiteService;

public class AddActualiteController {

    @FXML
    private TextField idActualitefx;
    @FXML
    private TextField titreActualitefx;
    @FXML
    private TextField contenuActualitefx;
    @FXML
    private TextField categorieActualitefx;

    @FXML
    private Button GoBack;
    @FXML
    private Button Ajouter;
    @FXML
    private Button list;

    @FXML
    private Button uploadImageButton;

    private final ActualiteService actualiteService = new ActualiteService();

    private String imageUrl;

    @FXML
    void handleAjouter(ActionEvent actualite) throws SQLException {
        if (validateFields()) {
            int id = Integer.parseInt(idActualitefx.getText());
            String titre = titreActualitefx.getText();
            String contenu = contenuActualitefx.getText();
            String categorie = categorieActualitefx.getText();
            LocalDateTime datePublication = LocalDateTime.now(); // Current date and time
            actualite newActualite = new actualite(id, titre, contenu, categorie, datePublication, imageUrl);

            actualiteService.ajouter(newActualite);
            showAlert("Success", "Actualite added successfully.");
        }
    }

    @FXML
    void handleUploadImage(ActionEvent actualite) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        File selectedFile = fileChooser.showOpenDialog(uploadImageButton.getScene().getWindow());
        if (selectedFile != null) {
            try {
                imageUrl = selectedFile.toURI().toURL().toString();
            } catch (MalformedURLException e) {
                showAlert("Error", "Failed to load image: " + e.getMessage());
            }
        }
    }

    private boolean validateFields() {
        String idText = idActualitefx.getText();
        String titre = titreActualitefx.getText();
        String contenu = contenuActualitefx.getText();
        String categorie = categorieActualitefx.getText();

        if (idText.isEmpty() || !Pattern.matches("\\d+", idText)) {
            showAlert("Error", "Please enter a valid ID.");
            return false;
        }

        if (titre.isEmpty() || titre.length() < 5 || titre.length() > 50) {
            showAlert("Error", "Please enter a title between 5 and 50 characters.");
            return false;
        }

        if (contenu.isEmpty() || contenu.length() < 9 || contenu.length() > 200) {
            showAlert("Error", "Please enter content between 9 and 200 characters.");
            return false;
        }

        if (categorie.isEmpty() || categorie.length() < 2 || categorie.length() > 20) {
            showAlert("Error", "Please enter a category between 2 and 20 characters.");
            return false;
        }

        if (imageUrl == null || imageUrl.isEmpty()) {
            showAlert("Error", "Please upload an image.");
            return false;
        }

        return true;
    }


    @FXML
    private void handleListAction(ActionEvent event) {
        try {
            // Load the display FXML file using a relative path
            Parent root = FXMLLoader.load(getClass().getResource("/displayNews.fxml"));

            // Create a new scene
            Scene scene = new Scene(root);

            // Get the stage from the event
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            // Set the scene to the stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
