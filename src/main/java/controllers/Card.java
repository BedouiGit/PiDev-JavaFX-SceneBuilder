package controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.NFT;
import models.NFT;
import services.NFTService;
import java.util.List;

public class Card {

        @FXML
        private HBox CardBoxID;

        @FXML
        private ImageView CardImage;

        @FXML
        private Label CardNftName;

        @FXML
        private Label CardNftPrice;

        private final NFTService ps = new NFTService();

        private String[] colors = {"B9E5FF","BDB2FE","FB9AA8","FF5056"};

    public void setData(NFT nft) {
        String imagePath = "file://C:/Users/Khalil/Desktop/integration java/JavaFX-Login/" + nft.getImage();
        Image image = new Image(imagePath);
        CardImage.setImage(image);
        CardNftName.setText(nft.getName());
        CardNftPrice.setText(String.format("$%.2f", nft.getPrice()));
        CardBoxID.setStyle("-fx-background-color: #"+ colors[(int)(Math.random()*colors.length)] +";"+
                " -fx-background-radius: 15;" +
                " -fx-effect: dropShadow(three-pass-box, rgba(0,0,0,0), 10, 0, 0, 10);");
    }


}

