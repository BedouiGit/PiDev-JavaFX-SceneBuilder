package services;

import entities.commentaire;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import utils.MyDB;

public class CommentaireService {

    private Connection cnx;

    public CommentaireService() {
        cnx = MyDB.getInstance().getConnection();
    }

    public void ajouter(commentaire commentaire) {
        try (Connection connection = MyDB.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO commentaire (actualite_id, author, contenu, date_contenu) VALUES (?, ?, ?, ?)")) {

            ps.setInt(1, commentaire.getId_actualite());
            ps.setString(2, commentaire.getAuthor());
            ps.setString(3, commentaire.getContenu());
            ps.setObject(4, commentaire.getDateContenu());

            ps.executeUpdate();
            System.out.println("New commentaire added: " + commentaire);

        } catch (SQLException ex) {
            System.out.println("Error adding commentaire: " + ex.getMessage());
        }
    }

    public void modifier(commentaire commentaire) {
        try (PreparedStatement ps = cnx.prepareStatement("UPDATE commentaire SET contenu = ? WHERE id = ?")) {
            ps.setString(1, commentaire.getContenu());
            ps.setInt(2, commentaire.getId());
            ps.executeUpdate();
            System.out.println("Commentaire modifié");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void supprimer(int id) {
        try (PreparedStatement ps = cnx.prepareStatement("DELETE FROM commentaire WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Commentaire supprimé avec succès");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<commentaire> recuperer(int id_actualite) {
        List<commentaire> comments = new ArrayList<>();
        try (PreparedStatement ps = cnx.prepareStatement("SELECT * FROM commentaire WHERE id_actualite = ?")) {
            ps.setInt(1, id_actualite);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    commentaire commentaire = new commentaire();
                    commentaire.setId(rs.getInt("id"));
                    commentaire.setId_actualite(rs.getInt("id_actualite"));
                    commentaire.setAuthor(rs.getString("author"));
                    commentaire.setContenu(rs.getString("contenu"));
                    commentaire.setDateContenu(rs.getTimestamp("date_contenu").toLocalDateTime());
                    comments.add(commentaire);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return comments;
    }

    public commentaire detail(int id) {
        commentaire commentaire = new commentaire();
        try (PreparedStatement ps = cnx.prepareStatement("SELECT * FROM commentaire WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    commentaire.setId(rs.getInt("id"));
                    commentaire.setId_actualite(rs.getInt("id_actualite"));
                    commentaire.setAuthor(rs.getString("author"));
                    commentaire.setContenu(rs.getString("contenu"));
                    commentaire.setDateContenu(rs.getTimestamp("date_contenu").toLocalDateTime());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return commentaire;
    }

    public boolean userPost(int id, int id_actualite) {
        try (PreparedStatement ps = cnx.prepareStatement("SELECT * FROM commentaire WHERE id = ? AND actualite_id = ?")) {
            ps.setInt(1, id);
            ps.setInt(2, id_actualite);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
}
