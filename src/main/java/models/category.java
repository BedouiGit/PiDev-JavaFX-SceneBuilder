package models;

import java.util.ArrayList;
import java.util.List;

public class category {
    private int id;
    private String nom;
    private String description;

    private String photoUrl;

    private List<projets> projets = new ArrayList<projets>();

    public List<projets> getProjets() {
        return projets;
    }

    public void setProjets(List<projets> questions) {
        this.projets = questions;
    }

    public void AddProjets(projets projet){
        if(!this.projets.contains(projet)){
            this.projets.add(projet);

        }
    }

    public category(int id, String nom, String description, String photoUrl) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.photoUrl = photoUrl;
    }
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }



    public String getPhotoUrl() {
        return photoUrl;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }

}
