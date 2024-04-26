package controllers.CategoryController;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.category;
import services.CategoryService;

import java.util.List;

public class displayCategoriesController {

    @FXML
    private Button btnSuivant;

    @FXML
    private Button btnRetour;

    @FXML
    private VBox categoryContainer;

    @FXML
    private AnchorPane BOX1;

    @FXML
    private AnchorPane BOX2;

    @FXML
    private AnchorPane BOX3;

    @FXML
    private Text nom1;

    @FXML
    private Text nom2;

    @FXML
    private Text nom3;

    @FXML
    private Text adresse1;

    @FXML
    private Text adresse2;

    @FXML
    private Text adresse3;

    @FXML
    private ImageView Img1;

    @FXML
    private ImageView Img2;

    @FXML
    private ImageView Img3;

    private final CategoryService cs = new CategoryService();

    private boolean imagesEmpty = false;

    @FXML
    void initialize() {
        actualise();
    }

    void actualise() {
        List<category> categories = cs.afficher();
        int i = 0;
        if (!categories.isEmpty()) {
            if (categories.size() - 1 - i * 3 >= 0) {
                BOX1.setVisible(true);
                category category1 = categories.get(i * 3);
                nom1.setText(category1.getNom());
                adresse1.setText(category1.getDescription());
                Img1.setImage(new Image(category1.getPhotoUrl()));
            } else {
                BOX1.setVisible(false);
            }

            if (categories.size() - 2 - i * 3 >= 0) {
                BOX2.setVisible(true);
                category category2 = categories.get(1 + i * 3);
                nom2.setText(category2.getNom());
                adresse2.setText(category2.getDescription());
                Img2.setImage(new Image(category2.getPhotoUrl()));
            } else {
                BOX2.setVisible(false);
            }

            if (categories.size() - 3 - i * 3 >= 0) {
                BOX3.setVisible(true);
                category category3 = categories.get(2 + i * 3);
                nom3.setText(category3.getNom());
                adresse3.setText(category3.getDescription());
                Img3.setImage(new Image(category3.getPhotoUrl()));
            } else {
                BOX3.setVisible(false);
            }
        } else {
            BOX1.setVisible(false);
            BOX2.setVisible(false);
            BOX3.setVisible(false);
        }

        btnSuivant.setVisible(categories.size() - 3 * i > 3);
    }
}
