package controllers;

import entities.actualite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import services.ActualiteService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class DisplayActualiteController implements Initializable {
    public Button RemoveButton;
    public Button goToRes;
    @FXML
    private ListView<actualite> eventsView;
    @FXML
    private Button modifyEvBtn;
    private final ActualiteService ES1 = new ActualiteService();


    //ObservableList<event> EV = FXCollections.observableArrayList();
    ObservableList<actualite> actualites;

    {
        try {
            actualites = FXCollections.observableArrayList(ES1.recuperer());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        // ObservableList<event> events = FXCollections.observableArrayList(ES1.afficher());
        // ObservableList<event> events = FXCollections.observableArrayList(ES1.afficher());
        //  EventTable.setItems(events);
        // eventsView.getItems().addAll(ES1.afficher());
        //eventsView.setCellFactory(eventListView -> new ListCell<event>());
        try {
            eventsView.getItems().addAll(ES1.recuperer());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

// Set custom cell factory for better layout
        eventsView.setCellFactory(eventListView -> new ListCell<actualite>() {
            @Override
            protected void updateItem(actualite item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    // Customize how each item is displayed in the ListView
                    setText("Titre: " + item.getTitre() + ", "
                            + "Contenu: " + item.getContenu() + ", "
                            + "Categorie: " + item.getCategorie() + ", "
                            + "Date de publication: " + item.getDatePublication() + ", "
                            + "Image URL: " + item.getImageUrl());

                }
            }
        });


    }

    @FXML
    void RemoveEvent(ActionEvent actualite) {
        try {
            actualite selectedEvent = eventsView.getSelectionModel().getSelectedItem(); // Get selected event from ListView

            ES1.supprimer(selectedEvent);

            actualites.clear();

            actualites.addAll(ES1.recuperer());

            eventsView.setItems(actualites);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        @FXML
        void modifyEventBtn (ActionEvent actualite){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifyNews.fxml"));
            try {
                Parent root = loader.load();
                eventsView.getScene().setRoot(root);


            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        @FXML
        void goToResBtn (ActionEvent actualite){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/displayNews.fxml"));
            try {
                Parent root = loader.load();
                eventsView.getScene().setRoot(root);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

}
