package test;

import services.ArticleService;
import services.TagService;
import utils.DBConnection;
import models.Article;
import models.Tag;

import java.sql.SQLException;
import java.util.List;

public class MainClass {

    public static void main(String[] args) {
        DBConnection cn1 = DBConnection.getInstance();

        ArticleService serviceArticle = new ArticleService();
        TagService serviceTag = new TagService(); // Utilisez TagService au lieu de ArticleService

        try {
            // Exemple d'utilisation : sélectionnez tous les articles et affichez-les
            List<Article> articles = serviceArticle.selectAll();
            for (Article article : articles) {
                System.out.println(article);
            }

            // Exemple d'utilisation : sélectionnez tous les tags et affichez-les
            List<Tag> tags = serviceTag.selectAll(); // Utilisez serviceTag au lieu de TagService.selectAll()
            for (Tag tag : tags) {
                System.out.println(tag);
            }
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
    }
}
