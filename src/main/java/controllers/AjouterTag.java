package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import models.Tag;
import services.TagService;

import java.sql.SQLException;

public class AjouterTag {

    @FXML
    private TextField tfNom;

    @FXML
    private TextField tfDescription;

    @FXML
    void ajouterTag(ActionEvent event) {
        String nom = tfNom.getText().trim();
        String description = tfDescription.getText().trim();

        // Validate length constraints
        if (nom.isEmpty() || description.isEmpty()) {
            // If any of the fields are empty, show an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Veuillez remplir tous les champs!");
            alert.show();
            return; // Exit the method without proceeding
        }

        if (nom.length() > 50 || description.length() > 1000) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Veuillez saisir des données conformes aux limites de taille: \n" +
                    "Nom (max 50 caractères), Description (max 1000 caractères)");
            alert.show();
            return; // Exit the method without proceeding
        }

        try {
            Tag tag = new Tag(nom, description);
            TagService tagService = new TagService();
            tagService.insertOne(tag);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
            alert.show();
        }
    }
}
