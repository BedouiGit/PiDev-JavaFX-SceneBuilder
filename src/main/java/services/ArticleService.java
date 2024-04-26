package services;

import models.Article;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ArticleService implements CRUD<Article> {

    private Connection cnx;

    public ArticleService() {
        cnx = DBConnection.getInstance().getCnx();
    }

    @Override
    public void insertOne(Article article) throws SQLException {
        String req = "INSERT INTO `article`(`titre`, `contenu`, `auteur`, `date`) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = cnx.prepareStatement(req)) {
            pstmt.setString(1, article.getTitre());
            pstmt.setString(2, article.getContenu());
            pstmt.setString(3, article.getAuteur());
            pstmt.setDate(4, new java.sql.Date(article.getDate().getTime()));

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Article Added !");
            } else {
                System.out.println("Failed to add article!");
            }
        } catch (SQLException e) {
            System.err.println("Error inserting article: " + e.getMessage());
            throw e; // Rethrow the exception to be handled by the caller
        }
    }

    @Override
    public void updateOne(Article article) throws SQLException {
        String query = "UPDATE `article` SET `titre`=?, `contenu`=?, `auteur`=?, `date`=? WHERE `id`=?";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setString(1, article.getTitre());
            preparedStatement.setString(2, article.getContenu());
            preparedStatement.setString(3, article.getAuteur());
            preparedStatement.setDate(4, new java.sql.Date(article.getDate().getTime()));
            preparedStatement.setInt(5, article.getId());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Article updated successfully!");
            } else {
                System.out.println("No article found with the given ID.");
            }
        } catch (SQLException ex) {
            System.err.println("Error updating article: " + ex.getMessage());
            throw ex; // Rethrow the exception to be handled by the caller
        }
    }

    @Override
    public void deleteOne(Article article) throws SQLException {
        // Implement delete functionality here
    }

    @Override
    public List<Article> selectAll() throws SQLException {
        List<Article> articleList = new ArrayList<>();

        String req = "SELECT * FROM `article`";
        Statement st = cnx.createStatement();

        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            Article article = new Article();
            article.setId(rs.getInt("id"));
            article.setTitre(rs.getString("titre"));
            article.setContenu(rs.getString("contenu"));
            article.setAuteur(rs.getString("auteur"));
            article.setDate(rs.getDate("date"));

            articleList.add(article);
        }

        return articleList;
    }
}
