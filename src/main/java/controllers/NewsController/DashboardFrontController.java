package controllers.NewsController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardFrontController {
    public void OnFilsActualitésClicked(MouseEvent mouseEvent) {



        try {


            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/NewsFxml/front_office/AjouterPost.fxml"));
            Parent root = fxmlLoader.load();


            // Assuming selectedPost is the post you want to edit

            Stage stage = new Stage();
            stage.setTitle("Add Comment");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }





    }
}
