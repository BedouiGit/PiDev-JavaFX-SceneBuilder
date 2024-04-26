package controllers.ProjetsController;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import models.category;
import models.projets;

public class ModifierProjectController {

    @FXML
    private TextField coursurl;

    @FXML
    private TextField niveau;

    @FXML
    private TextField nomcour;

    @FXML
    private TextField nommodule;
    private models.projets projets;
    @FXML
    public void setCours(projets projets) {
        this.projets = projets;
        coursurl.setText(projets.getPhotoUrl());
        niveau.setText(projets.getDescription());
        nomcour.setText(projets.getNom());

    }
}
