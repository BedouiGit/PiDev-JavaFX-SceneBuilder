package controllers.NFTController;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Commande;
import services.CommandeService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CommandeBack {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox root;

    @FXML
    private TableView<Commande> tableView;

    @FXML
    private TableColumn<?, ?> EmailCo;

    @FXML
    private TableColumn<?, ?> WalletCo;

    @FXML
    private TableColumn<Commande, String> TotalCol;

    @FXML
    private TableColumn<?, ?> NFTCo;

    @FXML
    private Button btn_workbench111112;

    @FXML
    private Button updateButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button printButton;

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> filterComboBox;

    @FXML
    private Button btn_workbench13;
    private Commande selectedCommande;


    @FXML
    void nftbackbtn(ActionEvent event) {

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
    void commandebackbtn(ActionEvent event) {
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
    void print(ActionEvent event) {

        Commande commande = selectedCommande;

        generatePDF(commande);
    }

    public void generatePDF(Commande commande) {
        // Création d'une boîte de dialogue pour choisir l'emplacement de sauvegarde du PDF
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"));

        // Afficher la boîte de dialogue et attendre que l'utilisateur sélectionne un emplacement
        File selectedFile = fileChooser.showSaveDialog(new Stage());

        // Vérifier si un fichier a été sélectionné
        if (selectedFile != null) {
            // Utiliser le chemin sélectionné par l'utilisateur pour enregistrer le PDF
            String dest = selectedFile.getAbsolutePath();
            try {
                // Initialiser le document PDF
                PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
                Document document = new Document(pdfDoc, PageSize.A4);

                // Ajouter un titre au document
                Paragraph title = new Paragraph("Liste des produits ");
                title.setFontSize(16).setBold();
                document.add(title);

                // Créer un tableau pour afficher les données
                Table table = new Table(5);
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("nom")));
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("prix")));
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Qauntity")));
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Statut")));






                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(commande.getEmail())));
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(String.valueOf(commande.getTotal()))));

                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(commande.getWallet())));
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(String.valueOf(commande.getDate()))));


                // Ajouter le tableau au document
                document.add(table);

                // Fermer le document
                document.close();

                // Affichage d'une alerte de succès
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("Le fichier PDF a été enregistré avec succès !");
                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Aucun emplacement sélectionné.");
        }
    }


    @FXML
    void removeCommande(ActionEvent event) {
        if(selectedCommande != null)
        {
            CommandeService sp = new CommandeService();
            sp.delete(selectedCommande);

            displayAllProductsInTableView();
        }

    }

    @FXML
    void searchProduct(ActionEvent event) {
        String filterBy = filterComboBox.getValue();
        String search = searchField.getText();

        try {
            CommandeService serviceCommande = new CommandeService();
            List<Commande> produits;
            if(search.isEmpty()){
                produits = serviceCommande.getAll();
            }else
                produits = serviceCommande.searchBy(filterBy,search);
            ObservableList<Commande> produitObservableList = FXCollections.observableArrayList(produits);
            tableView.setItems(produitObservableList);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des personnes : " + e.getMessage());
        }

    }

    @FXML
    void updateCommande(ActionEvent event) {
        if(selectedCommande != null)
        {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NFTFxml/BackOffice/BackFormCommande.fxml"));
                Parent parent = loader.load();

                BackFormCommande controller = loader.getController();

                controller.setcommande(selectedCommande);

                Scene scene = ((Node) event.getSource()).getScene();
                // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
                scene.setRoot(parent);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void displayAllProductsInTableView() {
        tableView.setOnMouseClicked(this::handleTableViewClick);

        EmailCo.setCellValueFactory(new PropertyValueFactory<>("email"));
        TotalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
        WalletCo.setCellValueFactory(new PropertyValueFactory<>("wallet"));

        CommandeService serviceproduit = new CommandeService(); // Pass the connection object
        List<Commande> commandes = serviceproduit.getAll();
        ObservableList<Commande> produitObservableList = FXCollections.observableArrayList(commandes);
        tableView.setItems(produitObservableList);
    }

    private void handleTableViewClick(MouseEvent mouseEvent) {
        Commande selectedCommande = tableView.getSelectionModel().getSelectedItem();
        if (selectedCommande != null) {
            this.selectedCommande = selectedCommande;

            setTableActionButtons(false);
        }
    }


    private void setTableActionButtons(boolean state) {
        updateButton.setDisable(state);
        removeButton.setDisable(state);

    }

    @FXML
    void initialize() {
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'BackForm.fxml'.";
        assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file 'BackForm.fxml'.";
        assert EmailCo != null : "fx:id=\"EmailCo\" was not injected: check your FXML file 'BackForm.fxml'.";
        assert WalletCo != null : "fx:id=\"WalletCo\" was not injected: check your FXML file 'BackForm.fxml'.";
        assert TotalCol != null : "fx:id=\"TotalCol\" was not injected: check your FXML file 'BackForm.fxml'.";
        assert btn_workbench111112 != null : "fx:id=\"btn_workbench111112\" was not injected: check your FXML file 'BackForm.fxml'.";
        assert updateButton != null : "fx:id=\"updateButton\" was not injected: check your FXML file 'BackForm.fxml'.";
        assert removeButton != null : "fx:id=\"removeButton\" was not injected: check your FXML file 'BackForm.fxml'.";
        assert printButton != null : "fx:id=\"printButton\" was not injected: check your FXML file 'BackForm.fxml'.";
        assert searchField != null : "fx:id=\"searchField\" was not injected: check your FXML file 'BackForm.fxml'.";
        assert filterComboBox != null : "fx:id=\"filterComboBox\" was not injected: check your FXML file 'BackForm.fxml'.";
        assert btn_workbench13 != null : "fx:id=\"btn_workbench13\" was not injected: check your FXML file 'BackForm.fxml'.";
        displayAllProductsInTableView();
        setTableActionButtons(true);

    }

}
