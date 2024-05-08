package controllers.CategoryController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Role;
import models.category;
import services.CategoryService;
import utils.NavigationUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DisplayCategoriesAdmin {

    private final CategoryService cs = new CategoryService();
    @FXML
    private TableColumn<category, String> name;

    private category selectedCategory;
    @FXML
    private TableColumn<category, Integer> id;
    @FXML
    private TableColumn<category, String> description;
    @FXML
    private TableColumn<category, String> photo_url;
    private ObservableList<category> categoryList;
    @FXML
    private TextField searchField;

    @FXML
    private Button refreshButton;
    @FXML
    private Button suppbutton;
    @FXML
    private Button modifierbutton;
    @FXML
    private TableView<category> tableView;

    public void initData(Role rolee)
    {
        CategoryService cs = new CategoryService();
        List<category> all = cs.afficher();
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("nom"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        photo_url.setCellValueFactory(new PropertyValueFactory<>("photo_url"));


        ObservableList<category> categoryList = FXCollections.observableArrayList(all);
        // Set data to table view
        tableView.setItems(categoryList);
        populateTableView();

    }

    private void populateTableView() {
        CategoryService categoryService = new CategoryService();
        categoryList = FXCollections.observableArrayList(categoryService.afficher());
        tableView.setItems(categoryList);
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
                selectedCategory = tableView.getSelectionModel().getSelectedItem();
                if (selectedCategory != null) {
                    openModificationForm();
                }
            }
        });
        // Ajout d'un gestionnaire d'événements au bouton "Supprimer"
        suppbutton.setOnAction(event -> {
            try {
                deleteCategory();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        modifierbutton.setOnAction(event -> {
            if (selectedCategory != null) {
                openModificationForm();
            } else {
                // Si aucun utilisateur n'est sélectionné, affichez un message d'avertissement
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No category selected\n");
                alert.setHeaderText(null);
                alert.setContentText("Please select a category to edit.\n");
                alert.showAndWait();
            }
        });
    }
    private void openModificationForm() {
        try {
            // Charger le formulaire de modification depuis le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/editUser.fxml"));
            Parent modificationInterface = loader.load();

            // Passer l'utilisateur sélectionné au contrôleur du formulaire de modification
            ModifierCategoryController modificationController = loader.getController();
            modificationController.initData(selectedCategory);

            // Créer une nouvelle fenêtre pour la modification et afficher le formulaire pré-rempli
            Stage modificationStage = new Stage();
            modificationStage.setScene(new Scene(modificationInterface));
            modificationStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadCategory() {
        // Utiliser le service UserService pour récupérer la liste des utilisateurs depuis la base de données
        CategoryService categoryService = new CategoryService();
        ObservableList<category> cat = FXCollections.observableArrayList(categoryService.afficher());

        // Mettre à jour la liste des utilisateurs de la TableView
        categoryList.clear(); // Nettoyer la liste existante
        categoryList.addAll(cat); // Ajouter les nouveaux utilisateurs
    }

    // Méthode pour rafraîchir la TableView
    public void refreshTableView() {
        loadCategory();
        // Actualiser la TableView pour refléter les changements
        tableView.refresh();
    }
    private void deleteCategory() throws SQLException {
        // Récupérer l'élément sélectionné dans le TableView
        category selectedCategory = tableView.getSelectionModel().getSelectedItem();

        // Vérifier si un élément est sélectionné
        if (selectedCategory != null) {
            // Afficher une boîte de dialogue de confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deletion confirmation\n");
            alert.setHeaderText("Confirm deletion\n");
            alert.setContentText("Are you sure you want to delete this category\n ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                CategoryService categoryService = new CategoryService();
                categoryService.supprimer(selectedCategory);
                // Supprimer l'utilisateur de la liste des données
                categoryList.remove(selectedCategory);

                // Supprimer l'utilisateur de la base de données en appelant votre méthode deleteCodePromo(selectedCategory.getId())

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
    void navigateToAddCategory(ActionEvent event ){
        try {
            NavigationUtil.navigateTo("/fxml/Admin/AddCategoryAdmin.fxml", ((Node) event.getSource()).getScene().getRoot());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void searchUsers(String query) {
        query = query.trim().toLowerCase();
        if (query.isEmpty()) {
            loadCategory(); // Reload all users if the search query is empty
        } else {
            // Filter users based on the search query
            String finalQuery = query;
            ObservableList<category> filteredcat = categoryList.filtered(u ->
                    u.getNom().toLowerCase().contains(finalQuery) ||
                            u.getDescription().toLowerCase().contains(finalQuery) ||
                            u.getPhotoUrl().toLowerCase().contains(finalQuery));
            tableView.setItems(filteredcat);
        }
    }
}
