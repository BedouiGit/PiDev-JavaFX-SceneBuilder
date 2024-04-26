package controllers.CategoryController;


import controllers.ProjetsController.DisplayProjectsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import services.CategoryService;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import models.category;
import models.projets;

public class AffichageCategoriesController {

    private ObservableList<category> observableList;
    private final CategoryService cs = new CategoryService();
    @FXML
    private ListView<category> listviewcours;
    @FXML
    private Button buttonReturn;
    private category SelectedCategory;

    @FXML
    private Button deletebuttton;

    @FXML
    private Button AfficherDetails;
    @FXML
    private Button editButton;
    @FXML
    private Button addEvaluationButton;

    @FXML
    private ListView<projets> projectListView;

    @FXML
    void initialize() {
        List<category> cours = cs.afficher();
        observableList = FXCollections.observableList(cours);
        listviewcours.setItems(observableList);

        listviewcours.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                SelectedCategory = newValue;
                addEvaluationButton.setDisable(false);
                deletebuttton.setDisable(false);
                editButton.setDisable(false);
                AfficherDetails.setDisable(false);

            }
        });
    }


    @FXML
    public void naviguezVersDetailsProjets(ActionEvent actionEvent) {
        if (SelectedCategory != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Projets/Projets.fxml"));
                Parent root = loader.load();

                DisplayProjectsController projetsController = loader.getController();

                projetsController.displayProjectsByCategory(SelectedCategory.getId());

                listviewcours.getScene().setRoot(root);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }






    @FXML
    public void naviguezVersAjoutercategory(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Projets/AjouterCategory.fxml"));
            listviewcours.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void naviguezVersmodifiercategory (ActionEvent event){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Projets/modifierCategory.fxml"));

            Parent root = loader.load();

            ModifierCategoryController controller = loader.getController();

            SelectedCategory=listviewcours.getSelectionModel().getSelectedItem();
            if (SelectedCategory != null) {
                controller.setCours(SelectedCategory);
            } else {
                System.out.println("No item selected");
                return;
            }

            listviewcours.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void deletecategory(ActionEvent event) throws SQLException {
        if ( listviewcours.getSelectionModel().getSelectedItem() != null) {
            category newValue =  listviewcours.getSelectionModel().getSelectedItem();
            cs.supprimer(newValue);
            initialize();
        }
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    void naviguezVersAcceuil(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Homepage.fxml"));
            buttonReturn.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


}
