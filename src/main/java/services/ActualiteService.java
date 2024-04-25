package services;

import entities.actualite;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActualiteService implements IService <actualite>{

    Connection cnx;

    public ActualiteService() {
        cnx = MyDB.getInstance().getConnection();
    }
    @Override
    public boolean ajouter(actualite t) throws SQLException{
        boolean success = false;
        String req = "INSERT INTO actualite(titre, contenu, categorie, date_publication, image_url) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, t.getTitre());
            ps.setString(2, t.getContenu());
            ps.setString(3, t.getCategorie());
            ps.setObject(4, t.getDatePublication());
            ps.setString(5, t.getImageUrl());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    t.setId(generatedKeys.getInt(1));
                }
                System.out.println("Actualité ajoutée");
                success = true;
            } else {
                System.out.println("Failed to add actualité.");
            }
        }

        return success;
    }
    @Override

    public void modifier(actualite t) throws SQLException {
        String req = "UPDATE actualite SET titre=?, contenu=?, categorie=?, image_url=? WHERE id=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, t.getTitre());
        ps.setString(2, t.getContenu());
        ps.setString(3, t.getCategorie());
        ps.setString(4, t.getImageUrl());
        ps.setInt(5, t.getId());
        ps.executeUpdate();
        System.out.println("Actualité modifiée");
    }


    @Override

    public void supprimer(actualite t) {
        try {
            if (t != null) {
                String req = "DELETE FROM actualite WHERE id=?";
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setInt(1, t.getId());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Actualité supprimée");
                } else {
                    System.out.println("No actualité deleted. Perhaps the ID does not exist.");
                }
            } else {
                System.out.println("The actualite object is null");
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Print stack trace for debugging
            System.out.println("Error deleting actualité: " + ex.getMessage());
        }
    }


    @Override
    public List<actualite> afficher() throws SQLException {
        return null;
    }


    @Override
    public List<actualite> recuperer() throws SQLException{
        List<actualite> actualites = new ArrayList<>();
        try {
            String req = "SELECT * FROM actualite ORDER BY date_publication DESC";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                actualite a = new actualite();
                a.setId(rs.getInt("id"));
                a.setTitre(rs.getString("titre"));
                a.setContenu(rs.getString("contenu"));
                a.setCategorie(rs.getString("categorie")); // Assuming actualite has a category field
                a.setDatePublication(rs.getTimestamp("date_publication").toLocalDateTime());
                a.setImageUrl(rs.getString("image_url")); // Assuming actualite has an image URL field
                actualites.add(a);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return actualites;
    }



}
