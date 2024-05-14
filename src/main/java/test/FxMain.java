package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class FxMain extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

        /*String gClientId = "###############";
        String gRedir = "##########";
        String gScope = "https://www.googleapis.com/auth/userinfo.profile";
        String gSecret = "8QXZg1ug5HbY0IFH6AdwzV4u";
        OAuthAuthenticator auth = new OAuthGoogleAuthenticator(gClientId, gRedir, gSecret, gScope);
        //auth.startLogin();*/

        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/Login.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        stage.setTitle("NFTun ");
        stage.setScene(scene);

        Image icon = new Image("/images/nftun.png");
        stage.getIcons().add(icon);

        stage.show();

    }
}
