package services;

import models.publication;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicePublicationArticle implements IServiceArticle<publication> {
    private Connection con;

    public ServicePublicationArticle() {
        con = MyDB.getInstance().getConnection();
    }

    @Override
    public void ajouter(publication publication) throws SQLException {
        String req = "INSERT INTO article (id_user_id, titre,contenu,photo, date) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, publication.getIdUserId());
        pre.setString(2, publication.getTitreP());
        pre.setString(3, publication.getDescriptionP());
        pre.setString(4, publication.getImageP());
        pre.setDate(5, new Date(publication.getDateP().getTime()));
        pre.executeUpdate();
    }

    @Override
    public void modifier(publication publication) throws SQLException {
        String req = "UPDATE article SET id_user_id=?, titre=?, contenu=?,photo=?, date=? WHERE id=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, publication.getIdUserId());
        pre.setString(2, publication.getTitreP());
        pre.setString(3, publication.getDescriptionP());
        pre.setString(4, publication.getImageP());
        pre.setDate(5, new Date(publication.getDateP().getTime()));
        pre.setInt(6, publication.getId());
        pre.executeUpdate();
    }

    @Override
    public void supprimer(publication publication) throws SQLException {
        PreparedStatement pre = con.prepareStatement("DELETE FROM article WHERE id=?");
        pre.setInt(1, publication.getId());
        pre.executeUpdate();
    }

    @Override
    public List<publication> afficher() throws SQLException {
        List<publication> listPublications = new ArrayList<>();
        String req = "SELECT * FROM article";
        Statement ste = con.createStatement();
        ResultSet res = ste.executeQuery(req);
        while (res.next()) {
            publication p = new publication();
            p.setId(res.getInt("id"));
            p.setIdUserId(res.getInt("id_user_id"));
            p.setTitreP(res.getString("titre"));
            p.setDescriptionP(res.getString("contenu"));
            p.setImageP(res.getString("photo"));
            p.setDateP(res.getDate("date"));
            listPublications.add(p);
        }
        return listPublications;
    }
}
