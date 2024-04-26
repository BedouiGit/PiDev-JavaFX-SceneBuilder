package models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
public class Article {
    private int id;
    private String titre;
    private String contenu;
    private String auteur;
    private Date date;
    private Set<Tag> tags = new HashSet<>();

    public Article() {}


    public Article(String titre, String contenu, String auteur, Date date) {
        this.titre = titre;
        this.contenu = contenu;
        this.auteur = auteur;
        this.date = date;
    }

    public Article(int id, String titre, String contenu, String auteur, Date date) {
        this.id = id;
        this.titre = titre;
        this.contenu = contenu;
        this.auteur = auteur;
        this.date = date;
    }
    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getArticles().add(this);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.getArticles().remove(this);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", contenu='" + contenu + '\'' +
                ", auteur='" + auteur + '\'' +
                ", date=" + date +
                '}';
    }

}
