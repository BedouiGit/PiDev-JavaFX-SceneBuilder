package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class NavigationUtil {

    public static void navigateTo(String resourcePath, Node currentNode) throws IOException {
            Parent NewPage = FXMLLoader.load(Objects.requireNonNull(NavigationUtil.class.getResource(resourcePath)));

            Stage stage = (Stage) currentNode.getScene().getWindow();
            stage.getScene().setRoot(NewPage);
    }

}
