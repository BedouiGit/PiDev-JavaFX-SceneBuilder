package Controller;

import entities.Posts;
import entities.Reactions;
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

public class ShowPost {


    @FXML
    private Label content;
    @FXML
    private Label username;
    @FXML
    private Label idPost;


    private Posts post;

    @FXML
    private Label reactionName;
    @FXML
    private HBox commentContainer;

    @FXML
    private Label nbReactions;


    @FXML
    private ImageView imgReaction;

    @FXML
    private HBox reactionsContainer;

    @FXML
    private ImageView imgLike;

    private long startTime = 0;
    private Reactions currentReaction=Reactions.NON;

    // Méthode pour définir la référence au contrôleur Dashboard_Back


    public void setContent(String cont) {
        this.content.setText(cont);
    }

    public void setUsername(String user) {
        this.username.setText(user);
    }

    private Posts getPost(){
        Posts post = new Posts();
        post.setId(1);
        post.setTitle("admin");
        post.setContent("aaaa");
        return post;
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
    }


    public void onReactionImgPressed(MouseEvent mouseEvent) {


    }

    public void onLikeContainerPressed(MouseEvent mouseEvent) {


        if (currentReaction == Reactions.NON) {
            setReaction(Reactions.LIKE);
        } else {
            setReaction(Reactions.NON);
        }

        likePost(Integer.parseInt(idPost.getText()));

    }
    private void likePost(int postId) {

        ServicePosts servicePosts=new ServicePosts();



        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Simulate delay
                Thread.sleep(500);

                servicePosts.UpdateLikes(postId);


                // Fetch updated like count from database
                int newLikeCount = servicePosts.getLikeCount(postId);
                // Update UI on JavaFX Application Thread
              //  nbReactions.setText(String.valueOf(newLikeCount));

                updateUI(newLikeCount);



                return null;
            }
        };


        task.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                Throwable exception = task.getException();
                if (exception != null) {
                    exception.printStackTrace();
                }
            }
        });

        new Thread(task).start();
    }


    private void updateUI(int likeCount) {
        // Update UI on JavaFX Application Thread
      //  nbReactions.setText(String.valueOf(likeCount));
        Platform.runLater(() -> nbReactions.setText(String.valueOf(likeCount)));



    }




    public void setReaction(Reactions reaction){
        currentReaction = reaction; // Update the currentReaction field

        Image image = new Image(getClass().getResourceAsStream(reaction.getImgSrc()));
        imgReaction.setImage(image);

        reactionName.setText(reaction.getName());
        reactionName.setTextFill(Color.web(reaction.getColor()));



       // nbReactions.setText(String.valueOf(post.getTotalReactions()));
    }

    public void onLikeContainerMouseReleased(MouseEvent mouseEvent) {


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
            stage.setScene(new Scene(root, 555, 555));
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











}
