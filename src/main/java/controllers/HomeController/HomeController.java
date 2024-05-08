package controllers.HomeController;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import utils.NavigationUtil;

import java.io.IOException;

public class HomeController {
    @FXML
    private ImageView bannerImageView;


    @FXML
    private void navigateToParties(ActionEvent event) {
        try {
            NavigationUtil.navigateTo("/fxml/Client/Home/Classification.fxml", ((Node) event.getSource()).getScene().getRoot());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateToClient(ActionEvent event) {
        try {
            NavigationUtil.navigateTo("/fxml/Client/Categories/DisplayCategories.fxml", ((Node) event.getSource()).getScene().getRoot());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateToAdmin(ActionEvent event) {
        try {
            NavigationUtil.navigateTo("/fxml/Client/Categories/LIstOfCategories.fxml", ((Node) event.getSource()).getScene().getRoot());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
