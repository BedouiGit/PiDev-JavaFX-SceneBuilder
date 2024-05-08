package controllers.CategoryController;

import controllers.ProjetsController.DisplayProjectsController;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;
import javafx.util.Duration;
import models.category;
import services.CategoryService;
import utils.NavigationUtil;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AffichageCategoriesController {

    private ObservableList<category> observableList;
    private final CategoryService cs = new CategoryService();

    @FXML
    private ListView<category> listviewcategories;
    @FXML
    private Button buttonReturn;

    @FXML
    private VBox categoryVBox;

    @FXML
    private TextField searchField;


    @FXML
    void initialize() {
        List<category> allCategories = cs.afficher();
        observableList = FXCollections.observableList(allCategories);
        displayCategories(allCategories); // Display all categories initially

        // Add listener to the search field to dynamically filter categories
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Filter categories based on the search input
            filterCategories(newValue.trim().toLowerCase(), allCategories);
        });
    }




    @FXML
    public void naviguezVersAjoutercategory(ActionEvent event) {
        try {
            NavigationUtil.navigateTo("/fxml/Client/Categories/CreateCategory.fxml", ((Node) event.getSource()).getScene().getRoot());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void naviguezVersAcceuil(ActionEvent event) {
        try {
            NavigationUtil.navigateTo("/fxml/Client/Home/Homepage.fxml", ((Node) event.getSource()).getScene().getRoot());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayCategories(List<category> categories) {
        // Create a final reference to the categories list for use inside lambda expression
        final List<category> finalCategories = new ArrayList<>(categories);

        // If no categories are provided, retrieve them from the database
        if (finalCategories.isEmpty()) {
            finalCategories.addAll(cs.afficher());
        }

        // Clear any existing items in the VBox
        categoryVBox.getChildren().clear();

        // Create a TextField for searching categories
        TextField searchField = new TextField();
        //searchField.setPromptText("Search categories...");

        // Add a listener to the search field to dynamically filter categories
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterCategories(newValue, finalCategories);
        });

        // Add the search field to the VBox
        categoryVBox.getChildren().add(searchField);

        // Create a Pagination control
        Pagination pagination = new Pagination((int) Math.ceil(finalCategories.size() / 3.0), 0);
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                // Create a GridPane to organize the layout
                GridPane gridPane = new GridPane();
                gridPane.setHgap(20); // Set horizontal gap between columns
                gridPane.setVgap(10); // Set vertical gap between rows

                // Set constraints to make the categories larger
                ColumnConstraints colConstraints = new ColumnConstraints();
                colConstraints.setPercentWidth(100 / 3); // Divide the width equally among 3 columns
                gridPane.getColumnConstraints().addAll(colConstraints, colConstraints, colConstraints); // Apply constraints to all columns

                // Calculate the range of categories to display for the current page
                int startIndex = pageIndex * 3;
                int endIndex = Math.min(startIndex + 3, finalCategories.size());

                // Iterate through categories for the current page and add components to the GridPane
                int column = 0;
                int row = 0;
                for (int i = startIndex; i < endIndex; i++) {
                    category cat = finalCategories.get(i);

                    // Create BorderPane to contain category elements
                    BorderPane categoryPane = createCategoryPane(cat);

                    // Add the category pane to the GridPane
                    gridPane.add(categoryPane, column, row);

                    // Increment column to move to the next position
                    column++;

                    // Check if the current column exceeds the maximum number of columns (e.g., 3)
                    if (column >= 3) {
                        // Move to the next row and reset column
                        row++; // Adjust based on the number of rows each category occupies
                        column = 0;
                    }
                }

                return gridPane;
            }
        });

        // Add the Pagination control to the VBox
        categoryVBox.getChildren().add(pagination);
    }

    // Method to create a BorderPane for each category
    private BorderPane createCategoryPane(category cat) {
        // Create BorderPane to contain category elements
        BorderPane categoryPane = new BorderPane();
        categoryPane.setPrefSize(600, 700);

        // Create StackPane to contain the image
        StackPane imageStackPane = new StackPane();

        // Create ImageView to display category image
        ImageView imageView = new ImageView();
        imageView.setFitWidth(300); // Set image width
        imageView.setFitHeight(300); // Set image height

        try {
            // Load image from file using FileInputStream
            FileInputStream fileInputStream = new FileInputStream(cat.getPhotoUrl());
            Image image = new Image(fileInputStream);
            imageView.setImage(image);
            fileInputStream.close(); // Close the stream after loading the image
        } catch (IOException e) {
            // Handle image loading errors
            e.printStackTrace();
            // Optionally, set a default image or leave imageView without an image
        }

        // Add image to the imageStackPane
        imageStackPane.getChildren().add(imageView);

        // Apply drop shadow effect to the imageStackPane
        DropShadow dropShadow = new DropShadow();
        imageStackPane.setEffect(dropShadow);

        // Create Label to display category name
        Label nameLabel = new Label(cat.getNom());
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14)); // Set font and size

        // Create Label to display category description
        Label descriptionLabel = new Label(cat.getDescription());
        descriptionLabel.setFont(Font.font("Arial", 12)); // Set font and size
        descriptionLabel.setWrapText(true); // Enable text wrapping

        // Create VBox to contain the name and description labels
        VBox labelsVBox = new VBox(5); // Set spacing between labels
        labelsVBox.setAlignment(Pos.CENTER); // Align labels to the center
        labelsVBox.setPadding(new Insets(5)); // Add padding to the VBox

        // Add name and description labels to the VBox
        labelsVBox.getChildren().addAll(nameLabel, descriptionLabel);

        // Set the imageStackPane at the top, and labelsVBox at the bottom of the categoryPane
        categoryPane.setTop(imageStackPane);
        categoryPane.setCenter(labelsVBox);

        // Apply entry animation to the category pane
        applyEntryAnimation(categoryPane);

        // Add event handler to navigate to details page when category is clicked
        categoryPane.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Check for single click
                navigateToDetailsPage(cat); // Navigate to details page passing the selected category
            }
        });

        return categoryPane;
    }

    private void navigateToDetailsPage(category cat) {
        try {
            // Load the DisplayProjects.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Client/Projets/DisplayProjects.fxml"));
            Parent root = loader.load();

            // Get the controller for the loaded DisplayProjects.fxml file
            DisplayProjectsController displayController = loader.getController();

            // Call the method to display projects by category
            displayController.displayProjectsByCategory(cat.getId());

            // Set the category ID in the AjouterProjetController directly from DisplayProjectsController
            displayController.setAjouterProjetCategoryId(cat.getId());

            // Set the root of the scene to the loaded DisplayProjects.fxml file
            buttonReturn.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    // Method to apply entry animation to the category pane
    private void applyEntryAnimation(Node node) {
        // Fade in animation
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), node);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        // Slide in animation
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), node);
        translateTransition.setFromY(-50);
        translateTransition.setToY(0);

        // Play animations
        fadeTransition.play();
        translateTransition.play();
    }
    private void filterCategories(String keyword, List<category> allCategories) {
        // Use a local final variable to ensure it's effectively final
        final List<category> categories = new ArrayList<>(allCategories);

        List<category> filteredCategories = new ArrayList<>();

        // Iterate through all categories and check if they match the search keyword
        for (category cat : categories) {
            // Check if the category name or description contains the keyword (case-insensitive)
            if (cat.getNom().toLowerCase().contains(keyword) ||
                    cat.getDescription().toLowerCase().contains(keyword)) {
                filteredCategories.add(cat);
            }
        }

        // Display the filtered categories
        displayCategories(filteredCategories);
    }
}
