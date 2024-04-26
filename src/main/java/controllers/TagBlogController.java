package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import models.Tag;
import services.TagService;
import javafx.scene.control.Hyperlink;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class TagBlogController {

    public GridPane tagsGridPane;

    private TagService tagService;

    public void initialize() {
        tagService = new TagService();
        displayTags();
    }

    private void displayTags() {
        List<Tag> tags;
        try {
            tags = tagService.selectAll(); // Récupérer tous les tags depuis le service
            int row = 0; // Indice de la ligne dans le GridPane

            for (Tag tag : tags) {
                Hyperlink title = new Hyperlink(tag.getNom()); // Titre du tag (hyperlien)
                title.setStyle("-fx-font-weight: bold;"); // Mettre en gras
                tagsGridPane.add(title, 0, row); // Ajouter le titre à la grille

                Label description = new Label(tag.getDescription()); // Description du tag
                tagsGridPane.add(description, 0, row + 1); // Ajouter la description à la grille

                // Lier le tag à une action (par exemple, ouvrir une nouvelle vue ou effectuer une action)
                title.setOnAction(event -> handleTagClick(tag.getId()));

                row += 2; // Aller à la prochaine ligne
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception selon vos besoins
        }
    }

    private void handleTagClick(int tagId) {
        // Implémentez ici ce que vous voulez faire lorsque le tag est cliqué
        System.out.println("Tag clicked: " + tagId);
    }
}
