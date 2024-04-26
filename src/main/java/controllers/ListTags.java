package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import models.Tag;
import services.TagService;

import java.sql.SQLException;
import java.util.Optional;

public class ListTags {
    @FXML
    private ListView<Tag> listTags;

    private ObservableList<Tag> tagList;

    public void initialize() {
        initializeListView();
        populateListView();
    }

    private void initializeListView() {
        tagList = FXCollections.observableArrayList();
        listTags.setItems(tagList);

        listTags.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Tag tag, boolean empty) {
                super.updateItem(tag, empty);

                if (empty || tag == null) {
                    setGraphic(null);
                } else {
                    GridPane gridPane = new GridPane();
                    gridPane.setHgap(10);
                    gridPane.setVgap(5);

                    gridPane.add(new Label("ID"), 0, 0);
                    gridPane.add(new Label("Nom"), 1, 0);
                    gridPane.add(new Label("Description"), 2, 0);

                    gridPane.add(new Label(String.valueOf(tag.getId())), 0, 1);
                    gridPane.add(new Label(tag.getNom()), 1, 1);
                    gridPane.add(new Label(tag.getDescription()), 2, 1);

                    HBox actionButtons = new HBox(6);
                    Button deleteButton = new Button("Delete");
                    deleteButton.setOnAction(event -> deleteTag(tag));
                    actionButtons.getChildren().add(deleteButton);

                    gridPane.add(actionButtons, 3, 1);

                    setGraphic(gridPane);
                }
            }
        });
    }

    private void populateListView() {
        TagService tagService = new TagService();
        try {
            tagList.addAll(tagService.selectAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteTag(Tag tag) {
        TagService tagService = new TagService();
        try {
            tagService.deleteOne(tag);
            tagList.clear();
            populateListView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
