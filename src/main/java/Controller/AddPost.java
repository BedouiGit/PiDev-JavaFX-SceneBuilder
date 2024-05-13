package Controller;

import entities.Posts;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import services.ServicePosts;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddPost implements Initializable {

    @FXML
    private Button AddPost;

    @FXML
    private TextField content;

    @FXML
    private VBox postContainer;
    @FXML
    private ImageView audience;
    @FXML
    private Label banner;
    private ObservableList<Posts> observablePosts = FXCollections.observableArrayList();

    List<Posts> posts;

    @FXML
    void AddPost(MouseEvent event) throws SQLException {
        Posts post = new Posts(content.getText(), "NFTNavigator");
        ServicePosts ps = new ServicePosts();
        observablePosts.add(post); // Use add method instead of ajouter
    }


    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        loadPosts(); // Load posts when the controller is initialized
    }

    private void loadPosts() {
        Platform.runLater(() -> {
            ServicePosts ps = new ServicePosts();
            try {
                posts = new ArrayList<>(ps.afficher());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            for (Posts post : posts) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/front_office/AfficherPost.fxml"));
                try {
                    VBox vBox = fxmlLoader.load();
                    ShowPost showPost = fxmlLoader.getController();
                    showPost.setData(post);
                    observablePosts.setAll(posts);
                    postContainer.getChildren().add(vBox);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void refreshPosts() {
        postContainer.getChildren().clear(); // Clear existing posts
        loadPosts(); // Load and display posts again
    }

    private void showBanner(String message) {
        banner.setText(message);
        banner.setVisible(true);
    }

    public void AddImage(ActionEvent actionEvent) {
        Posts post = new Posts(content.getText(), "NFTNavigator");

        ServicePosts Sp = new ServicePosts();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.bmp", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String imagePathInDatabase = selectedFile.getAbsolutePath();

            Image image = new Image(selectedFile.toURI().toString());

            Sp.saveImageToDatabase(selectedFile, post);
        }
    }
}
