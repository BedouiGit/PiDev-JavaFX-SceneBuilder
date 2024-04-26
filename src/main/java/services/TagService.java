package services;

import models.Tag;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TagService implements CRUD<Tag> {

    private Connection cnx;

    public TagService() {
        cnx = DBConnection.getInstance().getCnx();
    }

    @Override
    public void insertOne(Tag tag) throws SQLException {
        String req = "INSERT INTO `tags`(`nom`, `description`) VALUES (?, ?)";

        PreparedStatement preparedStatement = cnx.prepareStatement(req);
        preparedStatement.setString(1, tag.getNom());
        preparedStatement.setString(2, tag.getDescription());

        preparedStatement.executeUpdate();
        System.out.println("Tag Added !");
    }

    @Override
    public void updateOne(Tag tag) throws SQLException {
        // Implement update functionality here
    }

    @Override
    public void deleteOne(Tag tag) throws SQLException {
        String query = "DELETE FROM tags WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, tag.getId());

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Tag deleted successfully!");
            } else {
                System.out.println("No tag found with the given ID.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TagService.class.getName()).log(Level.SEVERE, null, ex);
            throw ex; // Re-throw the exception to handle it outside this method if needed
        }
    }


    @Override
    public List<Tag> selectAll() throws SQLException {
        List<Tag> tagList = new ArrayList<>();

        String req = "SELECT * FROM `tags`";
        Statement st = cnx.createStatement();

        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            Tag tag = new Tag();
            tag.setId(rs.getInt("id"));
            tag.setNom(rs.getString("nom"));
            tag.setDescription(rs.getString("description"));

            tagList.add(tag);
        }

        return tagList;
    }





}
