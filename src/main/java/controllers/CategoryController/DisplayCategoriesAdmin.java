package controllers.CategoryController;

import controllers.BackFormNFT;
import controllers.ProjetsController.DisplayProjectsController;
import controllers.ProjetsController.ModifierProjectController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.util.StringConverter;
import models.projets;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import models.NFT;
import models.category;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import services.CategoryService;
import services.ProjetsServices;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.*;

public class DisplayCategoriesAdmin {

    @FXML
    private TableView<category> tableView;

    @FXML
    private TableColumn<category, String> name;

    @FXML
    private TableColumn<category, String> description;

    @FXML
    private TableColumn<category, String> photo_url;

    @FXML
    private Button btn_workbench111112;

    @FXML
    private Button updateButton;

    @FXML
    private Button Afficher_les_projets;

    @FXML
    private BarChart<String, Number> barChart;


    @FXML
    private Button removeButton;

    @FXML
    private Button printButton;

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> filterComboBox;

    @FXML
    private Button btn_workbench13;

    private category selectedCategory;


    @FXML
    private LineChart<String, Number> lineChartCategory;



    private void populateChartData() {
        CategoryService categoryService = new CategoryService();
        List<category> categories = categoryService.afficher();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (category cat : categories) {
            // Count the number of projects for each category
            int projectCount = cat.getProjets().size();
            series.getData().add(new XYChart.Data<>(cat.getNom(), projectCount));
        }


    }

    @FXML
    void generatePdf(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());

