package utils;

import javafx.stage.FileChooser;
import javafx.stage.Window;
import java.io.File;

public class FileUtils {
    public static File selectImageFile(Window window) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        return fileChooser.showOpenDialog(window);
    }
}
