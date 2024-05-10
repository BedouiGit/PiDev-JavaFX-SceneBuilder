package controllers.news;

import API.MailerAPI;
import models.Posts;
import models.Reactions;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import services.ServicePosts;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class ShowPost {


    @FXML
    private Label content;
    @FXML
    private Label username;
    @FXML
    private Label idPost;
    @FXML
    private ImageView imgPost;
    private Posts post;

    @FXML
    private Label reactionName;
    @FXML
    private HBox commentContainer;

    @FXML
    private Label nbReactions;

    private Voice voice;

    @FXML
    private ImageView imgReaction;

    @FXML
    private HBox reactionsContainer;

    @FXML
    private ImageView imgLike;

    private long startTime = 0;
    private Reactions currentReaction=Reactions.NON;
    private AddPost AddPostController; // Référence au contrôleur Dashboard_Back

    // Méthode pour définir la référence au contrôleur Dashboard_Back
    public void setAddPostController(AddPost AddPostController) {
        this.AddPostController = AddPostController;
    }

    public void setContent(String cont) {
        this.content.setText(cont);
    }

    public void setUsername(String user) {
        this.username.setText(user);
    }

    private Posts getPost(){



        Posts post = new Posts();


        post.setId(Integer.parseInt(idPost.getText()));
        post.setTitle(username.getText());
        post.setContent(content.getText());
        post.setNbLikes(Integer.parseInt(nbReactions.getText()));
        post.setNbLikes(Integer.parseInt(nbReactions.getText()));



        return post;
    }



    @FXML
    public void initialize() {
        // Set the FreeTTS voice properties
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

        // Get the default voice
        VoiceManager voiceManager = VoiceManager.getInstance();
        voice = voiceManager.getVoice("kevin");
        if (voice != null) {
            voice.allocate();
        } else {
            System.err.println("Cannot find voice: kevin");
        }

        // Add event handler for double-click on content label
        content.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) { // Check for double-click
                // Get the post details
                String postTitle = username.getText();
                String postContent = content.getText();
                String likeCount = nbReactions.getText();

                // Construct the phrase to be spoken
                String phrase = String.format("Post by %s. Content: %s. Number of likes: %s.", postTitle, postContent, likeCount);

                // Speak the phrase
                if (voice != null) {
                    voice.speak(phrase);
                } else {
                    System.err.println("Cannot find voice: kevin");
                }
            }
        });
    }

    @FXML
    void PostWithVoice(MouseEvent event) {
        // Get the post content
        String postContent = content.getText();

        // Speak the post content
        if (voice != null) {
            voice.speak(postContent);
        } else {
            System.err.println("Cannot find voice: kevin");
        }
    }

    @FXML
    void onEmailImageClicked(MouseEvent event) {
        String recipientEmail = "sara.hammouda@esprit.tn";
        String subject =  post.getTitle(); // Use post title as the email subject
        String content = post.getContent(); // Use post content as the email content

        // Replace with your Mailtrap credentials
        String username = "24e2e97c986da6";
        String password = "516b848d7e79db";

        MailerAPI.sendMail(username, password, recipientEmail, subject, content);
    }



    public void setData(Posts post){

        this.post= post;
        idPost.setText(String.valueOf(post.getId()));
        username.setText(post.getTitle());
        if(post.getContent() != null && !post.getContent().isEmpty()){
            content.setText(post.getContent());
        }else{
            content.setManaged(false);
        }
        nbReactions.setText(String.valueOf(post.getNbLikes()));
        String imagePath = "/img/" + post.getImage();
        InputStream imageStream = getClass().getResourceAsStream(imagePath);
        if (imageStream != null) {
            Image image = new Image(imageStream);
            imgPost.setImage(image);
        } else {
            // Image par défaut si l'image spécifiée n'est pas trouvée
            System.out.println("L'image n'a pas pu être chargée : " + imagePath);
        }
    }




    private void likePost(int postId) {
        ServicePosts servicePosts = new ServicePosts();

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Simulate delay
                Thread.sleep(1000);

                servicePosts.incrementLikes(postId);

                // Fetch updated like count from database
                int newLikeCount = servicePosts.getLikeCount(postId);

                // Update UI on JavaFX Application Thread
                Platform.runLater(() -> updateUI(newLikeCount));

                return null;
            }
        };

        new Thread(task).start();
    }

    private void unlikePost(int postId) {
        ServicePosts servicePosts = new ServicePosts();

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Simulate delay
                Thread.sleep(1000);

                servicePosts.DecrementLikes(postId);

                // Fetch updated like count from database
                int newLikeCount = servicePosts.getLikeCount(postId);

                // Update UI on JavaFX Application Thread
                Platform.runLater(() -> updateUI(newLikeCount));

                return null;
            }
        };

        new Thread(task).start();
    }

    private void updateUI(int likeCount) {
        // Update UI on JavaFX Application Thread
        nbReactions.setText(String.valueOf(likeCount));
    }

    public void onLikeContainerPressed(MouseEvent mouseEvent) {
        if (currentReaction == Reactions.NON) {
            setReaction(Reactions.LIKE);
            likePost(Integer.parseInt(idPost.getText()));
        } else {
            setReaction(Reactions.NON);
            unlikePost(Integer.parseInt(idPost.getText()));
        }
    }

    public void setReaction(Reactions reaction) {
        currentReaction = reaction; // Update the currentReaction field

        Image image = new Image(getClass().getResourceAsStream(reaction.getImgSrc()));
        imgReaction.setImage(image);

        reactionName.setText(reaction.getName());
        reactionName.setTextFill(Color.web(reaction.getColor()));
    }

    @FXML
    void OnCommentContainerClicked(MouseEvent event) {

        try {


            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/front_office/AddComments.fxml"));
            Parent root = fxmlLoader.load();

            // Get the controller instance
            AddComment addComment = fxmlLoader.getController();

            // Set the post data
            addComment.setPost(getPost());
            // Assuming selectedPost is the post you want to edit

            Stage stage = new Stage();
            stage.setTitle("Add Comment");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


    @FXML
    void editPost(MouseEvent event) {
        try {



            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/front_office/editPost.fxml"));
            Parent root = fxmlLoader.load();

            // Get the controller instance
            editPost editPost = fxmlLoader.getController();

            // Set the post data
            editPost.setPost(getPost());
            // Assuming selectedPost is the post you want to edit

            Stage stage = new Stage();
            stage.setTitle("Edit Post");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onReactionImgPressed(MouseEvent mouseEvent) {
    }

    public void onLikeContainerMouseReleased(MouseEvent mouseEvent) {
    }
}
