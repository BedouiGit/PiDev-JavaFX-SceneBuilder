package controllers.HomeController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import utils.NavigationUtil;

import java.io.IOException;
import java.util.Objects;

public class NavbarController {
    @FXML
    private void navigateToCategories(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Client/Categories/CreateCategory.fxml")));
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
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/CategoriesFxml/Client/Categories/DisplayCategories.fxml"));
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void navigateToClient(ActionEvent event) {
        try {
            NavigationUtil.navigateTo("/fxml/CategoriesFxml/Client/Categories/DisplayCategories.fxml", ((Node) event.getSource()).getScene().getRoot());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void navigateTnwes(ActionEvent event) {
        try {
            NavigationUtil.navigateTo("/front_office/ajouterPost.fxml", ((Node) event.getSource()).getScene().getRoot());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void AffichagePub(ActionEvent event) {
        try {
            NavigationUtil.navigateTo("/Back/Publication/affichagePub.fxml", ((Node) event.getSource()).getScene().getRoot());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateToProfile(ActionEvent event) {
        try {
            NavigationUtil.navigateTo("/profile.fxml", ((Node) event.getSource()).getScene().getRoot());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigatetonft(ActionEvent event) {
        try {
            NavigationUtil.navigateTo("/fxml/NFTFxml/FrontOffice/List_NFTS.fxml", ((Node) event.getSource()).getScene().getRoot());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}