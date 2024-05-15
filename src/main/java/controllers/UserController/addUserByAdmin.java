package controllers.UserController;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Role;
import models.user;
import services.userService;

import java.io.IOException;
import java.util.Random;

import static utils.MailUtil.sendPassword;

public class addUserByAdmin {
    @FXML
    private TextField fname;
    @FXML
    private TextField fage;
    @FXML
    private TextField faddress;
    @FXML
    private ComboBox<String> fgender;
    @FXML
    private TextField lname;
    @FXML
    private TextField email;
    @FXML
    private Label invalidText;
    @FXML
    private TextField phone;
    @FXML
    private ComboBox<String> roleComboBox;
    @FXML
    private Label titleid;



    private Role role; // Assuming Role is the enum type

    public void initData(Role rolee) {
        this.role = rolee;

    }

    public void signupButtonOnAction(ActionEvent event) {
        if (role == null) {
            System.out.println("Role is not initialized. Make sure to call initData method before signupButtonOnAction.");
            return;
        }


        if (fname.getText().isEmpty() || lname.getText().isEmpty() || email.getText().isEmpty() || phone.getText().isEmpty()) {
            invalidText.setText("Please fill in all fields");
            invalidText.setVisible(true);
            return;
        }

        userService us = new userService();
        if (us.getUserByEmail(email.getText()) != null) {
            invalidText.setText("Email already exists");
            invalidText.setVisible(true);
            return;
        }

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        String password = random.ints(leftLimit, rightLimit + 1)
                .limit(12)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        user u = new user(
                0, // Assuming id should be set later or auto-generated
                fname.getText(),
                lname.getText(),
                email.getText(),
                password, // Assuming generatedString is the password
                Role.valueOf(roleComboBox.getValue()), // Assuming roleComboBox.getValue() returns a valid Role enum value
                Integer.parseInt(fage.getText()),
                faddress.getText(),
                phone.getText(),
                fgender.getValue(),
        false        );
        if (us.ajouterUser(u)) {
            sendPassword(email.getText(), password);
            System.out.println("User added successfully");
        }

        else {
            invalidText.setText("User already exists");
            invalidText.setVisible(true);
            return;
        }
    }
}
