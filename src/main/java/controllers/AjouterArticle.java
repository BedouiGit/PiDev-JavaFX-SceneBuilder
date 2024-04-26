package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import models.Article;
import services.ArticleService;

import java.sql.SQLException;
import java.util.Date;

public class AjouterArticle {

    @FXML
    private TextField tfAuteur;

    @FXML
    private TextArea taContenu;

    @FXML
    private TextField imageUrlFX;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField tfTitre;

    @FXML
    void ajouterArticle(ActionEvent event) {
        String titre = tfTitre.getText().trim();
        String contenu = taContenu.getText().trim();
        String auteur = tfAuteur.getText().trim();
        // Validate length constraints
        if (titre.isEmpty() || contenu.isEmpty() || auteur.isEmpty()) {
            // If any of the fields are empty, show an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Veuillez remplir tous les champs!");
            alert.show();
            return; // Exit the method without proceeding
        }


        if (titre.length() > 50 || contenu.length() > 1000 || auteur.length() > 50) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Veuillez saisir des données conformes aux limites de taille: \n" +
                    "Titre (max 50 caractères), Contenu (max 1000 caractères), Auteur (max 50 caractères)");
            alert.show();
            return; // Exit the method without proceeding
        }

        try {
            Article article = new Article(tfTitre.getText(), taContenu.getText(), tfAuteur.getText(), new Date());
            ArticleService articleService = new ArticleService();
            articleService.insertOne(article);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
            alert.show();
        }
    }
}