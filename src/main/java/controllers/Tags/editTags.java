package controllers.Tags;

import models.Tags;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import services.ServiceTags;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDate;

public class editTags {

    @FXML
    private DatePicker TfDateEdit;

    @FXML
    private TextField TfDescriptionEdit;

    @FXML
    private Button TfEdit;

    @FXML
    private TextField TfIdUserEdit;

    @FXML
    private TextField TfTitreEdit;

    @FXML
    private Button btnImageEdit;

    @FXML
    private ImageView imagePreview;

    private File imageFile; // For storing the selected image file
    private Tags currentPublication; // The publication being edited
    private ServiceTags serviceTags = new ServiceTags();

    public void setTags(Tags pub) {
        currentPublication = pub;
        TfTitreEdit.setText(pub.getNom());
        TfDescriptionEdit.setText(pub.getDescription());


        // Set image with a unique timestamp to prevent caching
        String imagePath = "/images/" + pub.getNom() + "?time=" + System.currentTimeMillis();
        InputStream imageStream = getClass().getResourceAsStream(imagePath);
        if (imageStream != null) {
            Image image = new Image(imageStream);
            imagePreview.setImage(image);
        } else {
            System.out.println("Image not found: " + imagePath);
            // You can set a default image here if necessary
        }
    }




    @FXML
    void EditImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an image for the publication");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.jpeg", "*.bmp", "*.gif"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            imageFile = file;
            Image image = new Image(file.toURI().toString());
            imagePreview.setImage(image);
        }
    }

    @FXML
    void ModifierPublication(ActionEvent event) {
        try {
            String titre = TfTitreEdit.getText();
            String description = TfDescriptionEdit.getText();
            int idUser = Integer.parseInt(TfIdUserEdit.getText());
            LocalDate dateLocal = TfDateEdit.getValue();
            java.sql.Date date = java.sql.Date.valueOf(dateLocal);

            // Update the publication object
            currentPublication.setNom(titre);
            currentPublication.setDescription(description);

            // Update the image if a new one was selected
            if (imageFile != null) {
                String imagesDir = "src/main/resources/images/";
                Files.createDirectories(Paths.get(imagesDir));
                Path sourcePath = imageFile.toPath();
                Path destinationPath = Paths.get(imagesDir + imageFile.getName());
                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                String imageName = destinationPath.getFileName().toString();
                currentPublication.setImageT(imageName);
            }

            serviceTags.modifier(currentPublication);

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Tag has been updated successfully.");
            alert.showAndWait();

        } catch (IOException | NumberFormatException | SQLException e) {
            // Show error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
