package services;

import entities.Commentaire;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceCommentaire implements IService<Commentaire> {
    private Connection con;

    public ServiceCommentaire() {
        con = MyDB.getInstance().getConnection();
    }

    @Override
    public void ajouter(Commentaire commentaire) throws SQLException {
        String req = "INSERT INTO commentaires (publication_id, contenu_c, id_user) VALUES (?, ?, ?)";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, commentaire.getPublication_id());
        pre.setString(2, commentaire.getContenu_c());
        pre.setInt(3, commentaire.getId_user());
        pre.executeUpdate();
    }


    @Override
    public void modifier(Commentaire commentaire) throws SQLException {
        String req = "UPDATE commentaires SET publication_id=?, contenu_c=?, id_user=? WHERE id=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, commentaire.getPublication_id());
        pre.setString(2, commentaire.getContenu_c());
        pre.setInt(3, commentaire.getId_user());
        pre.setInt(4, commentaire.getId());
        pre.executeUpdate();
    }

    @Override
    public void supprimer(Commentaire commentaire) throws SQLException {
        PreparedStatement pre = con.prepareStatement("DELETE FROM commentaires WHERE id=?");
        pre.setInt(1, commentaire.getId());
        pre.executeUpdate();
    }

    @Override
    public List<Commentaire> afficher() throws SQLException {
        List<Commentaire> listCommentaires = new ArrayList<>();
        String req = "SELECT * FROM commentaires";
        Statement ste = con.createStatement();
        ResultSet res = ste.executeQuery(req);
        while (res.next()) {
            Commentaire c = new Commentaire();
            c.setId(res.getInt("id"));
            c.setPublication_id(res.getInt("publication_id"));
            c.setContenu_c(res.getString("contenu_c"));
            c.setId_user(res.getInt("id_user"));
            listCommentaires.add(c);
        }
        return listCommentaires;
    }




        public List<Commentaire> getCommentairesByPublication(int publicationId) throws SQLException {
            List<Commentaire> listCommentaires = new ArrayList<>();
            String req = "SELECT * FROM commentaires WHERE publication_id = ?";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setInt(1, publicationId);
            ResultSet res = pre.executeQuery();
            while (res.next()) {
                Commentaire c = new Commentaire();
                c.setId(res.getInt("id"));
                c.setPublication_id(res.getInt("publication_id"));
                c.setContenu_c(res.getString("contenu_c"));
                c.setId_user(res.getInt("id_user"));
                listCommentaires.add(c);
            }
            return listCommentaires;
        }
    }


