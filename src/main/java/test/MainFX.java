package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.io.IOException;

public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Client/Home/Homepage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);
            primaryStage.setTitle("NFTUN");

            // Load and set the application icon
            Image icon = new Image("/imges/logo/nftlogo.png");
            primaryStage.getIcons().add(icon);

            // Set the stage to full screen

            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
