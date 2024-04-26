package controllers.ProjetsController;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import models.projets;
import services.CategoryService;
import services.ProjetsServices;
import models.category;

public class AjouterProjetController implements Initializable {

    @FXML
    private TextField titre;

    @FXML
    private TextField titre1;

    @FXML
    private TextField titre2;

    @FXML
    private TextField titre3;

    @FXML
    private ComboBox <category>listCours;

    @FXML
    private TextField titre4;

    private final ProjetsServices es = new ProjetsServices();
    private final CategoryService cs = new CategoryService();

    private category selectedCategory; // Store the selected category

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listCours.getItems().addAll(cs.afficher());
    }

    public void setCour(category category) {
        this.selectedCategory = category;
        listCours.setValue(category);
        listCours.setDisable(true);
    }

    @FXML
    public void Ajouter(ActionEvent actionEvent) {
        int id = Integer.parseInt(titre4.getText());

        String evaluationTitre = titre.getText().trim();
        String description = titre1.getText().trim();
        String walletAddress = titre2.getText().trim();

        if (id==0 ||evaluationTitre.isEmpty() || description.isEmpty() || walletAddress.isEmpty()) {
            showAlert("Empty Fields", "Please fill in all the fields.");
        } else {
            // Set the current date
            Date dateDeCreation = new Date();

            // Get the selected category ID from the ComboBox
            int categoryId = selectedCategory.getId();

            // Create the projets object with the retrieved values
            projets project = new projets(id, evaluationTitre, description, walletAddress, dateDeCreation, "", categoryId);

            // Insert the project
            es.insererProjet(project);

            // Navigate to the desired page
            navigeuzVersAfficher();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void navigeuzVersAfficher() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Afficherevaluation.fxml"));
            titre.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    public void navigeuzVersAffichercours() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Affichercour.fxml"));
            titre.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}