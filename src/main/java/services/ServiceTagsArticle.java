package services;

import models.Tags;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceTagsArticle implements IServiceArticle<Tags> {
    private Connection con;

    public ServiceTagsArticle() {
        con = DBConnection.getInstance().getConnection();
    }

    @Override
    public void ajouter(Tags tag) throws SQLException {
        String req = "INSERT INTO tags (id,nom, description, phototag) VALUES (?, ?, ?, ?)";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, tag.getId());
        pre.setString(2, tag.getNom());
        pre.setString(3, tag.getDescription());
        pre.setString(4, tag.getImageT());
        pre.executeUpdate();
    }

    @Override
    public void modifier(Tags tag) throws SQLException {
        String req = "UPDATE tags SET id=?, nom=?, description=?, phototag=? WHERE id=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, tag.getId());
        pre.setString(2, tag.getNom());
        pre.setString(3, tag.getDescription());
        pre.setString(4, tag.getImageT());
        pre.setInt(5, tag.getId()); // Bind the ID again for the WHERE clause

        pre.executeUpdate();
    }

    @Override
    public void supprimer(Tags tag) throws SQLException {
        PreparedStatement pre = con.prepareStatement("DELETE FROM tags WHERE id=?");
        pre.setInt(1, tag.getId());
        pre.executeUpdate();
    }

    @Override
    public List<Tags> afficher() throws SQLException {
        List<Tags> listTags = new ArrayList<>();
        String req = "SELECT * FROM tags";
        Statement ste = con.createStatement();
        ResultSet res = ste.executeQuery(req);
        while (res.next()) {
            Tags p = new Tags();
            p.setId(res.getInt("id"));
            p.setNom(res.getString("nom"));
            p.setDescription(res.getString("description"));
            p.setImageT(res.getString("phototag"));
            listTags.add(p);
        }
        return listTags;
    }
}
