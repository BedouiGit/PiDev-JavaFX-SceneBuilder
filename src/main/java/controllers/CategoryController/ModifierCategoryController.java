package controllers.CategoryController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.category;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import models.user;
import services.CategoryService;
import services.userService;

import java.io.IOException;
import java.sql.SQLException;

public class ModifierCategoryController {

    @FXML
    private TextField coursurl;

    @FXML
    private TextField niveau;

    @FXML
    private TextField nomcour;

    @FXML
    private TextField nommodule;

    private category category;
    private final CategoryService cs = new CategoryService();


    @FXML
    private TextField address;

    @FXML
    private TextField age;

    @FXML
    private TextField fname;



    @FXML
    private TextField lname;

    @FXML
    private TextField tel;



    @FXML
    private TextField userEmailLabel;

    private  CategoryService categoryService;

    public void EditCategory() {
         categoryService = new CategoryService ();
    }

    public void initData(category category) {
        this.category = category;
        userEmailLabel.setText(category.getNom());
        fname.setText(category.getDescription());
        lname.setText(category.getPhotoUrl());



    }

    @FXML
    private void saveChanges() throws SQLException {
        if (category != null) {
            // Update user object with new values from text fields
            category.setNom(fname.getText());
            category.setDescription(lname.getText());
            category.setPhotoUrl(address.getText());


            // Call the modifierUser method to update the user in the database
            categoryService.modifier(category);
        }

    }








    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    private void afficherMessageErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
