package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SideBarDashboard {

    @FXML
    void NavigateToCategories(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CategoriesFxml/Admin/ListcategoryAdmin.fxml"));
        Parent categoriesRoot = loader.load();

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(categoriesRoot);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void NavigateToTags(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Tagsfxml/affichageTag.fxml"));
        Parent categoriesRoot = loader.load();

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(categoriesRoot);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void NavigateToDash(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dash.fxml"));
        Parent categoriesRoot = loader.load();

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(categoriesRoot);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void NavigateToNFT(ActionEvent event)  throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NFTFxml/BackOffice/BackNft.fxml"));
        Parent categoriesRoot = loader.load();

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(categoriesRoot);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void NavigateToNews(ActionEvent event)  throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewsFxml/back_office/ShowPost_Back.fxml"));
        Parent categoriesRoot = loader.load();

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(categoriesRoot);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void NavigateToPublication(ActionEvent event)  throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Publicationfxml/affichagePub.fxml"));
        Parent categoriesRoot = loader.load();

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(categoriesRoot);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void NavigateTocreatePublication(ActionEvent event)  throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Publicationfxml/ajoutPub.fxml"));
        Parent categoriesRoot = loader.load();

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(categoriesRoot);
        stage.setScene(scene);
        stage.show();
    }}
