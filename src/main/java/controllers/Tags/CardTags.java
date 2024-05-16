package controllers.Tags;


import models.Tags;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import services.ServiceTags;
import test.MainFX;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

public class CardTags {
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
    private ImageView warningIcon;
    private Tags currentPublication;
    private AffichageTags affichageTagsController;



    public void setAffichageTagsController(AffichageTags controller) {
        this.affichageTagsController = controller;
    }
    @FXML
    public void initialize() {
        warningIcon.setVisible(true); // Temporarily force visibility
    }


    public void setPublication(Tags publication) {

        this.currentPublication = publication;
        titrePub.setText(publication.getNom());
        descriptionPub.setText(publication.getDescription());


        String imagePath = "/images/" + publication.getImageT();
        InputStream imageStream = getClass().getResourceAsStream(imagePath);
        if (imageStream != null) {
            Image image = new Image(imageStream);
            imagePub.setImage(image);
        } else {
            // Set a default image or leave it blank
        }



    }
    @FXML
    private void handleEditAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Tags/editPub.fxml"));
            Parent root = loader.load();
            editTags controller = loader.getController();
            controller.setTags(this.currentPublication);
            MainFX.setCenterView(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    @FXML
    private void handleDeleteAction(ActionEvent event) {
        try {
            ServiceTags serviceTags = new ServiceTags();
            serviceTags.supprimer(currentPublication); // Delete the publication
            System.out.println("Tag deleted successfully");

            // Refresh the publications view
            if (affichageTagsController != null) {
                affichageTagsController.refreshPublicationsView();
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
        if (event.getClickCount() == 2) {
            try {
                FXMLLoader loader;

                    // Load the front end detail view otherwise
                    loader = new FXMLLoader(getClass().getResource("/Front/Tags/DetailPublication.fxml"));

                Parent detailView = loader.load();

                DetailTags controller = loader.getController();
                controller.setPublication(this.currentPublication);

                // Assuming you have a static method in MainFX to change the center view
                MainFX.setCenterView(detailView);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }








}
