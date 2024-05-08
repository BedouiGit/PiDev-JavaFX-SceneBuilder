package models;

import java.util.Date;

public class projectComments {

    private int commentId;
    private user user;
    private int projetId;
    private String commentText;
    private Date createdAt;

    public projectComments( user user, int eventId, String commentText ) {

        this.user = user;
        this.projetId = eventId;
        this.commentText = commentText;
        this.createdAt = createdAt;
    }

    public projectComments() {
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public user getUserId() {
        return user;
    }

    public void setUserId(user userId) {
        this.user = userId;
    }

    public int getEventId() {
        return projetId;
    }

    public void setEventId(int eventId) {
        this.projetId = eventId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "ProjetComment{" +
                ", commentText='" + commentText + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
