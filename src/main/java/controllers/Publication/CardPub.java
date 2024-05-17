package controllers.Publication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import models.Commentaire;
import models.publication;
import services.ServiceCommentaire;
import services.ServicePublication;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

public class CardPub {
    @FXML
    private Button btnDelete;
    @FXML
    private AnchorPane cardPane;
    @FXML
    private ImageView imagePub;
    @FXML
    private Label titrePub;
    @FXML
    private Label descriptionPub;
    @FXML
    private Label datePub;
    @FXML
    private Label idUserPub;
    @FXML
    private ImageView warningIcon;
    private publication currentPublication;
    private AffichagePub affichagePubController;
    private ServiceCommentaire serviceCommentaire = new ServiceCommentaire();


    public void setAffichagePubController(AffichagePub controller) {
        this.affichagePubController = controller;
    }
    @FXML
    public void initialize() {
        warningIcon.setVisible(true); // Temporarily force visibility
    }


    public void setPublication(publication publication) {

        this.currentPublication = publication;
        titrePub.setText(publication.getTitreP());
        descriptionPub.setText(publication.getDescriptionP());
        idUserPub.setText(String.valueOf(publication.getIdUserId()));
        datePub.setText(publication.getDateP().toString());

        // Ajuster la visibilité des boutons en fonction de l'ID de l'utilisateur
        boolean isUserOne = publication.getIdUserId() != 1;
        btnDelete.setVisible(true);

        String imagePath = "/images/" + publication.getImageP();
        InputStream imageStream = getClass().getResourceAsStream(imagePath);
        if (imageStream != null) {
            Image image = new Image(imageStream);
            imagePub.setImage(image);
        } else {
            // Set a default image or leave it blank
        }
        updateWarningIconVisibility();


    }
    @FXML
    private void handleEditAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Publicationfxml/editPub.fxml"));
            Parent root = loader.load();
            editPub controller = loader.getController();
            controller.setPublication(this.currentPublication);

            // Get the current scene and set its root to the root of the new FXML file
            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    @FXML
    private void handleDeleteAction(ActionEvent event) {
        try {
            ServicePublication servicePublication = new ServicePublication();
            servicePublication.supprimer(currentPublication); // Delete the publication
            System.out.println("Publication deleted successfully");

            // Refresh the publications view
            if (affichagePubController != null) {
                affichagePubController.refreshPublicationsView();
                System.out.println("Refresh method called");
            } else {
                System.out.println("affichagePubController is null");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception, for example, by showing an error message
        }
    }

    @FXML
    private void handleCardClick(MouseEvent event) {
        if (event.getClickCount() == 2) { // Check if it's a double-click
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Publicationfxml/DetailPublication.fxml"));
                Parent detailView = loader.load();
                DetailPublication controller = loader.getController();
                controller.setPublication(this.currentPublication);

                // Get the current scene and set its root to the root of the new FXML file
                Scene scene = ((Node) event.getSource()).getScene();
                scene.setRoot(detailView);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void updateWarningIconVisibility() {
        try {
            boolean hasProfanity = checkForProfanity(currentPublication);
            System.out.println("Updating warning icon visibility. Profanity found: " + hasProfanity);
            warningIcon.setVisible(hasProfanity);}
        catch (SQLException e) {
            e.printStackTrace();
            warningIcon.setVisible(false);
        }

    }


    private boolean checkForProfanity(publication pub) throws SQLException {
        List<Commentaire> comments = serviceCommentaire.getCommentairesByPublication(pub.getId());
        for (Commentaire comment : comments) {
            System.out.println("Comment Content: " + comment.getContenu_c());  // Debugging line to print comment content
            if (containsProfanity(comment.getContenu_c())) {
                return true;
            }
        }
        return false;
    }


    private boolean containsProfanity(String content) {
        return content.contains("*");
    }






}
