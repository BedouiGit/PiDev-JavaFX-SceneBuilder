package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import models.Commande;
import models.NFT;
import services.CommandeService;
import services.NFTService;
import javafx.scene.image.ImageView;

public class CheckoutForm {

    @FXML
    private TextField WalletAdressTF;

    @FXML
    private TextField EmailAddressTF;

    @FXML
    private ImageView nftImageView;

    @FXML
    private Label nftNameLabel;

    @FXML
    private Label nftPriceLabel;


    private NFT nft;
    private final NFTService ps = new NFTService();
    private final CommandeService cs = new CommandeService();

    public void initData(NFT nft) {
        this.nft = ps.getOne(nft.getId());
        nftNameLabel.setText(nft.getName());
        nftPriceLabel.setText(String.valueOf(nft.getPrice()));

        String imagePath = "file:C:/Users/Admin/Desktop/Java_badbud/nfttun/" + nft.getImage();
        Image image = new Image(imagePath);
        nftImageView.setImage(image);
    }


    @FXML
    void BuyNowbtn(ActionEvent event) {
        if (validateInput()) {
            try {
                // Assuming the constructor Commande(String wallet, String email, double total)
                Commande commande = new Commande(EmailAddressTF.getText(), WalletAdressTF.getText(), nft.getPrice());
                cs.add(commande);
                nft.setStatus("private");
                ps.update(nft);
                showAlert("ADD Successful", "The Commande has been added successfully.");
            } catch (Exception e) {  // General exception handling; specific exceptions should be handled appropriately
                showAlert("Error", "Failed to add the Commande: " + e.getMessage());
            }
        } else {
            showAlert("Validation Error", "Please check your input and try again.");
        }
    }

    private boolean validateInput() {
        // Regular expression for validating an email address
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        // Regular expression for validating a wallet address (e.g., Ethereum address format)
        String walletRegex = "^0x[a-fA-F0-9]{40}$";

        // Check if the email field is empty or invalid
        if (EmailAddressTF.getText().isEmpty() || !EmailAddressTF.getText().matches(emailRegex)) {
            showAlert("Validation Error", "Please enter a valid email address.");
            return false;
        }
        // Check if the wallet address field is empty or invalid
        if (WalletAdressTF.getText().isEmpty() || !WalletAdressTF.getText().matches(walletRegex)) {
            showAlert("Validation Error", "Please enter a valid wallet address.");
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
