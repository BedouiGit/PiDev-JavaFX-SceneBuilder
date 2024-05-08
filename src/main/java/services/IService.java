package services;

import entities.Comments;
import entities.Posts;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public interface IService<T> {

    void ajouter(T t) throws SQLException;

    void modifier(T t) throws SQLException;

    void supprimer(T t) throws SQLException;


    List<T> afficher() throws SQLException;


    void UpdateLikes(int postId);

    void addComment(Posts post, Comments comment) throws SQLException;

    int getLikeCount(int postId) throws SQLException;

    void incrementLikes(int postId);

    void DecrementLikes(int postId);

    // Function to get total likes from the database
    int getTotalLikesFromDatabase();

    void saveImageToDatabase(File imageFile, Posts posts);
}
