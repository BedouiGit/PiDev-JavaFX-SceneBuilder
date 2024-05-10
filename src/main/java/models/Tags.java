package models;
import models.publication;

import java.util.HashSet;
import java.util.Set;
public class Tags {
    private int id;

    private String nom;
    private String description;

    private String imageT;
    public Tags() {}

    public Tags(String nom, String description,String imageT) {
        this.nom = nom;
        this.description = description;
        this.imageT = imageT;
    }



    public Tags(int id, String nom, String description) {
        this.id = id;
        this.nom = nom;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getImageT() {
        return imageT;
    }

    public void setImageT(String imageT) {
        this.imageT = imageT;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String  toString() {
        return "Tag{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", imageT='" + imageT + '\'' +
                '}';
    }
}
