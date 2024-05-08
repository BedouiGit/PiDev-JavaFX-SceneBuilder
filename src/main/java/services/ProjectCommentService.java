package services;

import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.projectComments;
import models.projets;

public class ProjectCommentService {

    Connection cnx = MyDB.getInstance().getConnection();

    userService userService = new userService();
    public ProjectCommentService() {
    }

    public void ajouter(projectComments comment) {
        String sql = "INSERT INTO projets_comments (user_id, projet_id, comment_text, created_at) VALUES (?, ?, ?, NOW())";

        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, comment.getUserId().getId());
            statement.setInt(2, comment.getEventId());
            statement.setString(3, comment.getCommentText());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                comment.setCommentId(generatedKeys.getInt(1));
            }
            System.out.println("Comment Added for Projet ID: " + comment.getEventId());
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }

    public void modifier(projectComments comment) {
        String sql = "UPDATE projets_comments SET comment_text = ? WHERE id = ?";

        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setString(1, comment.getCommentText());
            statement.setInt(2, comment.getCommentId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Comment updated successfully");
            } else {
                System.out.println("No comment found for comment ID: " + comment.getCommentId());
            }
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }

    public void supprimer(projectComments comment) {
        String sql = "DELETE FROM projets_comments WHERE id = ?";

        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setInt(1, comment.getCommentId());
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Comment deleted successfully");
            } else {
                System.out.println("No comment found for comment ID: " + comment.getCommentId());
            }
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }

    public List<projectComments> display(projets event) {

        List<projectComments> comments = new ArrayList<>();
        String sql = "SELECT * FROM projets_comments where id = ?";

        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setInt(1, event.getId());
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                projectComments comment = new projectComments();
                comment.setCommentId(rs.getInt("id"));
                int userId = rs.getInt("user_id");
                comment.setUserId(userService.getAll().stream().filter(e->e.getId() ==userId ).findFirst().orElse(null));

                comment.setEventId(rs.getInt("projet_id"));
                comment.setCommentText(rs.getString("comment_text"));
                comment.setCreatedAt(rs.getTimestamp("created_at"));
                comments.add(comment);
            }
        } catch (SQLException var6) {
            System.out.println(var6.getMessage());
        }
        return comments;
    }
}
