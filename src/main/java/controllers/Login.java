package controllers;

import com.mysql.cj.DataStoreMetadata;
import com.mysql.cj.MessageBuilder;
import com.mysql.cj.Session;
import com.mysql.cj.conf.HostInfo;
import com.mysql.cj.conf.PropertySet;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.log.Log;
import com.mysql.cj.log.ProfilerEventHandler;
import com.mysql.cj.protocol.Message;
import com.mysql.cj.protocol.ServerSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import models.Role;
import models.user;
import services.userService;

import java.io.IOException;
import java.net.SocketAddress;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Login {

    @FXML
    private Button loginButton;
    @FXML
    private TextField emailTextfield;
    @FXML
    private PasswordField passwordTextfield;
    @FXML
    private Hyperlink signupLink;
    @FXML
    private Label invalidText;
    private user connectedUser;
    userService us = new userService();
    public void loginButtonOnAction(ActionEvent e) throws IOException, SQLException
    {

        if (emailTextfield.getText().isEmpty() || passwordTextfield.getText().isEmpty()) {
            invalidText.setText("Please complete all fields.");
            return;
        } else {
            userService us = new userService();
            user u = us.getUserByEmail(emailTextfield.getText());
            if(u == null){
                invalidText.setText("Invalid E-mail. Please try again.");
                return;
            }
            String enteredPassword = passwordTextfield.getText();
            boolean passwordMatch = BCrypt.checkpw(enteredPassword, u.getPassword());
            if (passwordMatch) {
                // Set up session

                invalidText.setText("");

                if (u.getRole() == Role.ROLE_ADMIN) {
                    // Redirect to admin dashboard
                    goToAdminDashboard(u);
                } else if (u.getRole() == Role.ROLE_USER) {
                    // Redirect to user profile
                    goToHomePage(u);
                }
            } else {
                invalidText.setText("Invalid Password. Please try again.");
            }
        }
    }
        // Method to redirect to admin dashboard
        private void goToAdminDashboard(user u) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Dashboard.fxml"));
        Parent adminRoot = loader.load();
            Dashboard profileController = loader.getController(); // Assuming ProfileController is the name of your controller class
           // profileController.initData(u);
            Scene adminScene = new Scene(adminRoot);
        Stage window = (Stage) loginButton.getScene().getWindow();
        window.setScene(adminScene);
        window.show();
    }
        // Method to redirect to user profile
        private void goToUserProfile(user u) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/List_NFTS.fxml"));
        Parent userRoot = loader.load();
            Profile profileController = loader.getController(); // Assuming ProfileController is the name of your controller class
            profileController.initData(u);
            Scene userScene = new Scene(userRoot);
        Stage window = (Stage) loginButton.getScene().getWindow();
        window.setScene(userScene);
        window.show();
    }
    private void goToHomePage(user u) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/NavBar.fxml"));
        Parent userRoot = loader.load();
        Scene userScene = new Scene(userRoot);
        Stage window = (Stage) loginButton.getScene().getWindow();
        window.setScene(userScene);
        window.show();
    }

    @FXML
    private void forgetPassword(ActionEvent event){
        userService userService = new userService();
        Stage resetPasswordStage = new Stage();
        Parent resetPasswordInterface;
        try {
            resetPasswordInterface = FXMLLoader.load(getClass().getResource("/forgetPassword.fxml"));
            Scene resetPasswordScene = new Scene(resetPasswordInterface);
            resetPasswordStage.setScene(resetPasswordScene);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            // Show the UserInterface stage
            resetPasswordStage.show();

        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    void goToSignup(ActionEvent event) {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/signup.fxml"));
        Parent signInRoot = null;
        try {
            signInRoot = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene signInScene = new Scene(signInRoot);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(signInScene);
        window.show();
    }
    public void goToUserList() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/DashboardAdmin.fxml"));
        Parent profileInterface = loader.load();

        // Get the controller instance
        DashboardAdmin profileController = loader.getController();

        // Initialize data using the controller's method

        Scene profileScene = new Scene(profileInterface);
        Stage profileStage = new Stage();
        profileStage.setScene(profileScene);

        // Close the current stage (assuming loginButton is accessible from here)
        Stage currentStage = (Stage) loginButton.getScene().getWindow();
        currentStage.close();

        // Show the profile stage
        profileStage.show();
    }
    @FXML
    public void loginfaceid(ActionEvent actionEvent) throws IOException {
        emailTextfield.getText();
        user user =  us.getOneByEmail(emailTextfield.getText());
        System.out.println(user);
        FXMLLoader load = new FXMLLoader(getClass().getResource("/loginfaceid.fxml"));
        Parent root =load.load();
        Loginfaceid c2=  load.getController();
        c2.getuser(user);
        Scene ss= new Scene(root);
        Stage s= new Stage();
        s=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        s.setScene(ss);
        s.show();
    }
}
