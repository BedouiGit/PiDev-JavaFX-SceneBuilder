package controllers;

import entities.actualite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ActualiteService;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifyActualiteController implements Initializable {

    public Button uploadImageButton;
    @FXML
    private TextField descEMod;

    @FXML
    private ListView<actualite> eventView1;

    @FXML
    private TextField idMod;

    @FXML
    private TextField titreMod;

    @FXML
    private TextField categorieMod;

    @FXML
    private TextField imageMod;

    @FXML
    private Button removeEvent1;

    @FXML
    private Button updateEvent;

    private final ActualiteService ES1 = new ActualiteService();
    private ObservableList<actualite> actualites;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            actualites = FXCollections.observableArrayList(ES1.recuperer());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        eventView1.setItems(actualites);
        eventView1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Pre-fill the form fields with the attributes of the selected actualite
                idMod.setText(String.valueOf(newValue.getId()));
                titreMod.setText(newValue.getTitre());
                categorieMod.setText(newValue.getCategorie());
                imageMod.setText(newValue.getImageUrl());
                descEMod.setText(newValue.getContenu());
            }
        });
    }

    @FXML
    void removeEvent1(ActionEvent actualite) throws SQLException {
        actualite selectedEvent = eventView1.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            ES1.supprimer(selectedEvent);
            actualites.clear();
            actualites.addAll(ES1.recuperer());
            eventView1.setItems(actualites);
        }
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

    @FXML
    void handleUploadImageForUpdate(ActionEvent actualite) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        File selectedFile = fileChooser.showOpenDialog(uploadImageButton.getScene().getWindow());
        if (selectedFile != null) {
            try {
                // Update the image URL for the selected actualite
                uploadImageButton.setText(selectedFile.toURI().toURL().toString());
            } catch (MalformedURLException e) {
                showAlert("Error", "Failed to load image: " + e.getMessage());
            }
        }
    }

    @FXML
    void updateEvent(ActionEvent actualite) {
        // Get the selected actualite from the ListView
        actualite selectedActualite = eventView1.getSelectionModel().getSelectedItem();

        try {
            // Modify the selected actualite object with the provided details
            selectedActualite.setTitre(titreMod.getText());
            selectedActualite.setCategorie(categorieMod.getText());
            selectedActualite.setImageUrl(uploadImageButton.getText());
            selectedActualite.setContenu(descEMod.getText());


            // Call the modifier method of your service to update the actualite in the database
            ES1.modifier(selectedActualite);

            // Clear the list of actualites
            actualites.clear();

            // Retrieve the updated list of actualites from the database and add them to the ObservableList
            actualites.addAll(ES1.recuperer());

            // Set the updated list of actualites to the ListView
            eventView1.setItems(actualites);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
