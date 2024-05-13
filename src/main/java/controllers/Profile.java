    package controllers;
    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.fxml.Initializable;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.control.*;
    import javafx.scene.image.Image;
    import javafx.scene.image.ImageView;
    import javafx.stage.FileChooser;
    import javafx.stage.Stage;
    import models.Temp;
    import models.user;
    import models.Role;
    import services.userService;
    import java.io.*;
    import java.net.URL;
    import java.util.Optional;
    import java.util.ResourceBundle;

    public class Profile implements Initializable {
        private user currentUser;

        @FXML
        private TextField nom;
        @FXML
        private TextField prenom;
        @FXML
        private TextField email;
        @FXML
        private TextField phone;
        @FXML
        private TextField adresse;
        @FXML
        private CheckBox maleCheckbox;
        @FXML
        private CheckBox femaleCheckbox;
        @FXML
        private Button editButton;
        @FXML
        private Button updateButton;
        @FXML
        private ImageView profileImageView;
        @FXML
        private Button logoutButton;
        @FXML
        private Button uploadButton;

        public void initData() {
            user usere = Temp.getCurrentUser();
            if(usere.getRole().equals(Role.ROLE_ADMIN)){
                logoutButton.setVisible(false);
            }
            //  textFiedTest.setText("testtt");
            userService us = new userService();

            System.out.println(Temp.getCurrentUser());
            user user = Temp.getCurrentUser();

            user currentUser = Temp.getCurrentUser();


            nom.setText(user.getFirst_name());
            prenom.setText(user.getLast_name());
            email.setText(user.getEmail());
            phone.setText(user.getTel());
            adresse.setText(user.getAddress());

            if (user.getGender().equals("Male")) {
                maleCheckbox.setSelected(true);
                femaleCheckbox.setSelected(false);
            } else if (user.getGender().equals("Female")) {
                maleCheckbox.setSelected(false);
                femaleCheckbox.setSelected(true);
            }
            System.out.println(usere.getImage());

          //  String imageUrl = "/static/images/" + user.getImage(); // Assuming user.getImage() returns the image filename
            //Image image = new Image(getClass().getResource(usere.getImage()).toExternalForm());
            Image image = null;
            System.out.println(user.getImage());

           // Image image = null;
            if (user.getImage() != null && !user.getImage().isEmpty()) {
                image = new Image(user.getImage());
                System.out.println(user.getImage());
                profileImageView.setImage(image);
            }
            profileImageView.setImage(image);

           // user.getImage();
            profileImageView.setImage(image);
            System.out.println(user);
            populateProfileInformation(user);
        }
        @FXML
        private void uploadImage(ActionEvent event) throws IOException {
            userService us = new userService();
            this.currentUser = Temp.getCurrentUser();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Image File");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
            );

            File selectedFile = fileChooser.showOpenDialog(new Stage());
            if (selectedFile != null) {
                Image image = new Image(selectedFile.toURI().toString());
                System.out.println(selectedFile.toURI().toString());
                profileImageView.setImage(image);

                // Check if currentUser is null before accessing its properties
                if (currentUser != null) {
                    System.out.println(selectedFile.toURI().toString());
                    try {
                        saveImageAndGetUrl(selectedFile.toPath().toUri().toURL().openStream(), selectedFile.getName());
                        us.updateImage(currentUser, selectedFile.getAbsolutePath());
                        currentUser.setImage(selectedFile.getAbsolutePath());
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Image updated successfully");
                        alert.showAndWait(); // Show the confirmation dialog
                    } catch (IOException e) {
                        e.printStackTrace(); // Handle the exception appropriately
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("An error occurred while saving the image.");
                        alert.showAndWait();
                    }
                } else {
                    // Handle the case when currentUser is null
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("User information is not available. Please log in again.");
                    alert.showAndWait();
                }
            }
        }

        private void populateProfileInformation(user user) {

        }

        private void handleEditButtonAction() {
            // Implement edit profile action
        }

        public void enabelEditing(){
            nom.setDisable(false);
            prenom.setDisable(false);
            email.setDisable(false);
            phone.setDisable(false);
            adresse.setDisable(false);
            maleCheckbox.setDisable(false);
            femaleCheckbox.setDisable(false);
            updateButton.setVisible(true);
        }
        public void UpdateData(){
            userService us = new userService();
            user u = new user();
            u.setFirst_name(nom.getText());
            u.setLast_name(prenom.getText());
            u.setImage(currentUser.getImage());
            u.setEmail(email.getText());
            u.setRole(currentUser.getRole());
            u.setTel(phone.getText());
            u.setAddress(adresse.getText());
            if (maleCheckbox.isSelected()) {
                u.setGender("Male");
            } else if (femaleCheckbox.isSelected()) {
                u.setGender("Female");
            } else {
                // Handle the case when neither checkbox is selected
                // You may want to display an error message or handle it according to your requirements
            }



            us.updateProfile(u);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Informations Updated");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {}
        }
        private String saveImageAndGetUrl(InputStream inputStream , String filename) throws IOException {
            // Define the directory where images will be stored
            String uploadDirectory = "src/main/resources/static/images"; // Change this to your desired directory path
            File directory = new File(uploadDirectory);
            if (!directory.exists()) {
                directory.mkdirs(); // Create the directory if it doesn't exist
            }

            // Save the image file to the upload directory
            String filePath = uploadDirectory + File.separator + filename;
            try (OutputStream outputStream = new FileOutputStream(new File(filePath))) {
                int read;
                byte[] bytes = new byte[1024];
                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
            }

            // Return the URL of the saved image
            return  filename; // Adjust the URL format as needed
        }
        public void logout(){
            Stage stage = (Stage) updateButton.getScene().getWindow();
            stage.close();
            Stage newStage = new Stage();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
                Scene scene = new Scene(root);
                newStage.setScene(scene);
                newStage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            user usere = Temp.getCurrentUser();

            if(usere.getRole().equals(Role.ROLE_ADMIN)){
                logoutButton.setVisible(false);
            }
            //  textFiedTest.setText("testtt");
            userService us = new userService();

            System.out.println(Temp.getCurrentUser());
            user user = Temp.getCurrentUser();

            user currentUser = Temp.getCurrentUser();


            nom.setText(user.getFirst_name());
            prenom.setText(user.getLast_name());
            email.setText(user.getEmail());
            phone.setText(user.getTel());
            adresse.setText(user.getAddress());

            if (user.getGender().equals("Male")) {
                maleCheckbox.setSelected(true);
                femaleCheckbox.setSelected(false);
            } else if (user.getGender().equals("Female")) {
                maleCheckbox.setSelected(false);
                femaleCheckbox.setSelected(true);
            }
            System.out.println(usere.getImage());

            //  String imageUrl = "/static/images/" + user.getImage(); // Assuming user.getImage() returns the image filename
            //Image image = new Image(getClass().getResource(usere.getImage()).toExternalForm());
            Image image = null;
            System.out.println(user.getImage());

            // Image image = null;
            if (user.getImage() != null && !user.getImage().isEmpty()) {
                image = new Image(user.getImage());
                System.out.println(user.getImage());
                profileImageView.setImage(image);
            }
            profileImageView.setImage(image);

            // user.getImage();
            profileImageView.setImage(image);
            System.out.println(user);
            populateProfileInformation(user);
        }
    }