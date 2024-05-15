package controllers.UserController;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.Role;
import models.user;
import services.userService;

public class EditUser {

    @FXML
    private TextField address;

    @FXML
    private TextField age;

    @FXML
    private TextField fname;

    @FXML
    private ComboBox<String> gender;

    @FXML
    private TextField lname;

    @FXML
    private TextField tel;

    @FXML
    private CheckBox userActiveCheckBox;

    @FXML
    private TextField userEmailLabel;
    @FXML
    private ComboBox<?> role;

    private user user;
    private final userService userService;

    public EditUser() {
        userService = new userService();
    }

    public void initData(user user) {
        this.user = user;
        userEmailLabel.setText(user.getEmail());
        fname.setText(user.getFirst_name());
        lname.setText(user.getLast_name());
        age.setText(Integer.toString(user.getAge()));
        address.setText(user.getAddress());
        tel.setText(user.getTel());
        gender.setValue(user.getGender());
        userActiveCheckBox.setSelected(user.getBanned());

           }

    @FXML
    private void saveChanges() {
        if (user != null) {
            // Update user object with new values from text fields
            user.setFirst_name(fname.getText());
            user.setLast_name(lname.getText());
            user.setAddress(address.getText());
            user.setAge(Integer.parseInt(age.getText()));
            user.setGender(gender.getValue());
            user.setTel(tel.getText());
            user.setIs_banned(userActiveCheckBox.isSelected());
            // Assuming Role is an enum and roleComboBox is a ComboBox<Role>
            //user.setRole(Role.valueOf(role.getValue().toString()));

            // Call the modifierUser method to update the user in the database
            userService.modifierUser(user);
        }
        closeDialog();
    }


    @FXML
    private void closeDialog() {
        userActiveCheckBox.getScene().getWindow().hide();
    }

}
