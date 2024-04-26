import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/AjouterArticle.fxml"));

        // Load CSS
        Scene scene = new Scene(root, 800, 400);
        scene.getStylesheets().add(getClass().getResource("/gui/style.css").toExternalForm()); // Replace "styles.css" with your CSS file path

        primaryStage.setTitle("Signup");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
