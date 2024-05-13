package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
//import javafx.stage.StageStyle;

public class FxMain extends Application {

    private static BorderPane mainLayout;
    //private double x, y;


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/login.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        stage.setTitle("NFTun ");
        stage.setScene(scene);

        /*stage.initStyle(StageStyle.UNDECORATED);

        parent.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        parent.setOnMouseDragged(event -> {

            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);

        });*/


        loader = new FXMLLoader(getClass().getResource("/Back/Dashboard.fxml"));
        mainLayout = loader.load();

        // Load and set the application icon
        Image icon = new Image("/imges/logo/nftlogo.png");
        stage.getIcons().add(icon);



        stage.show();

    }
    public static void setCenterView(Parent node) {
        mainLayout.setCenter(node);
    }
}
