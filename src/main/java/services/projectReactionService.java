package services;

import models.projet_reactions;
import utils.MyDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class projectReactionService {

    Connection cnx = MyDB.getInstance().getConnection();
    userService userService = new userService();

    public projectReactionService() {
    }

    public void ajouter(projet_reactions reaction) {
        String sql = "INSERT INTO projets_reactions (user_id, projet_id, reaction_type, created_at) VALUES (?, ?, ?, NOW())";

        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setInt(1, reaction.getUserId().getId());
            statement.setInt(2, reaction.getEventId());
            statement.setString(3, reaction.getReactionType());
            statement.executeUpdate();
            System.out.println("Reaction Added for Event ID: " + reaction.getEventId());
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }

    public void modifier(projet_reactions reaction) {
        String sql = "UPDATE projets_reactions SET reaction_type = ? WHERE user_id = ? AND projet_id = ?";

        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setString(1, reaction.getReactionType());
            statement.setInt(2, reaction.getUserId().getId());
            statement.setInt(3, reaction.getEventId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Reaction updated successfully");
            } else {
                System.out.println("No reaction found for user ID: " + reaction.getUserId() + " and event ID: " + reaction.getEventId());
            }
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }

    public void supprimer(projet_reactions reaction) {
        String sql = "DELETE FROM projets_reactions WHERE user_id = ? AND projet_id = ?";

        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setInt(1, reaction.getUserId().getId());
            statement.setInt(2, reaction.getEventId());
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Reaction deleted successfully");
            } else {
                System.out.println("No reaction found for user ID: " + reaction.getUserId() + " and event ID: " + reaction.getEventId());
            }
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }

    public List<projet_reactions> display() {
        List<projet_reactions> reactions = new ArrayList<>();
        String sql = "SELECT * FROM projets_reactions";

        try {
            Statement statement = this.cnx.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                projet_reactions reaction = new projet_reactions();

                int userId = rs.getInt("user_id");
                reaction.setUserId(userService.getAll().stream().filter(e -> e.getId() == userId).findFirst().orElse(null));
                reaction.setEventId(rs.getInt("projet_id"));
                reaction.setReactionType(rs.getString("reaction_type"));
                reaction.setCreatedAt(rs.getTimestamp("created_at"));
                reactions.add(reaction);
            }
        } catch (SQLException var6) {
            System.out.println(var6.getMessage());
        }
        return reactions;
    }

    public boolean hasUserReacted(int userId, int eventId, String reactionType) {
        // Implement logic to check if the user has reacted to the event with the given reaction type
        // Return true if the user has reacted, false otherwise
        return false; // Placeholder, replace with actual implementation
    }

    public void addReaction(projet_reactions reaction) {
        // Implement logic to add a reaction for the user to the event
    }

    public void removeReaction(int userId, int eventId, String reactionType) {
        // Implement logic to remove the user's reaction to the event
    }

    public long countReactions(int eventId) {
        // Implement logic to count the number of reactions for the event
        return 0; // Placeholder, replace with actual implementation
    }
}