        if (file != null) {
            List<category> categories = tableView.getItems();
            genererPDF(categories, file.getAbsolutePath());
            showAlert("Success", "PDF saved successfully.", Alert.AlertType.INFORMATION);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void nftbackbtn(ActionEvent event) {
        loadFXML("/BackOffice/BackCommande.fxml", event);
    }

    @FXML
    void commandebackbtn(ActionEvent event) {
        loadFXML("/BackOffice/BackCommande.fxml", event);
    }

    private void loadFXML(String fxmlPath, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent parent = loader.load();

            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(parent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void print(ActionEvent event) {
        // Implement printing functionality here if needed
    }

    @FXML
    void addCategory(ActionEvent event) {
        loadFXML("/fxml/Admin/AddCategoryAdmin.fxml", event);
    }

    @FXML
    void removeCategory(ActionEvent event) throws SQLException {
        if (selectedCategory != null) {
            CategoryService sp = new CategoryService();
            sp.supprimer(selectedCategory);
            displayAllCategoriesInTableView();
        }
    }



    @FXML
    void DisplayProjects() {
        if (selectedCategory != null) {
            // Create the custom display projects dialog
            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle("Projects for Category: " + selectedCategory.getNom());
            dialog.setHeaderText("List of Projects:");
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);
// Create a TableView to hold project information and actions
            TableView<projets> tableView = new TableView<>();

            // Define TableColumn instances for project name, description, and actions
            TableColumn<projets, String> nameColumn = new TableColumn<>("Nom");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));

            TableColumn<projets, String> descriptionColumn = new TableColumn<>("Description");
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

            TableColumn<projets, String> wallet_addressColumn = new TableColumn<>("Wallet_address");
            wallet_addressColumn.setCellValueFactory(new PropertyValueFactory<>("wallet_address"));

            TableColumn<projets, String> photo_urlColumn = new TableColumn<>("Photo_url");
            photo_urlColumn.setCellValueFactory(new PropertyValueFactory<>("photoUrl"));


            TableColumn<projets, Void> actionColumn = new TableColumn<>("Actions");
            actionColumn.setCellFactory(param -> new TableCell<>() {
                final Button viewButton = new Button("View");
                final Button editButton = new Button("Edit");
                final Button deleteButton = new Button("Delete");

                {
                    viewButton.setOnAction(event -> {
                        // Handle view action
                        projets project = tableView.getItems().get(getIndex());

                        // Create a custom dialog to display project details
                        Dialog<Void> projectDialog = new Dialog<>();
                        projectDialog.setTitle("Project Details");
                        projectDialog.setHeaderText("Details for Project: " + project.getNom());

                        // Set the button types (Close)
                        projectDialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);

                        // Create a VBox to hold project information
                        VBox vbox = new VBox(10);
                        vbox.setPadding(new Insets(10));

                        // Display project details
                        Label nameLabel = new Label("Name: " + project.getNom());
                        Label descriptionLabel = new Label("Description: " + project.getDescription());
                        Label walletAddressLabel = new Label("Wallet Address: " + project.getWallet_address());
                        Label photoLabel = new Label("Photo URL: " + project.getPhotoUrl());

                        vbox.getChildren().addAll(nameLabel, descriptionLabel, walletAddressLabel, photoLabel);

                        projectDialog.getDialogPane().setContent(vbox);

                        // Show the dialog
                        projectDialog.showAndWait();
                    });

                    editButton.setOnAction(event -> {
                        // Handle edit action
                        projets project = tableView.getItems().get(getIndex());

                        // Create a custom dialog to edit project details
                        Dialog<Void> editDialog = new Dialog<>();
                        editDialog.setTitle("Edit Project");
                        editDialog.setHeaderText("Edit Details for Project: " + project.getNom());

                        // Set the button types (OK and Cancel)
                        editDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

                        // Create a GridPane to hold text fields for editing
                        GridPane gridPane = new GridPane();
                        gridPane.setHgap(10);
                        gridPane.setVgap(10);
                        gridPane.setPadding(new Insets(20, 150, 10, 10));

                        TextField nameField = new TextField(project.getNom());
                        TextField descriptionField = new TextField(project.getDescription());
                        TextField walletAddressField = new TextField(project.getWallet_address());

                        gridPane.add(new Label("Name:"), 0, 0);
                        gridPane.add(nameField, 1, 0);
                        gridPane.add(new Label("Description:"), 0, 1);
                        gridPane.add(descriptionField, 1, 1);
                        gridPane.add(new Label("Wallet Address:"), 0, 2);
                        gridPane.add(walletAddressField, 1, 2);

                        editDialog.getDialogPane().setContent(gridPane);

                        // Convert the result to a simple array when the OK button is clicked
                        editDialog.setResultConverter(dialogButton -> {
                            if (dialogButton == ButtonType.OK) {
                                String newName = nameField.getText();
                                String newDescription = descriptionField.getText();
                                String newWalletAddress = walletAddressField.getText();
                                // Perform action to update project in database or wherever it's stored
                                // For example, you can call a method in ProjetsServices to update the project
                                ProjetsServices projectService = new ProjetsServices();
                                project.setNom(newName);
                                project.setDescription(newDescription);
                                project.setWallet_address(newWalletAddress);
                                projectService.modifierProjet(project);
                            }
                            return null; // Return null in all cases
                        });

                        // Show the dialog
                        editDialog.showAndWait();
                    });


                    deleteButton.setOnAction(event -> {
                        // Handle delete action
                        projets project = tableView.getItems().get(getIndex());

                        // Create a confirmation dialog
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirm Deletion");
                        alert.setHeaderText("Delete Project");
                        alert.setContentText("Are you sure you want to delete the project: " + project.getNom() + "?");

                        // Get the result of the confirmation dialog
                        Optional<ButtonType> result = alert.showAndWait();

                        // Check if the user confirmed the deletion
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            // Perform action to delete project from the database
                            ProjetsServices projectService = new ProjetsServices();
                            projectService.supprimerProjet(project.getId());

                            // Remove the project from the TableView
                            tableView.getItems().remove(project);
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        HBox buttons = new HBox(5);
                        buttons.getChildren().addAll(viewButton, editButton, deleteButton);
                        setGraphic(buttons);
                    }
                }
            });

            // Add columns to TableView
            tableView.getColumns().addAll(nameColumn, descriptionColumn, wallet_addressColumn, photo_urlColumn, actionColumn);

            // Assuming you have a method in your service to fetch projects by category id
            ProjetsServices projectService = new ProjetsServices();
            List<projets> projects = projectService.getProjectsByCategory(selectedCategory.getId());
            tableView.getItems().addAll(projects);

            dialog.getDialogPane().setContent(tableView);

            // Show the dialog
            dialog.showAndWait();
        } else {
            showAlert("Error", "Please select a category to display projects.", Alert.AlertType.ERROR);
        }
    }
    @FXML

