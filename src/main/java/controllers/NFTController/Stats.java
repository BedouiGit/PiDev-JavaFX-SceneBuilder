package controllers.NFTController;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import models.Commande;
import services.CommandeService;

public class Stats implements Initializable {

    @FXML
    private ImageView AIResult;

    private Image image1 = new Image("file:/C:/Users/Admin/Desktop/Java_badbud/nfttun/img/131096-down-arrow-png-free-photo.png");
    private Image image2 = new Image("file:/C:/Users/Admin/Desktop/Java_badbud/nfttun/img/131096-down-arrow-png-free-photo.png");
    private CommandeService sp = new CommandeService();


    private void fetchPredictionAndUpdateImage() {
        List<Commande> commandes = sp.getRecentCommandes(20);
        System.out.print("test");

        List<Map<String, Object>> simplifiedCommandes = commandes.stream().map(commande -> {
            Map<String, Object> map = new HashMap<>();
            map.put("date", commande.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            map.put("total", commande.getTotal());
            return map;
        }).collect(Collectors.toList());
        System.out.print("test");

        String jsonData = new Gson().toJson(simplifiedCommandes);

        System.out.print(jsonData);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://127.0.0.1:5000/predict"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonData))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    JsonObject jsonResponse = new Gson().fromJson(response.body(), JsonObject.class);
                    JsonArray predictions = jsonResponse.getAsJsonArray("predictions");

                    if (predictions != null && predictions.size() > 0) {
                        double prediction = predictions.get(0).getAsDouble();
                        System.out.println("Prediction: " + prediction);
                        if (prediction > 0.5) {
                            AIResult.setImage(image2);
                        } else {
                            AIResult.setImage(image1);
                        }
                    } else {
                        System.out.println("No prediction data available or the prediction format is incorrect.");
                    }
                })
                .exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.print("test");

        fetchPredictionAndUpdateImage();
    }
}
