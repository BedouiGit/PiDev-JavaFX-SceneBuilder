package services;

import entities.Comments;
import entities.Posts;
import utils.MyDB;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicePosts implements  IService<Posts>{

    private Connection con;


    public ServicePosts(){
        con= MyDB.getInstance().getConnection();

    }
    @Override
    public void ajouter(Posts posts) throws SQLException {
        String req = "INSERT INTO publication(title, contenu_pub, likes) VALUES (?, ?, 0)";
        try (PreparedStatement pstmt = con.prepareStatement(req)) {
            pstmt.setString(1, posts.getTitle());
            pstmt.setString(2, posts.getContent());
            pstmt.executeUpdate();
            // After adding, refresh the data
            List<Posts> updatedPostsList = afficher();
            // Do something with the updated list if needed
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void modifier(Posts posts) throws SQLException {
        String req = "UPDATE publication SET title=?, contenu_pub=? WHERE id=?";
        try (PreparedStatement pstmt = con.prepareStatement(req)) {
            pstmt.setString(1, posts.getTitle());
            pstmt.setString(2, posts.getContent());
            pstmt.setInt(3, posts.getId());
            pstmt.executeUpdate();
            // After modifying, refresh the data
            List<Posts> updatedPostsList = afficher();
            // Do something with the updated list if needed
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void supprimer(Posts posts) throws SQLException {
        String deleteCommentsSql = "DELETE FROM comment WHERE publication_id = ?";
        try (PreparedStatement deleteCommentsStmt = con.prepareStatement(deleteCommentsSql)) {
            deleteCommentsStmt.setInt(1, posts.getId());
            deleteCommentsStmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            PreparedStatement pre = con.prepareStatement("DELETE FROM publication WHERE id=?");
            pre.setInt(1, posts.getId());
            pre.executeUpdate();
            // After deleting, refresh the data
            List<Posts> updatedPostsList = afficher();
            // Do something with the updated list if needed
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Posts> afficher() throws SQLException {

        List<Posts> postsList= new ArrayList<>();

        String req="select * from publication";
        Statement ste=con.createStatement();
        ResultSet res= ste.executeQuery(req);
        while (res.next()){
            Posts p = new Posts();
              p.setId(res.getInt(1));
            p.setTitle(res.getString(2));
            p.setContent(res.getString(3));
            p.setNbLikes(res.getInt(4));


            postsList.add(p);
        }
        return postsList;
    }

    public Posts getPost(int postId) throws SQLException {
        String query = "SELECT * FROM publication WHERE id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, postId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Posts post = new Posts();
                    post.setId(rs.getInt("id"));
                    post.setTitle(rs.getString("title"));
                    post.setContent(rs.getString("contenu_pub"));
                    post.setNbLikes(rs.getInt("likes"));
                    return post;
                }
            }
        }
        return null;
    }

    @Override
    public void UpdateLikes(int postId) {


        String req = "UPDATE Publication SET likes = likes + 1 WHERE id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(req)) {
            pstmt.setInt(1, postId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void addComment(Posts post, Comments comment) throws SQLException {
        String req = "INSERT INTO comment(publication_id, contenu, date_commentaire) VALUES (?, ?, ?)";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setInt(1, post.getId()); // Set the post_id foreign key
            pre.setString(2, comment.getContent()); // Set the content of the comment
            pre.setTimestamp(3, Timestamp.valueOf(comment.getDateCommentaire())); // Set the date_commentaire
            pre.executeUpdate();
        }}

    @Override
    public int getLikeCount(int postId) throws SQLException {


        String sql = "SELECT likes FROM Publication WHERE id = ?";

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
        String query = "SELECT * FROM publication WHERE contenu_pub LIKE ?";
        List<Posts> searchResults = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, "%" + content + "%");
            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    Posts post = new Posts();
                    post.setId(resultSet.getInt("id"));
                    post.setTitle(resultSet.getString("title"));
                    post.setContent(resultSet.getString("contenu_pub"));
                    post.setNbLikes(resultSet.getInt("likes"));
                    searchResults.add(post);
                }
            }
        }
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
        } finally {
            try {
                if (con!= null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new int[]{highEngagementCount, moderateEngagementCount, lowEngagementCount};
    }


}
