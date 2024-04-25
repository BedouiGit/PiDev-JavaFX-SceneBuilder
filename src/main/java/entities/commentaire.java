package entities;

import java.time.LocalDateTime;

public class commentaire {
    private int id;
    private int actualite_id; // Foreign key referencing the id of the related Actualite
    private String author;
    private String contenu;
    private LocalDateTime dateContenu;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_actualite() {
        return actualite_id;
    }

    public void setId_actualite(int id_actualite) {
        this.actualite_id = id_actualite;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public LocalDateTime getDateContenu() {
        return dateContenu;
    }

    public void setDateContenu(LocalDateTime dateContenu) {
        this.dateContenu = dateContenu;
    }

    public commentaire() {
    }

    public commentaire( int id_actualite, String author, String contenu, LocalDateTime dateContenu) {
        this.actualite_id = actualite_id;
        this.author = author;
        this.contenu = contenu;
        this.dateContenu = dateContenu;
    }

    @Override
    public String toString() {
        return "Commentaire{" +
                "id=" + id +
                ", id_actualite=" + actualite_id +
                ", author='" + author + '\'' +
                ", contenu='" + contenu + '\'' +
                ", dateContenu=" + dateContenu +
                '}';
    }
}
