package controllers;

import controllers.UserController.EditUser;
import controllers.UserController.ListUsers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Role;
import controllers.UserController.addUserByAdmin;
import models.user;
import services.userExcel;
import services.userService;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class DashboardAdmin {
    @FXML
    private Button add;

    @FXML
    private PieChart pieChart;

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private TableColumn<user, String> address1;
    @FXML
    private TableColumn<user, String>sexe;

    @FXML
    private TableColumn<user, Integer> age;

    @FXML
    private TableColumn<user, String> email;

    @FXML
    private TableColumn<user, String> first_name;

    @FXML
    private TableColumn<user, Boolean> is_banned;
    @FXML
    private TableColumn<user, String> last_name;
    @FXML
    private TableColumn<user, Integer> tel1;
    @FXML
    private TableView<user> tableView;
    private ObservableList<user> userList;
    @FXML
    private TextField searchField;

    @FXML
    private Button refreshButton;
    @FXML
    private Button suppbutton;
    @FXML
    private Button modifierbutton;
    private user selectedUser;
    public void initData(Role rolee) {
        userService us = new userService();
        List<user> all = us.getAll();
        address1.setCellValueFactory(new PropertyValueFactory<>("address"));
        age.setCellValueFactory(new PropertyValueFactory<>("age"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        first_name.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        is_banned.setCellValueFactory(new PropertyValueFactory<>("banned"));
        last_name.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        tel1.setCellValueFactory(new PropertyValueFactory<>("tel"));
        sexe.setCellValueFactory(new PropertyValueFactory<>("gender"));

        // Convert List<user> to ObservableList<User>
        ObservableList<user> usersList = FXCollections.observableArrayList(all);
        // Set data to table view
        tableView.setItems(usersList);
        populateTableView();

    }
    // Méthode pour initialiser le TableView avec les données de la base de données
    private void populateTableView() {
        userService userService = new userService();
        userList = FXCollections.observableArrayList(userService.getAll());
        tableView.setItems(userList);
        // Associer les propriétés des utilisateurs aux colonnes de TableView
    }
    @FXML
    void refreshButtonAction(ActionEvent event) {
        refreshTableView();
    }

    @FXML
    public void initialize() {
        refreshButton.setOnAction(this::refreshButtonAction);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchUsers(newValue);
        });
        // Initialisation du TableView avec les données de la base de données
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Vérifiez si c'est un double clic
                selectedUser = tableView.getSelectionModel().getSelectedItem();
                if (selectedUser != null) {
                    openModificationForm();
                }
            }
        });

        // Ajout d'un gestionnaire d'événements au bouton "Supprimer"
        suppbutton.setOnAction(event -> {
            deleteUser();
        });
        modifierbutton.setOnAction(event -> {
            if (selectedUser != null) {
                openModificationForm();
            } else {
                // Si aucun utilisateur n'est sélectionné, affichez un message d'avertissement
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No user selected\n");
                alert.setHeaderText(null);
                alert.setContentText("Please select a user to edit.\n");
                alert.showAndWait();
            }
        });
    }
    private void openModificationForm() {
        try {
            // Charger le formulaire de modification depuis le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UserFxml/editUser.fxml"));
            Parent modificationInterface = loader.load();

            // Passer l'utilisateur sélectionné au contrôleur du formulaire de modification
            EditUser modificationController = loader.getController();
            modificationController.initData(selectedUser);

            // Créer une nouvelle fenêtre pour la modification et afficher le formulaire pré-rempli
            Stage modificationStage = new Stage();
            modificationStage.setScene(new Scene(modificationInterface));
            modificationStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadUsers() {
        // Utiliser le service UserService pour récupérer la liste des utilisateurs depuis la base de données
        userService userService = new userService();
        ObservableList<user> users = FXCollections.observableArrayList(userService.getAll());

        // Mettre à jour la liste des utilisateurs de la TableView
        userList.clear(); // Nettoyer la liste existante
        userList.addAll(users); // Ajouter les nouveaux utilisateurs
    }

    // Méthode pour rafraîchir la TableView
    public void refreshTableView() {
        loadUsers();
        // Actualiser la TableView pour refléter les changements
        tableView.refresh();
    }
    private void deleteUser() {
        // Récupérer l'élément sélectionné dans le TableView
        user selectedUser = tableView.getSelectionModel().getSelectedItem();

        // Vérifier si un élément est sélectionné
        if (selectedUser != null) {
            // Afficher une boîte de dialogue de confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deletion confirmation\n");
            alert.setHeaderText("Confirm deletion\n");
            alert.setContentText("Are you sure you want to delete this user\n ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                userService userService = new userService();
                userService.deleteUser(selectedUser);
                // Supprimer l'utilisateur de la liste des données
                userList.remove(selectedUser);

                // Supprimer l'utilisateur de la base de données en appelant votre méthode deleteCodePromo(selectedUser.getId())

                // Rafraîchir le TableView
                tableView.refresh();
            }
        } else {
            // Si aucun élément n'est sélectionné, affichez un message d'avertissement
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No items selected\n");
            alert.setHeaderText(null);
            alert.setContentText("Please select a user to delete\n.");
            alert.showAndWait();
        }
    }
    @FXML
    public void searchUsers(String query) {
        query = query.trim().toLowerCase();
        if (query.isEmpty()) {
            loadUsers(); // Reload all users if the search query is empty
        } else {
            // Filter users based on the search query
            String finalQuery = query;
            ObservableList<user> filteredUsers = userList.filtered(u ->
                    u.getFirst_name().toLowerCase().contains(finalQuery) ||
                            u.getLast_name().toLowerCase().contains(finalQuery) ||
                            u.getEmail().toLowerCase().contains(finalQuery) ||
                            u.getAddress().toLowerCase().contains(finalQuery));
            tableView.setItems(filteredUsers);
        }
    }




    public void logout(){
        Stage stage = (Stage) refreshButton.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/UserFxml/Login.fxml"));
            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void addTep()throws IOException  {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UserFxml/addUserByAdmin.fxml"));
        Parent root = loader.load();
        addUserByAdmin addController = loader.getController(); // Get the controller instance associated with the FXML
        Role role = Role.ROLE_USER;
        addController.initData(role); // Call initData on the correct controller instance
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();


    }


    public void openUsersList(ActionEvent actionEvent)throws IOException  {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UserFxml/ListUsers.fxml"));
        Parent root = loader.load();
        ListUsers addController = loader.getController(); // Get the controller instance associated with the FXML
        Role role = Role.ROLE_USER;
        addController.initData(role); // Call initData on the correct controller instance
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
    }

    @FXML
    public void Stat(ActionEvent actionEvent)throws IOException  {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UserFxml/stat.fxml"));
        Parent root = loader.load();
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
    }
    @FXML
    void excel(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("Users.xlsx");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());
        if (file != null) {
            String filePath = file.getAbsolutePath();
            userExcel.exportToExcel(tableView, filePath);
        }
    }



}
