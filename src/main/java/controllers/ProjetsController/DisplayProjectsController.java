package controllers.ProjetsController;

import controllers.CategoryController.ModifierCategoryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import models.category;
import models.projets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import services.ProjetsServices;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DisplayProjectsController {

    @FXML
    private Button buttonReturn;

    private projets selectedCour;

    @FXML
    private Button addEvaluationButton;

    @FXML
    private Button deletebuttton;

    @FXML
    private Button editButton;

    @FXML
    private Button AfficherDetails;


    @FXML
    private ListView<projets> projectListView;

    private final ProjetsServices projectService = new ProjetsServices(); // Assuming you have a ProjectService object

    @FXML
    public void displayProjectsByCategory(int categoryId) {
        List<projets> projects = projectService.getProjectsByCategory(categoryId);
        ObservableList<projets> observableProjects = FXCollections.observableList(projects);
        projectListView.setItems(observableProjects);

        projectListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedCour = newValue;
                addEvaluationButton.setDisable(false);
                deletebuttton.setDisable(false);
                editButton.setDisable(false);
                AfficherDetails.setDisable(false);

            }
        });
    }

    @FXML
    public void naviguezVersAjouterProjets(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Projets/AjouterCategory.fxml"));
            projectListView.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void naviguezVersmodifiercategory (ActionEvent event){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Projets/modifierCategory.fxml"));

            Parent root = loader.load();

            ModifierProjectController controller = loader.getController();

            selectedCour=projectListView.getSelectionModel().getSelectedItem();
            if (selectedCour != null) {
                controller.setCours(selectedCour);
            } else {
                System.out.println("No item selected");
                return;
            }

            projectListView.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void deletecategory(ActionEvent event) throws SQLException {
        projets selectedProject = projectListView.getSelectionModel().getSelectedItem();
        if (selectedProject != null) {
            int categoryId =selectedProject.getId();
            projectService.supprimerProjet(selectedProject.getId());
            displayProjectsByCategory(categoryId); // Pass the obtained category ID
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
