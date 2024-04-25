package test;

import entities.actualite;
import entities.commentaire;
import services.ActualiteService;
import services.CommentaireService;
import utils.MyDB;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws SQLException {
        // Get an instance of the database connection
        MyDB myDB = MyDB.getInstance();

        // Instantiate services
        ActualiteService actualiteService = new ActualiteService();
        CommentaireService commentaireService = new CommentaireService();

        // Add actualites
        actualite newActualite1 = new actualite(2,"Titre1", "Contenu1", "Categorie1", LocalDateTime.now(), "Image1");
        actualite newActualite2 = new actualite(4,"Titre2", "Contenu2", "Categorie2", LocalDateTime.now(), "Image2");

        // Add actualites to the database
        actualiteService.ajouter(newActualite1);
        actualiteService.ajouter(newActualite2);

        // Add commentaires related to the actualites
        commentaire newCommentaire1 = new commentaire(10, "John", "This is a test comment for Actualite 1.", LocalDateTime.now());
        commentaire newCommentaire2 = new commentaire(11, "Alice", "Another test comment for Actualite 2.", LocalDateTime.now());

        // Add commentaires to the database
        commentaireService.ajouter(newCommentaire1);
        commentaireService.ajouter(newCommentaire2);

        System.out.println("New commentaires added: " + newCommentaire1 + ", " + newCommentaire2);
    }
}
