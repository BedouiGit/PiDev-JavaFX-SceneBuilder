package Controller;

import entities.Comments;
import entities.Posts;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import services.ServiceComments;
import services.ServicePosts;
import utils.BadWordFilter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AddComment {


    private Posts post=new Posts();

    @FXML
    private ListView<String> commentListView;

    @FXML
    private TextArea commentTextArea;

    public void setPost(Posts post) {

        this.post = post;
        // You can use the post in this controller as needed

    }

    public void initialize() {
        Platform.runLater(() -> {
            try {

                ServiceComments sc = new ServiceComments();
                // Retrieve comments associated with the selected post
                List<String> comments = new ArrayList<>(sc.getCommentsForPost(post));
                // Create an ObservableList from the comments list

                ObservableList<String> observableComments = FXCollections.observableArrayList(comments);

                // Populate the ListView with the comments
                commentListView.setItems(observableComments);
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle exception
            }
        });
    }




    public void deleteSelectedComment(ActionEvent event) {

        ServiceComments Sc=new ServiceComments();
        String SP = commentListView.getSelectionModel().getSelectedItem();

        if (SP != null) {
          //  int id = SP.getId();
            Sc.delete(SP);
            commentListView.getItems().remove(SP);
        }
    }



    public void addComment(ActionEvent actionEvent) throws SQLException {
      //  LocalDateTime dateCommentaire = LocalDateTime.now(); // Get the current date and time


        String censoredText = BadWordFilter.getCensoredText(commentTextArea.getText());

        Comments comment = new Comments(censoredText);


        ServicePosts ps=new ServicePosts();
        //post.setContent(content.getId());
        try {
            ps.addComment(post,comment);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public void onItemSelected(ListView.EditEvent<String> stringEditEvent) {
    }
}
