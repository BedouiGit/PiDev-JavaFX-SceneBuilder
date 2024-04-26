package controllers.CategoryController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import models.category;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.regex.Pattern;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.CategoryService;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;


public class AjoutCategoryController {
    @FXML
    private TextField idCategoryfx;

    @FXML
    private TextField nameCategoryfx;

    @FXML
    private TextField descCategoryfx;



    @FXML
    private Button GoBack;



    @FXML
    private ImageView imageView;

    private String imgName;

    private final CategoryService SC = new CategoryService();

    @FXML
    private void goBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Projets/Homepage.fxml"));
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleAjouter(ActionEvent event) {
        if (validateFields()) {
            int id = Integer.parseInt(idCategoryfx.getText());
            String name = nameCategoryfx.getText();
            String description = descCategoryfx.getText();
            String photoUrl = imgName;

            category newCategory = new category(id, name, description, photoUrl);

            try {
                SC.ajouter(newCategory);
                showAlert("Success", "Category added successfully.");

                // Load the FXML file for the new page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Projets/Categories.fxml"));
                Parent root = loader.load();

                // Get the stage from the event source
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (SQLException | IOException e) {
                showAlert("Error", "Failed to add category: " + e.getMessage());
            }
        }
    }


    private boolean validateFields() {
        String idText = idCategoryfx.getText();
        String name = nameCategoryfx.getText();
        String description = descCategoryfx.getText();
        String photoUrl = imgName;

        if (idText.isEmpty() || !Pattern.matches("\\d+", idText)) {
            showAlert("Error", "Please enter a valid ID.");
            return false;
        }
        int id = Integer.parseInt(idText);
        if (id <= 0 || id > 1000) {
            showAlert("Error", "ID must be between 1 and 1000.");
            return false;
        }

        if (name.isEmpty() || name.length() > 50 || !Pattern.matches("[a-zA-Z\\s]+", name)) {
            showAlert("Error", "Please enter a valid category name (maximum 50 characters, letters and spaces only).");
            return false;
        }

        if (description.isEmpty() || description.length() > 200 || !Pattern.matches("[a-zA-Z0-9\\s]+", description)) {
            showAlert("Error", "Please enter a valid category description (maximum 200 characters, letters, digits, and spaces only).");
            return false;
        }

        if (photoUrl.isEmpty()) {
            showAlert("Error", "Please enter a valid photo URL.");
            return false;
        }

        return true;
    }

    private boolean isValidURL(String url) {
        String urlRegex = "^(https?|ftp)://.+";
        return Pattern.matches(urlRegex, url);
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            String destinationDirectory = "C:\\xampp\\htdocs\\img";
            String fileName = file.getName();
            Path destinationPath = new File(destinationDirectory, fileName).toPath();
            try {
                Files.copy(file.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File uploaded successfully to: " + destinationPath);
                imgName = fileName;
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);
            } catch (Exception e) {
                System.out.println("Error uploading file: " + e.getMessage());
                showAlert("Error", "Error uploading file: " + e.getMessage());
            }
        } else {
            System.out.println("No file selected.");
        }
    }
}
