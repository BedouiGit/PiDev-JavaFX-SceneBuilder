package models;

import java.util.Date;
import models.user;

public class projet_reactions {

    private int reactionId;
    private user user;
    private int projetId;
    private String reactionType;
    private Date createdAt;

    public projet_reactions( user user, int eventId, String reactionType) {
        this.user = user;
        this.projetId = eventId;
        this.reactionType = reactionType;

    }

    public projet_reactions() {

    }

    public int getReactionId() {
        return reactionId;
    }

    public void setReactionId(int reactionId) {
        this.reactionId = reactionId;
    }

    public user getUserId() {
        return user;
    }

    public void setUserId(user user) {
        this.user = user;
    }

    public int getEventId() {
        return projetId;
    }

    public void setEventId(int eventId) {
        this.projetId = eventId;
    }

    public String getReactionType() {
        return reactionType;
    }

    public void setReactionType(String reactionType) {
        this.reactionType = reactionType;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "ProjetReaction{" +
                ", reactionType='" + reactionType + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }


}
