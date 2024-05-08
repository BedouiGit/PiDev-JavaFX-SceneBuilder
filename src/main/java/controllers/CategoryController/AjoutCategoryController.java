package controllers.CategoryController;

import com.twilio.Twilio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
import utils.NavigationUtil;
import services.smsService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;



public class AjoutCategoryController {

    @FXML
    private TextField nameCategoryfx;

    @FXML
    private TextField descCategoryfx;

    @FXML
    private ImageView imageView;

    private String imgName;

    private final CategoryService SC = new CategoryService();

    private static final String ACCOUNT_SID = "AC0002e7c9fb46359fc7c0884c8313a819";
    private static final String AUTH_TOKEN = "f360ede7d59e486841fb22e444801015";

    // Twilio phone number
    private static final String TWILIO_NUMBER = "+12563339571";


    @FXML
    private void goBack(ActionEvent event) {

        try {
            NavigationUtil.navigateTo("/fxml/Client/Categories/DisplayCategories.fxml", ((Node) event.getSource()).getScene().getRoot());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendPaymentConfirmationSMS(String name) {
        // Formater le message SMS
        String messageSMS = "A new Category is added to our NFTUN SITE NAME "
                + name +" You can visit our application for more details ";

        // Initialiser Twilio
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);



        // Envoyer le SMS
        Message message = Message.creator(
                        new PhoneNumber("+21652715523" ),  // Ajoutez le préfixe international
                        new PhoneNumber(TWILIO_NUMBER),  // Numéro de téléphone Twilio
                        messageSMS)
                .create();

        System.out.println("SMS envoyé : " + message.getSid());
    }

    @FXML
    void handleAjouter(ActionEvent event) {


        if (validateFields()) {
            String name = nameCategoryfx.getText();
            String description = descCategoryfx.getText();
            String photoUrl = imgName;




            category newCategory = new category(0, name, description, photoUrl);

            try {
                SC.ajouter(newCategory);
                showAlert("Success", "Category added successfully.");

                // Load the FXML file for the new page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Client/Categories/DisplayCategories.fxml"));
                Parent root = loader.load();

                // Get the stage from the event source
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

                sendPaymentConfirmationSMS(name);

            } catch (SQLException | IOException e) {
                showAlert("Error", "Failed to add category: " + e.getMessage());
            }
        }
    }


    private boolean validateFields() {

        String name = nameCategoryfx.getText();
        String description = descCategoryfx.getText();
        String photoUrl = imgName;




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

}
