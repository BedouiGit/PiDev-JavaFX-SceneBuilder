package controllers.HomeController;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import models.user;

import java.io.IOException;

public class HomeController {
    @FXML
    private ImageView bannerImageView;


    user currentuser;

    public void initData(user usere) {
        this.currentuser = usere;
    }
    @FXML
    private void navigateToParties(ActionEvent event) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Client/Home/Classification.fxml"));
            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
