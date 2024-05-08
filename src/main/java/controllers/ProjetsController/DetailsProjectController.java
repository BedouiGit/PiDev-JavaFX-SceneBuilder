package controllers.ProjetsController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.projectComments;
import models.projet_reactions;
import models.projets;
import models.user;
import services.ProjetsServices;
import services.projectReactionService;
import services.ProjectCommentService;
import utils.MyDB;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DetailsProjectController {

    @FXML
    private VBox CommentsContainer;

    @FXML
    private Text description;

    @FXML
    private Text title;

    @FXML
    private Button addComment;

    @FXML
    private TextField commentField;

    @FXML
    private VBox commentDisplay;

    @FXML
    private ImageView imgPost;

    @FXML
    private ImageView imgReaction;

    @FXML
    private Label nbReactions;

    @FXML
    private HBox reactionsContainer;

    private long startTime = 0;

    private user user;
    private projets projet;

    private final projectReactionService projectReactionService;
    private final ProjectCommentService projectCommentService;

    public DetailsProjectController() {
        projectReactionService = new projectReactionService();
        projectCommentService = new ProjectCommentService();
    }

    public void setUser(user user) {
        this.user = user;
    }

    public void setProjet(projets projet) {
        this.projet = projet;
    }

    @FXML
    void displayDetailsProject(int projectId, int categoryId) {
        projets project = new ProjetsServices().getProjectById(projectId);
        if (project != null) {
            title.setText(project.getNom());
            description.setText(project.getDescription());
            // Load image using FileInputStream
            loadImage(project.getPhotoUrl());
        } else {
            // Handle case when project is not found
            showAlert(Alert.AlertType.ERROR, "Project Not Found", "The project with ID " + projectId + " was not found.");
        }
    }

    private void loadImage(String imagePath) {
        try (FileInputStream fileInputStream = new FileInputStream(imagePath)) {
            // Load the image using FileInputStream
            Image image = new Image(fileInputStream);
            // Set the image to the ImageView
            imgPost.setImage(image);
        } catch (IOException e) {
            // Handle image loading error
            showAlert(Alert.AlertType.ERROR, "Image Loading Error", "Failed to load project image: " + e.getMessage());
        }
    }

    @FXML
    void onLikeContainerMouseReleased(MouseEvent event) {
        startTime = System.currentTimeMillis();
    }

    @FXML
    void onLikeContainerPressed(MouseEvent event) {
        if (System.currentTimeMillis() - startTime > 500) {
            reactionsContainer.setVisible(true);
        } else {
            reactionsContainer.setVisible(!reactionsContainer.isVisible());
        }
    }

    @FXML
    void onReactionImgPressed(MouseEvent event) {
        String reactionType = "Like"; // Assuming it's always "Like" for now
        int eventId = 68; // Get the eventId from the current projet
        if (projectReactionService.hasUserReacted(1, eventId, reactionType)) {
            // If user has already reacted, remove reaction
            projectReactionService.removeReaction(1, eventId, reactionType);
        } else {
            // If user hasn't reacted, add reaction
            projectReactionService.addReaction(new projet_reactions(user, eventId, reactionType));
        }

        // Update UI with current reactions
        updateReactionsUI();
    }


    private void updateReactionsUI() {
        // Get the eventId from the current projet
        int eventId = 68;

        // Count the number of reactions for the current project
        long numberOfReactions = projectReactionService.countReactions(eventId);
        nbReactions.setText(String.valueOf(numberOfReactions));

        // Update like icon based on user's reaction
        if (projectReactionService.hasUserReacted(user.getId(), eventId, "Like")) {
            imgReaction.setImage(new Image("/img/ic_like.png"));
        } else {
            imgReaction.setImage(new Image("/img/ic_like_outline.png"));
        }
    }

    Boolean commentVisible = false;





    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    public long countReactions(int eventId) {
        String sql = "SELECT COUNT(*) AS reaction_count FROM projets_reactions WHERE projet_id = ?";
        long reactionCount = 0;

        try {
            Connection connection = MyDB.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, eventId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                reactionCount = rs.getLong("reaction_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reactionCount;
    }
}
