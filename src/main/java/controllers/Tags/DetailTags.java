package controllers.Tags;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import models.Tags;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import services.ServiceLikeArticle;

import java.io.IOException;
import java.io.InputStream;

public class DetailTags {

    @FXML
    private Button btnAddComment;

    @FXML
    private Label descriptionPubDetails;
    @FXML
    private ImageView imagePubDetails;
    @FXML
    private TextField tfAddComment;
    @FXML
    private Label titrePubDetails;

    @FXML
    private AnchorPane detailsPubPane;
    private Tags currentPublication;


    private ServiceLikeArticle serviceLike = new ServiceLikeArticle();


    public void setPublication(Tags pub) {
        currentPublication = pub;
        titrePubDetails.setText(pub.getNom());
        descriptionPubDetails.setText(pub.getDescription());
        Image image = new Image(getClass().getResourceAsStream("/images/" + pub.getImageT()));
        imagePubDetails.setImage(image);

        updateView();
    }






    @FXML
    private void handleDownloadPDF(ActionEvent event) {
        String path = System.getProperty("user.home") + "\\Desktop\\output.pdf";

        try {
            PdfWriter writer = new PdfWriter(path);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Adding a logo to the PDF
            InputStream logoStream = getClass().getResourceAsStream("/images/logo ff.png"); // Adjust the path to where your logo is stored
            if (logoStream != null) {
                byte[] logoData = logoStream.readAllBytes();
                ImageData logo = ImageDataFactory.create(logoData);
                com.itextpdf.layout.element.Image pdfImage = new com.itextpdf.layout.element.Image(logo);

                pdfImage.setWidth(50); // Set the width as per your requirement
                pdfImage.setHeight(50); // Set the height as per your requirement
                document.add(pdfImage);
            }

            document.add(new Paragraph("Title: " + titrePubDetails.getText()));


            InputStream imageStream = getClass().getResourceAsStream("/images/" + currentPublication.getImageT());
            if (imageStream != null) {
                byte[] imageData = imageStream.readAllBytes();
                ImageData data = ImageDataFactory.create(imageData);
                // Use fully qualified name for iText Image
                com.itextpdf.layout.element.Image pdfImage = new com.itextpdf.layout.element.Image(data);
                document.add(pdfImage); // Add the image to the document
            }
            document.add(new Paragraph("Description: " + descriptionPubDetails.getText()));

            document.close();
            showAlert("Success", "PDF created successfully at " + path);
        } catch (Exception e) {
            showAlert("Error", "Failed to create PDF. " + e.getMessage());
        }

    }


    private void updateView() {
        // Update the text fields with the publication data
        titrePubDetails.setText(currentPublication.getNom());
        descriptionPubDetails.setText(currentPublication.getDescription());
        // Load the image for the publication
        try {
            Image image = new Image(getClass().getResourceAsStream("/images/" + currentPublication.getImageT()));
            imagePubDetails.setImage(image);
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
            // Set a default image or handle the error
            imagePubDetails.setImage(new Image(getClass().getResourceAsStream("/images/default.png")));
        }

        // Display comments related to the publication


        // Update like and dislike counts on the UI

    }






    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    public void ReturnShowPublications() {
        try {
            Node displayPubs = FXMLLoader.load(getClass().getResource("/Front/Tags/affichagePub.fxml"));
            detailsPubPane.getChildren().setAll(displayPubs);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, for example, by showing an error message
        }
    }


}
