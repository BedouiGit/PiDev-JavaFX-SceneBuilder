package entities;

import java.time.LocalDateTime;

public class Comments {
    private int idComment;

    String content;
    private LocalDateTime date_commentaire;

    private Posts post; // Reference to the associated post

    public Comments(String text) {
        this.content=text;
        this.date_commentaire=LocalDateTime.now();
    }


    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }




    public void setPost(Posts post) {
        this.post = post;
    }

    // Getter method for date_commentaire
    public LocalDateTime getDateCommentaire() {
        return LocalDateTime.now();
    }
}
