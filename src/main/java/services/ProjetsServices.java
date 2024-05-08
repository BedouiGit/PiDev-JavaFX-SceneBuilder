package services;


import utils.MyDB;
import models.projets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.sql.*;

public class ProjetsServices {
    private Connection conn;
    private Statement ste;
    private PreparedStatement pst;

    public ProjetsServices() {
        conn = MyDB.getInstance().getConnection();
    }


     public List<projets>getProjectsByCategory(int categoryId) {
        String query = "SELECT id, nom, description, wallet_address, date_de_creation, photo_url FROM projets WHERE category_id=?";
        List<projets> projects = new ArrayList<>();
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, categoryId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                projects.add(new projets(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getString("wallet_address"),
                        rs.getDate("date_de_creation"),
                        rs.getString("photo_url"),
                        categoryId
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return projects;
    }


    public List<projets> obtenirToutesLesProjets() {
        String requete = "SELECT id, nom, description,wallet_address,date_de_creation,photo_url FROM projets";
        List<projets> list = new ArrayList<>();
        try {
            ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                list.add(new projets(rs.getInt("id"), rs.getString("nom"), rs.getString("description"), rs.getString("wallet_address"),rs.getDate("date_de_creation"),rs.getString("photo_url"),rs.getInt("category_id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public projets obtenirProjets(int projetID) {
        String requete = "SELECT id, nom, description ,wallet_address,date_de_creation,photo_url FROM projets WHERE id=?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, projetID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new projets(rs.getInt("id"), rs.getString("nom"), rs.getString("description"), rs.getString("wallet_address"),rs.getDate("date_de_creation"),rs.getString("photo_url"),rs.getInt("category_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public int obtenirIdProjetsParId(int projetID) {
        String requete = "SELECT id FROM projets WHERE id=?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, projetID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1; // Retourne -1 si l'ID de l'activité n'est pas trouvé
    }
    public String obtenirNomProjets(int projetID) {
        String requete = "SELECT nom FROM projets WHERE id=?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, projetID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("nom");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null; // Retourne null si l'ID de l'activité n'est pas trouvé
    }
    public void insererProjet(projets projet) {
        String requete = "INSERT INTO projets (nom, description, wallet_address, date_de_creation, photo_url,category_id) VALUES (? , ? , ?, ?, ?, ? )";
        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, projet.getNom());
            pst.setString(2, projet.getDescription());
            pst.setString(3, projet.getWallet_address());
            pst.setDate(4, new java.sql.Date(projet.getDateDeCreation().getTime()));
            pst.setString(5, projet.getPhotoUrl());
            pst.setInt(6,projet.getCategory_id());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void modifierProjet(projets projet) {
        String requete = "UPDATE projets SET nom=?, description=?,wallet_address=?, date_de_creation=?, photo_url=? WHERE id=?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, projet.getNom());
            pst.setString(2, projet.getDescription());
            pst.setString(3, projet.getWallet_address());
            pst.setDate(4, new java.sql.Date(projet.getDateDeCreation().getTime()));
            pst.setString(5, projet.getPhotoUrl());
            pst.setInt(6, projet.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void supprimerProjet(int projetID) {
        String requete = "DELETE FROM projets WHERE id=?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, projetID);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getCategoryIdByProjectId(int projectId) {
        String query = "SELECT category_id FROM projets WHERE id=?";
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, projectId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("category_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1; // Return -1 if category ID is not found
    }

    public projets getProjectById(int projectId) {
        String query = "SELECT id, nom, description, wallet_address, date_de_creation, photo_url, category_id FROM projets WHERE id=?";
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, projectId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new projets(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getString("wallet_address"),
                        rs.getDate("date_de_creation"),
                        rs.getString("photo_url"),
                        rs.getInt("category_id")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null; // Return null if project ID is not found
    }


}
