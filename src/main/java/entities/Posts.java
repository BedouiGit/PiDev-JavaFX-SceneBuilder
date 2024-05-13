package entities;

import java.util.List;

public class Posts {

    private int id;
    private List<Comments> comments;
    private List<Reactions> reactions;
    private String content;
    private String date;
    private String image;
    private int totalReactions;
    private int nbLikes;
    private int nbComments;
    private int nbShares;
    private String title;

    public Posts() {}

    public Posts(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
    public Posts(String title, String content) {
        this.title = title;
        this.content = content;
    }


    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    public List<Reactions> getReactions() {
        return reactions;
    }

    public void setReactions(List<Reactions> reactions) {
        this.reactions = reactions;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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

    public int getNbLikes() {
        return nbLikes;
    }

    public void setNbLikes(int nbLikes) {
        this.nbLikes = nbLikes;
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
                ", comments=" + comments +
                ", reactions=" + reactions +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                ", image='" + image + '\'' +
                ", totalReactions=" + totalReactions +
                ", nbLikes=" + nbLikes +
                ", nbComments=" + nbComments +
                ", nbShares=" + nbShares +
                ", title='" + title + '\'' +
                '}';
    }
}
