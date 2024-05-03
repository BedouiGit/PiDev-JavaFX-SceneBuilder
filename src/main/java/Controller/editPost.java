package Controller;

import entities.Posts;
import javafx.fxml.FXML;
import services.ServicePosts;
import javafx.scene.control.TextField;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;



import javafx.scene.input.MouseEvent;

public class editPost {

    @FXML
    private TextField contentTF;


    @FXML
    private Button editPost;


    @FXML
    private Label content;
    @FXML
    private Label username;
    @FXML
    private Label idPost;

    private Posts post;

    public void setPost(Posts post) {
        this.post = post;

        contentTF.setText(post.getContent());
        username.setText(post.getTitle());
        idPost.setText(String.valueOf(post.getId()));
        // Now you can access the post data and update your UI accordingly
    }



    @FXML
    void OnEditPostClicked(MouseEvent event) throws SQLException {

        Posts post= new Posts(idPost.getText(),username.getText(),contentTF.getText());
        ServicePosts ps=new ServicePosts();
        //post.setContent(content.getId());
        ps.modifier(post);





    }

















}
