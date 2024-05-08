package controllers.ProjetsController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.projets;
import services.ProjetsServices;

public class ModifierProjectController {

    @FXML
    private TextField Nom;

    private String imgName;

    @FXML
    private TextField description;

    @FXML
    private TextField wallet_address;

    @FXML
    private ImageView imageView;

    private final ProjetsServices projectService = new ProjetsServices(); // Assuming you have a ProjectService object

    private projets projectToUpdate;

    private int categoryId;

    public void initData(int projectId, int categoryId) {
        // Use projectId and categoryId to fetch the projets object from the database
        projets project = projectService.getProjectById(projectId); // Assuming you have a method to fetch a project by ID
        if (project != null) {
            this.projectToUpdate = project;
            this.categoryId = categoryId;
            initializeFields();
        } else {
            // Handle the case where the project is not found
            System.out.println("Project not found with ID: " + projectId);
        }
    }


    public void setProjectToUpdate(projets project) {
        this.projectToUpdate = project;
        // Initialize the form fields with the details of the project to update
        initializeFields();
    }

    private void initializeFields() {
        if (projectToUpdate != null) {
            Nom.setText(projectToUpdate.getNom());
            description.setText(projectToUpdate.getDescription());
            wallet_address.setText(projectToUpdate.getWallet_address());
            // Load the image of the project to update
            Image image = new Image(new File(projectToUpdate.getPhotoUrl()).toURI().toString());
            imageView.setImage(image);
        }
    }

    @FXML
    public void updateProject(ActionEvent event) {
        String name = Nom.getText().trim();
        String desc = description.getText().trim();
        String wallet = wallet_address.getText().trim();

        if (name.isEmpty() || desc.isEmpty() || wallet.isEmpty()) {
            showAlert("Empty Fields", "Please fill in all the fields.");
        } else {
            // Update the project object with the new values
            projectToUpdate.setNom(name);
            projectToUpdate.setDescription(desc);
            projectToUpdate.setWallet_address(wallet);

            // Update the project in the database
            projectService.modifierProjet(projectToUpdate);

            // Show confirmation message
            showAlert("Project Updated", "The project has been successfully updated.");

            try {
                // Load the FXML file for the new page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Client/Categories/DisplayCategories.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                showAlert("Error", "Failed to load the page: " + e.getMessage());
                e.printStackTrace(); // Print the stack trace for debugging
            }
        }
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            // Get the path of your source directory
            String sourceDirectory = System.getProperty("user.dir");

            sourceDirectory = sourceDirectory + "/uploadedimage";

            // Define a new directory within your source directory to save images
            String destinationDirectory = sourceDirectory + File.separator + "img";

            // Create the directory if it doesn't exist
            File destDir = new File(destinationDirectory);
            if (!destDir.exists()) {
                destDir.mkdirs();
            }

            String fileName = file.getName();
            Path destinationPath = new File(destinationDirectory, fileName).toPath();
            try {
                // Save the file to the destination directory
                Files.copy(file.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File uploaded successfully to: " + destinationPath);

                // Set the image view
                imgName = destinationPath.toString();
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);
            } catch (IOException e) {
                System.out.println("Error uploading file: " + e.getMessage());
                showAlert("Error", "Error uploading file: " + e.getMessage());
            }
        } else {
            System.out.println("No file selected.");
        }
    }

    // Additional methods for navigation or handling image upload can be added here
}
