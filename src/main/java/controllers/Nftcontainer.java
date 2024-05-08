package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.NFT;

import java.io.IOException;

public class Nftcontainer {


    @FXML
    private ImageView nftimage;

    @FXML
    private Label nftname;

    @FXML
    private Label nftprice;
    private NFT nft;

    public void setData(NFT nft){

        this.nft = nft;
        String imagePath = "file:///C:/Users/Khalil/Desktop/integration java/JavaFX-Login/" + nft.getImage();
        Image image = new Image(imagePath);
        nftimage.setImage(image);
        nftname.setText(nft.getName());
        nftprice.setText(String.format("$%.2f", nft.getPrice()));

//       CardBoxID.setStyle("-fx-background-color: #"+ colors[(int)(Math.random()*colors.length)] +";"+
//              " -fx-background-radius: 15;" +
//             " -fx-effect: dropShadow(three-pass-box, rgba(0,0,0,0), 10, 0, 0, 10);");


    }
    @FXML
    private void buynowbutton(ActionEvent event) {
        openCheckoutForm(this.nft);
    }

    private void openCheckoutForm(NFT nft) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CheckoutForm.fxml"));
            Parent root = loader.load();

            CheckoutForm controller = loader.getController();
            controller.initData(nft);

            Stage stage = new Stage();
            stage.setTitle("Checkout");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
