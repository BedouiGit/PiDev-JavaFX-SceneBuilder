package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Role;
import models.user;
import services.userService;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class Signup implements Initializable {

    @FXML
    private TextField address;

    @FXML
    private TextField captcha;

    @FXML
    private TextField captchaC;

    @FXML
    private TextField ageTextField;

    @FXML
    private TextField emailTextfield;

    @FXML
    private ComboBox<String> gender;

    @FXML
    private Label invalidText;

    @FXML
    private TextField nomTextField;

    @FXML
    private PasswordField passwordTextfield;

    @FXML
    private TextField prenomTextField;

    @FXML
    private Button signupButton;

    @FXML
    private TextField tel;
    public void signupButtonOnAction(ActionEvent event) throws IOException {

        // String gender = genderComboBox.getValue();

        //System.out.println(gender);
        if (emailTextfield.getText().isEmpty() || emailTextfield.getText().isEmpty() || captcha.getText().isEmpty()|| passwordTextfield.getText().isEmpty() || gender.getValue() == null) {
            invalidText.setText("Please fill in all fields");
            invalidText.setVisible(true);
            return;
        }
        if(!emailTextfield.getText().equals(emailTextfield.getText())){
            invalidText.setText("Email do not match");
            invalidText.setVisible(true);
            return;
        }

        if (!captcha.getText().equalsIgnoreCase(captchaText)) {
            invalidText.setText("Invalid CAPTCHA!");
            invalidText.setVisible(true);
            return; // Return if the captcha is incorrect
        }

        if(!passwordTextfield.getText().equals(passwordTextfield.getText())){
            invalidText.setText("Passwords do not match");
            invalidText.setVisible(true);
            return;
        }
        userService us = new userService();
        if(us.getUserByEmail(emailTextfield.getText()) == null){
            int leftLimit = 97; // letter 'a'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = 6;
            Random random = new Random();
            String generatedString = random.ints(leftLimit, rightLimit + 1)
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
            user u = new user(
                    0, // Assuming id should be set later or auto-generated
                    nomTextField.getText(),
                    prenomTextField.getText(),
                    emailTextfield.getText(),
                    passwordTextfield.getText(), // Assuming generatedString is the password
                    Role.ROLE_USER, // Assuming roleComboBox.getValue() returns a valid Role enum value
                    Integer.parseInt(ageTextField.getText()),
                    address.getText(), // Assuming address is not provided in this context
                    tel.getText(), // Assuming tel is not provided in this context
                    gender.getValue(),
                    false// Assuming gender is not provided in this context


            );
            us.ajouterUser(u);


        }else{
            invalidText.setText("User already exists");
            invalidText.setVisible(true);
            return;
        }
    }
    public void goBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent profileInterface = loader.load();
        Scene profileScene = new Scene(profileInterface);
        Stage profileStage = new Stage();
        profileStage.setScene(profileScene);

        // Close the current stage (assuming loginButton s accessible from here)
        Stage currentStage = (Stage) signupButton.getScene().getWindow();
        currentStage.close();

        // Show the profile stage
        profileStage.show();
    }

    private String captchaText;
    private void generateCaptcha() {
        // Generate a random sequence of letters for the CAPTCHA
        Random random = new Random();
        StringBuilder captcha = new StringBuilder();
        for (int i = 0; i < 6; i++) { // Generate a 6-letter CAPTCHA
            char randomChar = (char) (random.nextInt(26) + 'A'); // Generate random uppercase letters
            captcha.append(randomChar);
        }
        captchaText = captcha.toString();
        captchaC.setText(captchaText);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateCaptcha();
    }
}
