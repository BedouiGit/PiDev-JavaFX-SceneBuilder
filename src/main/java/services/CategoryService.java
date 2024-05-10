package services;

import models.NFT;
import models.category;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryService implements ISERVICEScat<category> {
    private Connection conn;
    private PreparedStatement pst;

    Connection cnx = utils.MaConnexion.getInstance().getCnx();

    public CategoryService() {
        conn = MyDB.getInstance().getConnection();
    }

    @Override
    public void ajouter(category category) throws SQLException {
        String query = "INSERT INTO category (nom, description, photo_url) VALUES (?, ?, ?)";
        try {
            pst = conn.prepareStatement(query);
            pst.setString(1, category.getNom());
            pst.setString(2, category.getDescription());
            pst.setString(3, category.getPhotoUrl());
            pst.executeUpdate();
        } finally {
            if (pst != null) {
                pst.close();
            }
        }
    }

    @Override
    public void modifier(category category) throws SQLException {
        String query = "UPDATE category SET nom=?, description=?, photo_url=? WHERE id=?";
        try {
            pst = conn.prepareStatement(query);
            pst.setString(1, category.getNom());
            pst.setString(2, category.getDescription());
            pst.setString(3, category.getPhotoUrl());
            pst.setInt(4, category.getId());
            pst.executeUpdate();
        } finally {
            if (pst != null) {
                pst.close();
            }
        }
    }

    @Override
    public void supprimer(category category) throws SQLException {
        String query = "DELETE FROM category WHERE id=?";
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, category.getId());
            pst.executeUpdate();
        } finally {
            if (pst != null) {
                pst.close();
            }
        }
    }

    @Override
    public List<category> afficher() {
        List<category> categories = new ArrayList<>();
        try (Statement ste = conn.createStatement();
             ResultSet rs = ste.executeQuery("SELECT id, nom, description, photo_url FROM category")) {
            while (rs.next()) {
                categories.add(new category(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getString("photo_url")
                ));
            }
        } catch (SQLException e) {
            // Handle the exception (e.g., log the error, throw a custom exception)
            e.printStackTrace();
        }
        return categories;
    }


    @Override
    public category getCategoryByName(String name) throws SQLException {
        String query = "SELECT id, nom, description, photo_url FROM category WHERE nom=?";
        category category = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement(query);
            pst.setString(1, name);
            rs = pst.executeQuery();
            if (rs.next()) {
                category = new category(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getString("photo_url")
                );
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
        }
        return category;
    }


    @Override
    public category getCategoryById(int id) throws SQLException {
        String query = "SELECT id, nom, description, photo_url FROM category WHERE id=?";
        category category = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs.next()) {
                category = new category(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getString("photo_url")
                );
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
        }
        return category;
    }


    public List<category> searchBy(String filterBy, String search) throws SQLException {
        List<category> categories = new ArrayList<>();
        String req = "SELECT * FROM `category` WHERE `"+filterBy+"` LIKE '%"+search+"%'";
        System.out.println(req);
        Statement st = cnx.createStatement();
        ResultSet res = st.executeQuery(req);
        while (res.next()) {

            category category = new category();
            category.setId(res.getInt("id"));
            category.setNom(res.getString("Nom"));
            category.setDescription(res.getString("description"));
            category.setPhotoUrl(res.getString("photo_url"));

            categories.add(category);
        }

        return categories;
    }

    public Map<String, Integer> getProjectsPerCategory() throws SQLException {
        Map<String, Integer> projectsPerCategory = new HashMap<>();
        String query = "SELECT c.nom AS category_name, COUNT(p.id) AS project_count " +
                "FROM category c " +
                "LEFT JOIN projets p ON c.id = p.category_id " +
                "GROUP BY c.nom";
        try (PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                String categoryName = rs.getString("category_name");
                int projectCount = rs.getInt("project_count");
                projectsPerCategory.put(categoryName, projectCount);
            }
        }
        return projectsPerCategory;
    }
}
