package controllers.NFTController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import models.NFT;
import services.NFTService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class NFTBack {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox root;

    @FXML
    private TableView<NFT> tableView;

    @FXML
    private TableColumn<?, ?> nameCo;

    @FXML
    private TableColumn<?, ?> priceCo;

    @FXML
    private TableColumn<NFT, String> ImageCol;

    @FXML
    private TableColumn<?, ?> statusCo;

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
    private NFT selectedNFT;

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

    }


    @FXML
    void addProduct(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NFTFxml/BackOffice/BackForm.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle depuis l'événement
            Scene scene = ((Node) event.getSource()).getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }}

    @FXML
    void removeProduct(ActionEvent event) {
        if(selectedNFT != null)
        {
            NFTService sp = new NFTService();
            sp.delete(selectedNFT);

            displayAllProductsInTableView();
        }

    }

    @FXML
    void searchProduct(ActionEvent event) {
        String filterBy = filterComboBox.getValue();
        String search = searchField.getText();

        try {
            NFTService serviceNFT = new NFTService();
            List<NFT> produits;
            if(search.isEmpty()){
                produits = serviceNFT.getAll();
            }else
                produits = serviceNFT.searchBy(filterBy,search);
            ObservableList<NFT> produitObservableList = FXCollections.observableArrayList(produits);
            tableView.setItems(produitObservableList);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des personnes : " + e.getMessage());
        }

    }

    @FXML
    void updateProduct(ActionEvent event) {
        if(selectedNFT != null)
        {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NFTFxml/BackOffice/BackForm.fxml"));
                Parent parent = loader.load();

                BackFormNFT controller = loader.getController();

                controller.setProduct(selectedNFT);

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

        nameCo.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCo.setCellValueFactory(new PropertyValueFactory<>("price"));
        statusCo.setCellValueFactory(new PropertyValueFactory<>("status"));
        ImageCol.setCellValueFactory(new PropertyValueFactory<>("image"));
        ImageCol.setCellFactory(col -> new TableCell<NFT, String>() {
            private final ImageView imageView = new ImageView();
            private final String basePath = "file:/C:/Users/Admin/Desktop/Java_badbud/nfttun/";

            @Override
            protected void updateItem(String imagePath, boolean empty) {
                super.updateItem(imagePath, empty);
                if (empty || imagePath == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    System.out.println("Loading image from: " + imagePath);
                    try {
                        Image image = new Image(basePath + imagePath, 50, 50, true, true, true);
                        if (image.isError()) {
                            System.out.println("Failed to load image from: " + imagePath);
                            setText("Image load failed");
                        } else {
                            imageView.setImage(image);
                            setGraphic(imageView);
                            setText(null);
                        }
                    } catch (Exception e) {
                        System.out.println("Error loading image: " + e.getMessage());
                        setText("Exception loading image");
                    }
                }
            }
        });

        NFTService serviceproduit = new NFTService();
        List<NFT> nfts = serviceproduit.getAll();
        ObservableList<NFT> produitObservableList = FXCollections.observableArrayList(nfts);
        tableView.setItems(produitObservableList);
    }

    private void handleTableViewClick(MouseEvent mouseEvent) {
        NFT selectedNFT = tableView.getSelectionModel().getSelectedItem();
        if (selectedNFT != null) {
            this.selectedNFT = selectedNFT;

            setTableActionButtons(false);
        }
    }


    private void setTableActionButtons(boolean state) {
        updateButton.setDisable(state);
        removeButton.setDisable(state);

    }

    @FXML
    void ViewCommande(ActionEvent event) throws IOException  {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NFTFxml/BackOffice/BackCommande.fxml"));
        Parent categoriesRoot = loader.load();

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(categoriesRoot);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void initialize() {
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'BackForm.fxml'.";
        assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file 'BackForm.fxml'.";
        assert nameCo != null : "fx:id=\"nameCo\" was not injected: check your FXML file 'BackForm.fxml'.";
        assert priceCo != null : "fx:id=\"priceCo\" was not injected: check your FXML file 'BackForm.fxml'.";
        assert statusCo != null : "fx:id=\"statusCo\" was not injected: check your FXML file 'BackForm.fxml'.";
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
