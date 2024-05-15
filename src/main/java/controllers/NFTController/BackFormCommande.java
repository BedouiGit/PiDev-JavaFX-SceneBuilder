package controllers.NFTController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import models.Commande;
import models.NFT;
import services.CommandeService;

import java.io.IOException;

public class BackFormCommande {

    @FXML
    private TextField EmailField;

    @FXML
    private TextField TotalField;

    @FXML
    private TextField WalletField;

    @FXML
    private Button addT;

    @FXML
    private Button btn_workbench1111121;

    @FXML
    private Button home;

    @FXML
    private HBox root;

    @FXML
    void back(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NFTFxml/BackOffice/BackCommande.fxml"));
            Parent parent = loader.load();

            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void cancelcommande(ActionEvent event) {

        cs.delete(selectedCommande);

    }

    private final CommandeService cs = new CommandeService();


    @FXML
    void updatecommande(ActionEvent event) {

        selectedCommande.setEmail(EmailField.getText());
        selectedCommande.setWallet(WalletField.getText());
        selectedCommande.setTotal(Double.parseDouble(TotalField.getText()));

        if (validateInput()) {
            try {
                cs.update(selectedCommande);
                showAlert("Update Successful", "The NFT has been updated successfully.");
            } catch (Exception e) {
                showAlert("Update Error", "Failed to update the NFT: " + e.getMessage());
            }
        }
    }

    private Commande selectedCommande;

    public void setcommande(Commande commande) {
        this.selectedCommande = commande;

        EmailField.setText(commande.getEmail());
        TotalField.setText(String.valueOf(commande.getTotal()));
        WalletField.setText(commande.getWallet());
    }


    private boolean validateInput() {
        // Regular expression for validating an email address
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        // Regular expression for validating a wallet address (e.g., Ethereum address format)
        String walletRegex = "^0x[a-fA-F0-9]{40}$";

        // Check if the email field is empty or invalid
        if (EmailField.getText().isEmpty() || !EmailField.getText().matches(emailRegex)) {
            showAlert("Validation Error", "Please enter a valid email address.");
            return false;
        }
        // Check if the wallet address field is empty or invalid
        if (WalletField.getText().isEmpty() || !WalletField.getText().matches(walletRegex)) {
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
