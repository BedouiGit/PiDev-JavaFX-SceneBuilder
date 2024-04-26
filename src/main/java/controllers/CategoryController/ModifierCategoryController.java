package controllers.CategoryController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import models.category;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import services.CategoryService;

import java.io.IOException;
import java.sql.SQLException;

public class ModifierCategoryController {

    @FXML
    private TextField coursurl;

    @FXML
    private TextField niveau;

    @FXML
    private TextField nomcour;

    @FXML
    private TextField nommodule;

    private category category;
    private final CategoryService cs = new CategoryService();

    @FXML
    public void setCours(category category) {
        this.category = category;
        coursurl.setText(category.getPhotoUrl());
        niveau.setText(category.getDescription());
        nomcour.setText(category.getNom());

    }

    @FXML
    void modifiercour(ActionEvent event) throws SQLException, IOException {
        String nomCours = nomcour.getText();
        String niveauStr = niveau.getText();
        String coursurlStr = coursurl.getText(); // Correct variable name

        boolean vnom = validerNomCours(nomCours);
        boolean vniveauStr = validerNiveau(niveauStr);


        if (vnom ) {
            // Conversion de niveau en entier après validation
            int niveauInt = Integer.parseInt(niveauStr);

            // Mise à jour du cours avec les valeurs validées
            category.setPhotoUrl(coursurlStr); // Use the correct variable name
            category.setDescription(niveauStr); // Use niveauStr instead of niveau.getText()
            category.setNom(nomCours);

            cs.modifier(category);
            showAlert("Success", "Category modified successfully.");

            // Load the FXML file for the new page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Projets/Categories.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            afficherMessageErreur("Erreur de validation. Vérifiez les champs.");
        }}


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    private void afficherMessageErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean validerNomCours(String nomCours) {
        // Vérification : nomCours doit être une chaîne non vide
        if (!nomCours.matches("[a-zA-Z]+") || nomCours.isEmpty()) {
            afficherMessageErreur("Nom du categorie invalide.");
            return false;
        } else {
            return true;
        }
    }

    private boolean validerNomModule(String nomModule) {
        // Vérification : nomModule doit être une chaîne non vide et sa longueur ne doit pas dépasser 5
        if (nomModule.length() < 5) {
            afficherMessageErreur("Nom du module invalide.");
            return false;
        } else {
            return true;
        }
    }

    private boolean validerNiveau(String niveauStr) {
        // Vérification : niveauStr doit être une chaîne non vide
        if (niveauStr.isEmpty()) {
            afficherMessageErreur("Description invalide.");
            return false;
        } else {
            return true;
        }
    }

}
