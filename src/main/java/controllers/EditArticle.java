package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import models.Article;
import services.ArticleService;

import java.sql.SQLException;

public class EditArticle {

    @FXML
    private TextField articleTitleField;

    @FXML
    private TextField articleContentField;

    private Article article;
    private ArticleService articleService;

    public EditArticle() {
        articleService = new ArticleService();
    }

    public void initData(Article article) {
        this.article = article;
        articleTitleField.setText(article.getTitre());
        articleContentField.setText(article.getContenu());
    }

    @FXML
    private void saveChanges() {
        if (article != null) {
            article.setTitre(articleTitleField.getText());
            article.setContenu(articleContentField.getText());
            // Assuming you have a method to update the article in your ArticleService
            try {
                articleService.updateOne(article);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        closeDialog();
    }

    @FXML
    private void closeDialog() {
        articleTitleField.getScene().getWindow().hide();
    }

}