    void addProject(ActionEvent event) {
        // Create a custom dialog to add a new project
        Dialog<Void> addDialog = new Dialog<>();
        addDialog.setTitle("Add Project");
        addDialog.setHeaderText("Add a New Project");

        // Set the button types (OK and Cancel)
        addDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Create a GridPane to hold text fields for adding a new project
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField();
        TextField descriptionField = new TextField();
        TextField walletAddressField = new TextField();
        TextField photoUrlField = new TextField();
        ComboBox<category> categoryComboBox = new ComboBox<>(); // Use ComboBox for category selection

        CategoryService categoryService = new CategoryService();
        List<category> categories = categoryService.afficher(); // Assuming you have a method to fetch categories

        // Create a StringConverter to display only category names
        StringConverter<category> categoryStringConverter = new StringConverter<category>() {
            @Override
            public String toString(category category) {
                if (category != null) {
                    return category.getNom(); // Display only the category name if category is not null
                } else {
                    return ""; // Or any default value you prefer when category is null
                }
            }

            @Override
            public category fromString(String string) {
                // Not needed for ComboBox display, but you can implement if necessary
                return null;
            }
        };

        categoryComboBox.setConverter(categoryStringConverter);
        categoryComboBox.setItems(FXCollections.observableArrayList(categories));

        gridPane.add(new Label("Name:"), 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(new Label("Description:"), 0, 1);
        gridPane.add(descriptionField, 1, 1);
        gridPane.add(new Label("Wallet Address:"), 0, 2);
        gridPane.add(walletAddressField, 1, 2);
        gridPane.add(new Label("Photo URL:"), 0, 3);
        gridPane.add(photoUrlField, 1, 3);
        gridPane.add(new Label("Category:"), 0, 4); // Change label to "Category"
        gridPane.add(categoryComboBox, 1, 4); // Add ComboBox for category selection

        // Button for uploading an image
        Button uploadButton = new Button("Upload Image");
        uploadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Image File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
            );
            File selectedFile = fileChooser.showOpenDialog(addDialog.getOwner());

            if (selectedFile != null) {
                // Copy the selected image file to the project directory
                File destDir = new File("uploadedimage/img/");
                if (!destDir.exists()) {
                    destDir.mkdirs(); // Create directory if it doesn't exist
                }
                File destFile = new File(destDir, selectedFile.getName());
                try {
                    Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    // Set the photo URL field to the path of the copied image file
                    photoUrlField.setText(destFile.getAbsolutePath());
                } catch (IOException ex) {
                    ex.printStackTrace();
                    // Handle the exception (e.g., show an error message)
                }
            }
        });

        gridPane.add(uploadButton, 2, 3); // Add the upload button to the grid pane

        addDialog.getDialogPane().setContent(gridPane);

        // Convert the result to a simple array when the OK button is clicked
        addDialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                String name = nameField.getText();
                String description = descriptionField.getText();
                String walletAddress = walletAddressField.getText();
                String photoUrl = photoUrlField.getText();
                category selectedCategory = categoryComboBox.getValue(); // Get the selected category from the ComboBox

                // Get the current date and time
                Date currentDate = new Date(System.currentTimeMillis());

                projets newProject = new projets(0, name, description, walletAddress, currentDate, photoUrl, selectedCategory.getId());
                ProjetsServices projectService = new ProjetsServices();
                projectService.insererProjet(newProject);
            }
            return null; // Return null in all cases
        });

        // Show the dialog
        addDialog.showAndWait();
    }


    @FXML
    void initialize() {
        filterComboBox.getItems().addAll("nom", "description");
        filterComboBox.getSelectionModel().selectFirst();

        // Add event listener to the filter combo box
        filterComboBox.setOnAction(event -> searchCategory(event));

        // Add listener to the text property of the search field for dynamic search
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchCategory(new ActionEvent());
        });

        displayAllCategoriesInTableView();
        setTableActionButtons(true);

        populateChartData();
    }


    @FXML
    void searchCategory(ActionEvent event) {
        String filterBy = filterComboBox.getValue();
        String search = searchField.getText();

        try {
            CategoryService serviceCategories = new CategoryService();
            List<category> categories;
            if (search.isEmpty()) {
                categories = serviceCategories.afficher();
            } else {
                categories = serviceCategories.searchBy(filterBy, search);
            }

            // Sort categories alphabetically by name or description based on the filter
            if ("nom".equals(filterBy)) {
                categories.sort(Comparator.comparing(category::getNom));
            } else if ("description".equals(filterBy)) {
                categories.sort(Comparator.comparing(category::getDescription));
            }

            ObservableList<category> produitObservableList = FXCollections.observableArrayList(categories);

            // Clear existing items from the table view
            tableView.getItems().clear();

            // Set updated items to the table view
            tableView.setItems(produitObservableList);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des personnes : " + e.getMessage());
        }
    }



    public void displayAllCategoriesInTableView() {
        tableView.setOnMouseClicked(this::handleTableViewClick);

        name.setCellValueFactory(new PropertyValueFactory<>("nom"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));

        photo_url.setCellValueFactory(new PropertyValueFactory<>("photoUrl"));
        photo_url.setCellFactory(col -> new TableCell<category, String>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String imagePath, boolean empty) {
                super.updateItem(imagePath, empty);
                if (empty || imagePath == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    try {
                        Image image = new Image(new FileInputStream(imagePath), 50, 50, true, true);
                        if (image.isError()) {
                            setText("Image load failed");
                        } else {
                            imageView.setImage(image);
                            setGraphic(imageView);
                            setText(null);
                        }
                    } catch (FileNotFoundException e) {
                        System.out.println("Error loading image: " + e.getMessage());
                        setText("Image not found");
                    }
                }
            }
        });

        // Assuming CategoryService is correctly implemented
        CategoryService categoryService = new CategoryService();
        List<category> categories = categoryService.afficher();

        if (categories.isEmpty()) {
            System.out.println("No categories found.");
        } else {
            ObservableList<category> categoryObservableList = FXCollections.observableArrayList(categories);
            tableView.setItems(categoryObservableList);
        }
    }


    private void handleTableViewClick(MouseEvent mouseEvent) {
        category selectedCategory = tableView.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            this.selectedCategory = selectedCategory;
            setTableActionButtons(false);
        }
    }

    private void setTableActionButtons(boolean state) {
        removeButton.setDisable(state);
        Afficher_les_projets.setDisable(state);
    }


    public static void genererPDF(List<category> categories, String cheminFichier) {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, page.getMediaBox().getHeight() - 50);
            contentStream.showText("Liste des catégories:");
            contentStream.newLine();
            contentStream.newLine();
            contentStream.endText();

            float tableWidth = page.getMediaBox().getWidth() - 100;
            float yStart = page.getMediaBox().getHeight() - 100;
            float yPosition = yStart;
            final float cellMargin = 5f;
            final float rowHeight = 20f;
            final float tableMargin = 50f;

            String[][] data = new String[categories.size() + 1][2];
            data[0][0] = "Nom";
            data[0][1] = "Description";

            int row = 1;
            for (category cat : categories) {
                data[row][0] = cat.getNom();
                data[row][1] = cat.getDescription();
                row++;
            }

            // Draw the table
            drawTable(contentStream, yStart, tableWidth, tableMargin, cellMargin, rowHeight, data);

            contentStream.close();
            document.save(cheminFichier);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void drawTable(PDPageContentStream contentStream, float yStart, float tableWidth, float tableMargin,
                                  float cellMargin, float rowHeight, String[][] data) throws IOException {
        float yPosition = yStart;
        float tableBottomY = yStart - (rowHeight * data.length);
        float cellWidth = (tableWidth - (2 * tableMargin)) / data[0].length;

        // Draw the rows
        for (String[] row : data) {
            // Draw the cells
            float nextYPosition = yPosition - rowHeight;
            float nextXPosition = tableMargin;
            for (String cell : row) {
                drawCell(contentStream, nextXPosition, yPosition, cellWidth, rowHeight, cellMargin, cell);
                nextXPosition += cellWidth;
            }

            yPosition = nextYPosition;
        }

        // Draw the borders
        float tableTopY = yStart;
        contentStream.moveTo(tableMargin, tableTopY);
        contentStream.lineTo(tableMargin, tableBottomY);
        contentStream.moveTo(tableWidth - tableMargin, tableTopY);
        contentStream.lineTo(tableWidth - tableMargin, tableBottomY);
        for (int i = 0; i <= data.length; i++) {
            contentStream.moveTo(tableMargin, yStart - (i * rowHeight));
            contentStream.lineTo(tableWidth - tableMargin, yStart - (i * rowHeight));
        }
        contentStream.stroke();
    }

    private static void drawCell(PDPageContentStream contentStream, float x, float y, float width, float height,
                                 float margin, String text) throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(x + margin, y - (height / 2));
        contentStream.showText(text);
        contentStream.endText();
    }


    private Image loadImage(String path) {
        try {
            return new Image(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            // Return null or a default image in case of error
            return null;
        }
    }

    @FXML
    void updateCategory(ActionEvent event) {
        if (selectedCategory != null) {
            // Create the custom update dialog
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("Update Category");
            dialog.setHeaderText("Update Category Details:");

            // Set the button types (OK and Cancel)
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            // Create the grid pane and add the text fields
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(20, 150, 10, 10));

            TextField nameField = new TextField(selectedCategory.getNom());
            TextField descriptionField = new TextField(selectedCategory.getDescription());

            Button uploadImageButton = new Button("Upload Image");
            Label imagePathLabel = new Label(selectedCategory.getPhotoUrl());

            uploadImageButton.setOnAction(e -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Choose Image File");
                File file = fileChooser.showOpenDialog(null);
                if (file != null) {
                    String imagePath = file.getAbsolutePath();
                    imagePathLabel.setText(imagePath);
                }
            });

            gridPane.add(new Label("Name:"), 0, 0);
            gridPane.add(nameField, 1, 0);
            gridPane.add(new Label("Description:"), 0, 1);
            gridPane.add(descriptionField, 1, 1);
            gridPane.add(new Label("Image Path:"), 0, 2);
            gridPane.add(imagePathLabel, 1, 2);
            gridPane.add(uploadImageButton, 2, 2);

            dialog.getDialogPane().setContent(gridPane);

            // Convert the result to a pair when the OK button is clicked
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == ButtonType.OK) {
                    return new Pair<>(nameField.getText(), descriptionField.getText());
                }
                return null;
            });

            // Show the dialog and handle the result
            Optional<Pair<String, String>> result = dialog.showAndWait();
            result.ifPresent(pair -> {
                String newName = pair.getKey();
                String newDescription = pair.getValue();
                String newImagePath = imagePathLabel.getText();
                // Update the selected category with the new details
                selectedCategory.setNom(newName);
                selectedCategory.setDescription(newDescription);
                selectedCategory.setPhotoUrl(newImagePath);
                try {
                    // Call the modifier method in CategoryService to update the category in the database
                    CategoryService categoryService = new CategoryService();
                    categoryService.modifier(selectedCategory);
                    // Refresh the UI
                    displayAllCategoriesInTableView();
                    showAlert("Success", "Category updated successfully.", Alert.AlertType.INFORMATION);
                } catch (SQLException e) {
                    showAlert("Error", "Failed to update category: " + e.getMessage(), Alert.AlertType.ERROR);
                }
            });
        } else {
            showAlert("Error", "Please select a category to update.", Alert.AlertType.ERROR);
        }
    }


    @FXML
    public void NavigateToChart(ActionEvent event){
        loadFXML("/fxml/Admin/Chart.fxml", event);
    }

}
