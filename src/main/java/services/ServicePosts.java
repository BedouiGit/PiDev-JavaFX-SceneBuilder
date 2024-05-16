package services;

import entities.Comments;
import entities.Posts;
import utils.MyDB;


import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicePosts implements  IService<Posts> {

    private Connection con;


    public ServicePosts() {
        con = MyDB.getInstance().getConnection();

    }

    @Override
    public void ajouter(Posts posts) throws SQLException {
        String req = "INSERT INTO actualite(titre, contenu, image_url) VALUES (?, ?, ?)";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setString(1, "NFTNavigator"); // Set title to "NFTNavigator"
            pre.setString(2, posts.getTitle()); // Use title content as content
            // Set a default value for image_url if it's null
            pre.setString(3, posts.getImage() != null ? posts.getImage() : "img/a.jpg");
            pre.executeUpdate();
        }
    }




    @Override
    public void modifier(Posts posts) throws SQLException {
        String req = "UPDATE actualite SET titre=?, contenu=?, image_url=? WHERE id=?";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setString(1, posts.getTitle());
            pre.setString(2, posts.getTitle());
            pre.setString(3, posts.getImage());
            pre.setInt(4, posts.getId());
            pre.executeUpdate();
        }
    }

    @Override
    public void supprimer(Posts posts) throws SQLException {
        String deleteCommentsSql = "DELETE FROM commentaire WHERE actualite_id = ?";
        try (PreparedStatement deleteCommentsStmt = con.prepareStatement(deleteCommentsSql)) {
            deleteCommentsStmt.setInt(1, posts.getId());
            deleteCommentsStmt.executeUpdate();
        }

        try (PreparedStatement pre = con.prepareStatement("DELETE FROM actualite WHERE id=?")) {
            pre.setInt(1, posts.getId());
            pre.executeUpdate();
        }
    }

    @Override
    public List<Posts> afficher() throws SQLException {
        List<Posts> postsList = new ArrayList<>();
        String req = "SELECT * FROM actualite";
        try (Statement ste = con.createStatement(); ResultSet res = ste.executeQuery(req)) {
            while (res.next()) {
                Posts p = new Posts();
                p.setId(res.getInt("id"));
                p.setTitle(res.getString("titre"));
                p.setContent(res.getString("contenu"));
                p.setImage(res.getString("image_url"));
                p.setNbLikes(res.getInt("likes"));
                postsList.add(p);
            }
        }
        return postsList;
    }

    @Override
    public Posts getPost(int postId) throws SQLException {
        String query = "SELECT * FROM actualite WHERE id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, postId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Posts post = new Posts();
                    post.setId(rs.getInt("id"));
                    post.setTitle(rs.getString("titre"));
                    post.setContent(rs.getString("contenu"));
                    post.setNbLikes(rs.getInt("likes"));
                    post.setImage(rs.getString("image_url"));
                    return post;
                }
            }
        }
        return null;
    }


    @Override
    public void UpdateLikes(int postId) {
        String req = "UPDATE actualite SET likes = likes + 1 WHERE id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(req)) {
            pstmt.setInt(1, postId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void incrementLikes(int postId) {
        String req = "UPDATE actualite SET likes = likes + 1 WHERE id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(req)) {
            pstmt.setInt(1, postId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void DecrementLikes(int postId) {
        String req = "UPDATE actualite SET likes = likes - 1 WHERE id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(req)) {
            pstmt.setInt(1, postId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    @Override
    public void addComment(Posts post, Comments comment) throws SQLException {
        String req = "INSERT INTO commentaire(actualite_id, contenu, date_contenu) VALUES (?, ?, ?)";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setInt(1, post.getId()); // Set the post_id foreign key
            pre.setString(2, comment.getContent()); // Set the content of the comment
            pre.setTimestamp(3, Timestamp.valueOf(comment.getDateCommentaire())); // Set the date_commentaire
            pre.executeUpdate();
        }}



    @Override
    public int getLikeCount(int postId) throws SQLException {


        String sql = "SELECT likes FROM actualite WHERE id = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, postId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("likes");
                }
            }
        }

        return 0;
    }



    @Override  // Function to get total likes from the database
    public int getTotalLikesFromDatabase() {
        int totalLikes = 0;
        try {
            String query = "SELECT SUM(likes) FROM publication";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                totalLikes = resultSet.getInt(10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalLikes;
    }



    public List<Posts> searchPost(String content) throws SQLException {
        String query = "SELECT * FROM actualite WHERE contenu LIKE ?";
        List<Posts> searchResults = new ArrayList<>();
        // Remove the try-with-resources for PreparedStatement to prevent automatic closing
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, "%" + content + "%");
        ResultSet resultSet = pstmt.executeQuery();
        while (resultSet.next()) {
            Posts post = new Posts();
            post.setId(resultSet.getInt("id"));
            post.setTitle(resultSet.getString("titre"));
            post.setContent(resultSet.getString("contenu"));
            post.setNbLikes(resultSet.getInt("likes"));
            post.setImage(resultSet.getString("image_url")); // Corrected: Retrieve as string
            searchResults.add(post);
        }
        // Do not close the ResultSet and PreparedStatement here
        // They will be closed by the calling code that handles the result
        return searchResults;
    }


    // Function to get engagement levels from the database
    public int[] getEngagementLevels() {
        int highEngagementThreshold = 100;
        int moderateEngagementThreshold = 50;

        int highEngagementCount = 0;
        int moderateEngagementCount = 0;
        int lowEngagementCount = 0;

        try {
            String query = "SELECT likes FROM publication";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int likes = resultSet.getInt("likes");
                if (likes > highEngagementThreshold) {
                    highEngagementCount++;
                } else if (likes >= moderateEngagementThreshold) {
                    moderateEngagementCount++;
                } else {
                    lowEngagementCount++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new int[]{highEngagementCount, moderateEngagementCount, lowEngagementCount};
    }



    @Override
    public void saveImageToDatabase(File imageFile, Posts posts) {
        try {
            String query = "INSERT INTO actualite (titre, contenu, image_url) VALUES (?, ?, ?)";

            PreparedStatement preparedStatement = con.prepareStatement(query);

            // Set the image path as a parameter
            preparedStatement.setString(1, "NFTNavigator"); // Set title to "NFTNavigator"
            preparedStatement.setString(2, posts.getTitle()); // Use index 2 for the second parameter
            preparedStatement.setString(3, imageFile.getName()); // Use index 3 for the third parameter

            // Execute the query
            preparedStatement.executeUpdate();

            System.out.println("Image path added to the database");

            // Close the connection
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to add image path to the database");
        }
    }
}
