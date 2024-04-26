package controllers.CategoryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
public class NavbarController {
    @FXML
    private void navigateToCategories(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Projets/AjouterCategory.fxml"));
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateToProjers(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Projets/AjouterProjet.fxml"));
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}