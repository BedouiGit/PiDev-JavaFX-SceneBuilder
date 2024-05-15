package controllers.UserController;

import com.github.sarxos.webcam.Webcam;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import models.user;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import models.user;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.ResourceBundle;

public class Loginfaceid implements Initializable
{
    @javafx.fxml.FXML
    private ImageView im;
    private Webcam webcam;
    @javafx.fxml.FXML
    public void initialize() {
    }
    user e = new user();
    @javafx.fxml.FXML
    public void capture(ActionEvent actionEvent) throws IOException, InterruptedException
    {

        ImageIO.write(webcam.getImage(),"JPG",new File("src/main/resources/images/firstimag.jpg"));
        webcam.close();

        System.setProperty("javax.net.ssl.trustStore","app.keystore");
        System.setProperty("javax.net.ssl.trustStorePassword","change it");

        String imagePath = "src/main/resources/images/firstimag.jpg";
        String imagePath2 = e.getImage();

        String urlString = " https://faceapi.mxface.ai/api/v3/face/verify";
        urlString = urlString.trim();

        // Read all bytes from the image file
        byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
        byte[] imageBytes2 = Files.readAllBytes(Paths.get(imagePath2));


        // Encode the byte array to a Base64 string
        String base64String = Base64.getEncoder().encodeToString(imageBytes);
        String base64String2 = Base64.getEncoder().encodeToString(imageBytes2);

        String jsonPayload = String.format("{ \"encoded_image1\": \"%s\", \"encoded_image2\": \"%s\" }", base64String, base64String2);

        // Output the Base64 string

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2) // Use HTTP/2 version
                .build();

        // Create the HttpRequest for an HTTPS endpoint
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString)) // HTTPS API endpoint
                .header("Content-Type", "application/json") // Correct Content-Type for JSON payload

                .header("Subscriptionkey", "1KnGsmZ682HQIDfGCf-C9qLaADDwr2323") // Set the content type to application/json
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload)) // JSON payload
                .build();

        // Send the request and receive the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String error ="";
        int matchResult =0;
        // Output the response status code and body
        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());

        JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();

        if (jsonObject.has("errorMessage"))
        {
            System.out.println("face is not detactable");
        }
        else
        {


            JsonArray matchedFaces = jsonObject.getAsJsonArray("matchedFaces");

            for (int i = 0; i < matchedFaces.size(); i++) {
                JsonObject matchedFace = matchedFaces.get(i).getAsJsonObject();
                matchResult = matchedFace.get("matchResult").getAsInt();
                System.out.println("Match Result: " + matchResult);
            }

            if (matchResult == 1)
            {
                if ( e instanceof user )
                {
                    System.out.println("user Detected");
                }
            }
            else
            {
                System.out.println("face not match");
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        webcam = Webcam.getDefault();
        webcam.open();

        im.setFitHeight(480);
        im.setFitWidth(640);
        Button b = new Button("test");
        // Update the ImageView with the webcam image every frame
        new Thread(() -> {
            while (true) {
                try {
                    if (webcam.isOpen()) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(webcam.getImage(), "PNG", baos);
                        im.setImage(SwingFXUtils.toFXImage(ImageIO.read(new ByteArrayInputStream(baos.toByteArray())), null));
                    }
                    Thread.sleep(100); // Adjust this value as needed
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public  void  getuser(user e)
    {
        this.e=e;
    }
}