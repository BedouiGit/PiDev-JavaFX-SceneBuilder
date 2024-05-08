package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;
import models.NFT;
import services.NFTService;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FrontNFT {

    @FXML
    private HBox cardlayout;

    @FXML
    private GridPane nftcontainer;

    private List<NFT> nfts , topnfts;

    @FXML
    void initialize() {
        nfts = new ArrayList<>(NFTlist());
        topnfts = new ArrayList<>(topnfts());

        int column=0;
        int row=1;

        if (nfts.isEmpty()) {
            System.out.println("No NFTs found. Please check the data source.");
            return; // Exit if no NFTs to display
        }

        try {
            for (NFT nft : topnfts) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/Card.fxml"));
                HBox cardbox = loader.load();
                Card card = loader.getController();
                card.setData(nft);
                cardlayout.getChildren().add(cardbox);
                System.out.println("Added card for: " + nft.getName());
            }
            for (NFT nft : nfts) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/nftcontainer.fxml"));
                VBox cardbox = loader.load();
                Nftcontainer nftcontainer = loader.getController();
                nftcontainer.setData(nft);

                if(column == 6){
                    column = 0;
                    ++row;
                }

                this.nftcontainer.add(cardbox, column++, row);
                GridPane.setMargin(cardbox, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<NFT> topnfts() {
        NFTService nftService = new NFTService();
        return nftService.getAll(200);
    }
    private List<NFT> NFTlist() {
        NFTService nftService = new NFTService();
        return nftService.getAll();
    }
}
