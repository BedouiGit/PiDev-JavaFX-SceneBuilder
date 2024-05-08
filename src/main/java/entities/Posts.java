package entities;

import javafx.fxml.FXML;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.util.List;

public class Posts {

     private int id ;

    private List<Comments> comments; // Collection of comments associated with this post

    public List<entities.Reactions> getReactions() {
        return Reactions;
    }

    private List<Reactions> Reactions; // Collection of comments associated with this post

    private String content;
    private String date;
    // private String caption;
    private String image;
    private int totalReactions;

    public void setNbLikes(int nbLikes) {
        this.nbLikes = nbLikes;
    }

    public int getNbLikes() {
        return nbLikes;
    }

    private int nbLikes;
    private int nbComments;
    private int nbShares;

    String  title ;

    public Posts(String text, String text1, String text2) {

        this.id= Integer.parseInt(text);
        this.title=text1;
        this.content=text2;

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Posts(String content) {
        this.content = content;
    }








    public  int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Posts{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public Posts() {
        this.content = content;
        this.title = title;
    }

    public Posts(String content, String title) {
        this.content = content;
        this.title = title;
    }




    //private Account account;
   // private PostAudience audience;


/*    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public PostAudience getAudience() {
        return audience;
    }

    public void setAudience(PostAudience audience) {
        this.audience = audience;
    }

 */

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getTotalReactions() {
        return totalReactions;
    }

    public void setTotalReactions(int totalReactions) {
        this.totalReactions = totalReactions;
    }

    public int getNbComments() {
        return nbComments;
    }

    public void setNbComments(int nbComments) {
        this.nbComments = nbComments;
    }

    public int getNbShares() {
        return nbShares;
    }

    public void setNbShares(int nbShares) {
        this.nbShares = nbShares;
    }


    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }












}
