package controllers.ProjetsController;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.projets;
import services.CategoryService;
import services.ProjetsServices;
import models.category;
import utils.FileUtils;

public class AjouterProjetController implements Initializable {

    @FXML
    private TextField Nom;

    private final ProjetsServices projectService = new ProjetsServices(); // Assuming you have a ProjectService object


    @FXML
    private TextField description;

    @FXML
    private TextField wallet_address;

    @FXML
    private ImageView imageView;

    private final ProjetsServices es = new ProjetsServices();
    private final CategoryService cs = new CategoryService();

    private String imgName;

    private category selectedCategory;

    private int selectedCategoryId;

    private int categoryId; // Variable to hold the category ID

    // Setter method for categoryId
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }


    private int project_id;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void setSelectedCategoryID(int categoryId) {
        this.categoryId = categoryId;
    }




    @FXML
    public void navigeuzVersAffichercategories(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Client/Categories/DisplayCategories.fxml"));
            // The above path should be adjusted based on the actual location of AfficherCategories.fxml
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }



    @FXML
    public void Ajouter() {




        String name = Nom.getText().trim();
        String desc = description.getText().trim();
        String wallet = wallet_address.getText().trim();
        String photoUrl = imgName;


        if (name.isEmpty() || desc.isEmpty() || wallet.isEmpty()) {
            showAlert("Empty Fields", "Please fill in all the fields.");
        } else {
            // Set the current date
            Date dateDeCreation = new Date();



            // Create the projet object with the retrieved values
            projets project = new projets(0, name, desc, wallet, dateDeCreation, photoUrl, categoryId);

            // Insert the project
            es.insererProjet(project);

            // Navigate to the desired page
            navigateToAfficher();
        }
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void navigateToAfficher() {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Client/Projets/DisplayProjects.fxml"));
            Parent root = loader.load();

            // Get the controller
            DisplayProjectsController controller = loader.getController();
            controller.displayProjectsByCategory(categoryId);


            // Set the scene
            Scene scene = new Scene(root);
            Stage stage = (Stage) Nom.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            // Get the path of your source directory
            String sourceDirectory = System.getProperty("user.dir");

            sourceDirectory = sourceDirectory + "/uploadedimage";

            // Define a new directory within your source directory to save images
            String destinationDirectory = sourceDirectory + File.separator + "img";

            // Create the directory if it doesn't exist
            File destDir = new File(destinationDirectory);
            if (!destDir.exists()) {
                destDir.mkdirs();
            }

            String fileName = file.getName();
            Path destinationPath = new File(destinationDirectory, fileName).toPath();
            try {
                // Save the file to the destination directory
                Files.copy(file.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File uploaded successfully to: " + destinationPath);

                // Set the image view
                imgName = destinationPath.toString();
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);
            } catch (IOException e) {
                System.out.println("Error uploading file: " + e.getMessage());
                showAlert("Error", "Error uploading file: " + e.getMessage());
            }
        } else {
            System.out.println("No file selected.");
        }
    }
}
