package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import models.NFT;
import services.NFTService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class BackFormNFT {

    private final NFTService ps = new NFTService();

    @FXML
    private Label InscriMessageLabel;

    @FXML
    private TextField NFTPriceField;

    @FXML
    private TextField NFTStatusField;

    @FXML
    private TextField NFTnameField;

    @FXML
    private Button addT;

    @FXML
    private Button btn_workbench1111121;

    @FXML
    private Label errorCategorie;

    @FXML
    private Label errorNom;

    @FXML
    private Label errorPrix;

    @FXML
    private Label errorQuantite;

    @FXML
    private Text errorUploadingImage;

    @FXML
    private Button home;

    @FXML
    private ImageView imageUploaded;

    @FXML
    private HBox root;

    private NFT selectednft;

    private String uploadImagePath;

    @FXML
    void back(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Dashboard.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle

            Scene scene = ((Node) event.getSource()).getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void cancelProduct(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Dashboard.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle

            Scene scene = ((Node) event.getSource()).getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void submitProduct(ActionEvent event) {
        if (validateInput()) {
            try {
                if(selectednft != null)
                {
                    selectednft.setStatus(NFTStatusField.getText());
                    selectednft.setName(NFTnameField.getText());
                    selectednft.setImage(uploadImagePath);
                    selectednft.setPrice(Double.parseDouble(NFTPriceField.getText()));
                    ps.update(selectednft);
                    showAlert("ADD Successful", "The NFT has been added successfully.");


                }else{
                    ps.add(new NFT(NFTnameField.getText(), NFTStatusField.getText(), uploadImagePath, Double.parseDouble(NFTPriceField.getText())));
                    showAlert("ADD Successful", "The NFT has been added successfully.");


                }


            } catch (NumberFormatException e) {
                showAlert("Error", "Price must be a valid number.");
            } catch (Exception e) {
                showAlert("Update Error", "Failed to add the NFT: " + e.getMessage());
            }
        }
    }

    @FXML
    void uploadImage(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a file (*.jpg, *.png)", "*.jpg", "*.png");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {
                Path imgDir = Paths.get("img");
                if (!Files.exists(imgDir)) {
                    Files.createDirectories(imgDir);
                }
                Path targetPath = imgDir.resolve(file.getName());
                Files.copy(file.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                imageUploaded.setImage(new Image(targetPath.toUri().toString()));
                uploadImagePath = targetPath.toString(); // Store the file path
            } catch (IOException e) {
                showAlert("Error", "Failed to save the image: " + e.getMessage());
            }
        }

    }

    public void setProduct(NFT nft) {
        this.selectednft = nft;

        NFTnameField.setText(nft.getName());
        NFTPriceField.setText(String.valueOf(nft.getPrice()));
        NFTStatusField.setText(nft.getStatus());

        uploadImagePath = nft.getImage();
    }

    private boolean validateInput() {
        if (NFTnameField.getText().isEmpty() || NFTPriceField.getText().isEmpty()) {
            showAlert("Validation Error", "Name and price must not be empty.");
            return false;
        }
        if (Double.parseDouble(NFTPriceField.getText())>999999) {
            showAlert("Validation Error", "Price should be less than 999999$");
            return false;
        }
        try {
            Double.parseDouble(NFTPriceField.getText());
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Price must be a valid number.");
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

}
