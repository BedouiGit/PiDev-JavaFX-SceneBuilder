package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javafx.stage.Stage;
import models.Article;
import services.ArticleService;

import java.io.IOException;
import java.sql.SQLException;

public class ListArticle {
    @FXML
    private ListView<Article> listArticles;

    private ObservableList<Article> articleList;

    public void initialize() {
        initializeListView();
        populateListView();
    }

    private void initializeListView() {
        articleList = FXCollections.observableArrayList();
        listArticles.setItems(articleList);

        listArticles.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Article article, boolean empty) {
                super.updateItem(article, empty);

                if (empty || article == null) {
                    setGraphic(null);
                } else {
                    GridPane gridPane = new GridPane();
                    gridPane.setHgap(10);
                    gridPane.setVgap(5);

                    gridPane.add(new Label("ID"), 0, 0);
                    gridPane.add(new Label("Titre"), 1, 0);
                    gridPane.add(new Label("Auteur"), 2, 0);
                    gridPane.add(new Label("Date"), 3, 0);

                    gridPane.add(new Label(String.valueOf(article.getId())), 0, 1);
                    gridPane.add(new Label(article.getTitre()), 1, 1);
                    gridPane.add(new Label(article.getAuteur()), 2, 1);
                    gridPane.add(new Label(String.valueOf(article.getDate())), 3, 1);

                    HBox actionButtons = new HBox(6);
                    Button deleteButton = new Button("Delete");
                    deleteButton.setOnAction(event -> deleteArticle(article));
                    actionButtons.getChildren().add(deleteButton);


                    gridPane.add(actionButtons, 4, 1);

                    setGraphic(gridPane);
                }
            }
        });
    }

    private void populateListView() {
        ArticleService articleService = new ArticleService();
        try {
            articleList.addAll(articleService.selectAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteArticle(Article article) {
        ArticleService articleService = new ArticleService();
        try {
            articleService.deleteOne(article);
            articleList.remove(article); // Remove the article from the list
            System.out.println("Article deleted successfully");
        } catch (SQLException e) {
            System.err.println("Error deleting article: " + e.getMessage());
            e.printStackTrace();
        }
    }


}



