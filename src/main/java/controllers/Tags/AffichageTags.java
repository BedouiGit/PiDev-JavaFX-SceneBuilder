package controllers.Tags;

import models.Tags;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import services.ServiceTagsArticle;

import java.util.List;
import java.util.stream.Collectors;

public class AffichageTags {
    @FXML
    private FlowPane publicationsContainer;

    @FXML
    private TextField searchField;
    private final ServiceTagsArticle serviceTags = new ServiceTagsArticle();

    @FXML
    private void initialize() {
        loadPublications(null); // Load all publications initially

        // Add a listener to the search field
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            loadPublications(newValue); // Load publications with the search term
        });

    }

    @FXML
    private void handleSearch() {
        String searchTerm = searchField.getText();
        // Perform search and update the view...
    }
    private void loadPublications(String searchTerm) {
        try {
            List<Tags> publicati = serviceTags.afficher();
            if (searchTerm != null && !searchTerm.isEmpty()) {
                publicati= publicati.stream()
                        .filter(pub -> pub.getNom().toLowerCase().contains(searchTerm.toLowerCase()))
                        .collect(Collectors.toList());
            }

            publicationsContainer.getChildren().clear();
            for (Tags pub : publicati) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Tags/cardPub.fxml"));
                Node card = loader.load(); // This line can throw IOException
                CardTags controller = loader.getController();
                controller.setPublication(pub);
                controller.setAffichageTagsController(this); // Pass reference to this controller
                publicationsContainer.getChildren().add(card);
            }
        } catch (Exception e) { // Catch any exception here
            e.printStackTrace();
        }
    }


    public void refreshPublicationsView() {
        Platform.runLater(() -> {
            loadPublications(null); // Reload all publications
        });
    }





}
