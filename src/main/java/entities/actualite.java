package entities;

import java.time.LocalDateTime;

public class actualite {
    private int id;
    private String titre;
    private String contenu;
    private String categorie;
    LocalDateTime datePublication;
    private String imageUrl;


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

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public LocalDateTime getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(LocalDateTime datePublication) {
        this.datePublication = datePublication;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public actualite() {
    }

    public actualite(int id, String titre, String contenu, String categorie, LocalDateTime datePublication, String imageUrl) {
        this.id = id;
        this.titre = titre;
        this.contenu = contenu;
        this.categorie = categorie;
        this.datePublication = datePublication;
        this.imageUrl = imageUrl;
    }


    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", contenu='" + contenu + '\'' +
                ", categorie='" + categorie + '\'' +
                ", datePublication=" + datePublication +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
