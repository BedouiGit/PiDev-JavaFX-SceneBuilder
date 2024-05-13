package Controller;

import entities.Posts;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.ServicePosts;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Dashboard_Back implements Initializable {

    public Button commentsView;
    public Button showStatsButton;
    @FXML
    private VBox postContainer;
    List<Posts> posts;


    @FXML
    private TextField searchBarEvents;

    @FXML
    private Button sortButton;
    private ServicePosts servicePosts;
    @FXML
    private ComboBox<String> criteriaComBox;

    @FXML
    private ComboBox<String> sortOrderComBox;

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        // Initialiser les publications lors du chargement de la vue
        //  loadPosts()
        servicePosts = new ServicePosts();
        refreshPosts();
        searchBarEvents.setOnKeyReleased(this::searchPosts);

    }


    // Méthode pour charger les publications
    private void loadPosts() {
        Platform.runLater(() -> {


            postContainer.getChildren().clear(); // Effacer les publications existantes


            ServicePosts ps = new ServicePosts();
            try {
                posts = new ArrayList<>(ps.afficher());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            for (Posts post : posts) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/back_office/item.fxml"));
                try {
                    VBox vBox = fxmlLoader.load();
                    ShowPost_back showPost = fxmlLoader.getController();
                    showPost.setData(post);
                    showPost.setDashboardController(this);
                    postContainer.getChildren().add(vBox);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Méthode pour rafraîchir les publications

    private void searchPosts(KeyEvent event) {
        try {
            String searchText = searchBarEvents.getText();
            List<Posts> searchResults = servicePosts.searchPost(searchText);
            updateEventCards(searchResults);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateEventCards(List<Posts> searchResults) {
        postContainer.getChildren().clear();
        for (Posts post : searchResults) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/back_office/item.fxml"));
                VBox vBox = fxmlLoader.load();
                ShowPost_back showPost = fxmlLoader.getController();
                showPost.setData(post);
                showPost.setDashboardController(this);
                postContainer.getChildren().add(vBox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void refreshPosts() {
        loadPosts(); // Appeler loadPosts() pour rafraîchir les publications
    }

    @FXML
    private void openAddPostInterface(ActionEvent event) {
        try {
            // Load the AddPost interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/back_office/AjouterPost.fxml"));
            Parent root = loader.load();

            // Create a new stage and set the scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            refreshPosts();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showStats(ActionEvent event) {
        try {
            // Load the stats interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/back_office/Stats.fxml"));
            Parent root = loader.load();

            // Create a new stage and set the scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showComments(ActionEvent event) {
        try {
            // Load the view that displays comments
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/back_office/ShowComments_back.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception
        }
    }
}