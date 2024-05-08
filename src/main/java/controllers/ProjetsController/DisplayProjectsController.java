package controllers.ProjetsController;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.projets;
import javafx.fxml.FXML;
import services.ProjetsServices;
import javafx.util.Duration;
import utils.NavigationUtil;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Duration;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DisplayProjectsController {

    @FXML
    private Button buttonReturn;

    private projets selectedProject;

    private int categoryId;

    @FXML
    private VBox ProjectVBox;
    private final ProjetsServices projectService = new ProjetsServices(); // Assuming you have a ProjectService object

    private int ajouterProjetCategoryId;

    private int currentPage = 1;
    private final int projectsPerPage = 3; // Number of projects to display per page

    // Add pagination buttons
    @FXML
    private Button prevButton;

    @FXML
    private Button nextButton;




    public void setAjouterProjetCategoryId(int categoryId) {
        this.ajouterProjetCategoryId = categoryId;
    }


    @FXML
    public void displayProjectsByCategory(int categoryId) {
        List<projets> projects = projectService.getProjectsByCategory(categoryId);

        ProjectVBox.getChildren().clear();

        // Check if the list of projects is empty
        if (projects.isEmpty()) {
            // Create and configure a custom alert
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Projects Found");
            alert.setHeaderText(null);
            alert.setContentText("There are no projects in this category.");

            // Set custom icon (optional)
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/imges/icons/boy-avater.png")));

            // Customize alert animation
            fadeInTransition(alert.getDialogPane());

            // Show the alert
            alert.showAndWait();
        } else {
            // Display projects
            displayProjects(projects, categoryId);
        }
    }

    private void displayProjects(List<projets> projects, int categoryId) {
        Pagination pagination = new Pagination((int) Math.ceil(projects.size() / 3.0), 0);
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                // Create an HBox to contain the projects for the current page
                HBox projectsHBox = new HBox(20); // Set spacing between nodes
                projectsHBox.setAlignment(Pos.CENTER); // Align nodes to the center

                // Calculate the range of projects to display for the current page
                int startIndex = pageIndex * 3;
                int endIndex = Math.min(startIndex + 3, projects.size());

                // Iterate through projects for the current page and add them to the HBox
                for (int i = startIndex; i < endIndex; i++) {
                    projets project = projects.get(i);

                    // Create a custom UI component to represent each project
                    Node projectNode = createProjectNode(project, categoryId);

                    // Add an event handler to navigate to project details when the node is clicked
                    projectNode.setOnMouseClicked(event -> {
                        navigateToProjectDetails(project, categoryId);
                    });

                    projectsHBox.getChildren().add(projectNode);
                }

                return projectsHBox;
            }
        });

        ProjectVBox.getChildren().add(pagination);
    }

    private void fadeInTransition(Node node) {
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }






    private void navigateToProjectDetails(projets project, int categoryId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Client/Projets/DetailsProject.fxml"));
            Parent root = loader.load();

            DetailsProjectController controller = loader.getController();
            controller.displayDetailsProject(project.getId(),categoryId);

            ProjectVBox.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        displayProjectsByCategory(categoryId);
    }


    private VBox createProjectNode(projets project, int categoryId) {
        System.out.println("Creating project node for project ID: " + project.getId());
        // Create a VBox to contain the project details
        VBox projectNode = new VBox(10);
        projectNode.setAlignment(Pos.CENTER);

        // ImageView for project image
        ImageView imageView = new ImageView();
        imageView.setFitWidth(300);
        imageView.setFitHeight(300);

        try {
            FileInputStream fileInputStream = new FileInputStream(project.getPhotoUrl());
            Image image = new Image(fileInputStream);
            imageView.setImage(image);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Label for project name
        Label nameLabel = new Label(project.getNom());
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        // Label for project description
        Label descriptionLabel = new Label(project.getDescription());
        descriptionLabel.setFont(Font.font("Arial", 12));
        descriptionLabel.setWrapText(true);

        // HBox to contain the buttons with icons
        HBox iconsHBox = new HBox(10);
        iconsHBox.setAlignment(Pos.CENTER);

        Button deleteButton = new Button();
        ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/imges/icons/delete.png")));
        deleteIcon.setFitWidth(20);
        deleteIcon.setFitHeight(20);
        deleteButton.setGraphic(deleteIcon);

        // Set action for delete button
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Delete button clicked for project ID: " + project.getId()); // Add this line for debugging
                deleteProject(project.getId(), categoryId); // Call deleteProject method with project ID and category ID
            }
        });

        Button modifyButton = new Button();
        ImageView modifyIcon = new ImageView(new Image(getClass().getResourceAsStream("/imges/icons/modify.png")));
        modifyIcon.setFitWidth(20);
        modifyIcon.setFitHeight(20);
        modifyButton.setGraphic(modifyIcon);
        modifyButton.setOnAction(event -> {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Client/Projets/updateProjet.fxml"));
                Parent root = loader.load();

                // Get the controller for the loaded ModifyProject.fxml file
                ModifierProjectController modifyController = loader.getController();

                // Pass the project ID and category ID to the ModifyProjectController
                modifyController.initData(project.getId(), categoryId);

                // Set the root of the scene to the loaded FXML file
                ProjectVBox.getScene().setRoot(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Button detailsButton = new Button();
        ImageView detailsIcon = new ImageView(new Image(getClass().getResourceAsStream("/imges/icons/deatils.png")));
        detailsIcon.setFitWidth(20);
        detailsIcon.setFitHeight(20);
        detailsButton.setGraphic(detailsIcon);
        detailsButton.setOnAction(event -> {
            // Handle details action
        });

        // Add buttons to the HBox
        iconsHBox.getChildren().addAll(deleteButton, modifyButton, detailsButton);

        // Add all components to the VBox
        projectNode.getChildren().addAll(imageView, nameLabel, descriptionLabel, iconsHBox);

        // Apply entry animation
        applyEntryAnimation(projectNode);

        return projectNode;
    }

    private void deleteProject(int project_id,int categoryId) {
        try {
            projectService.supprimerProjet(project_id);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        displayProjectsByCategory(categoryId);
    }



    private void applyEntryAnimation(Node node) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), node);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), node);
        translateTransition.setFromY(-90);
        translateTransition.setToY(20);
        fadeTransition.play();
        translateTransition.play();
    }


    @FXML
    public void naviguezVersAjouterProjets(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Client/Projets/AjouterProjet.fxml"));
            Parent root = loader.load();

            AjouterProjetController controller = loader.getController();
            controller.setSelectedCategoryID(ajouterProjetCategoryId);

            // Set the root of the scene to the loaded FXML file
            ProjectVBox.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    @FXML
    void naviguezVersAcceuil(ActionEvent event) {


        try {
            NavigationUtil.navigateTo("/fxml/Client/Categories/DisplayCategories.fxml", ((Node) event.getSource()).getScene().getRoot());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
