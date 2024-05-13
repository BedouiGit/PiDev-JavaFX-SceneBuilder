import javafx.application.Application;
import models.user;

import static javafx.application.Application.launch;

public abstract class Temp extends Application {
    private static user currentUser;

    // Constructor
    public Temp(user user) {
        currentUser = user;
    }

    // Static method to get the currentUser
    public static user getCurrentUser() {
        return currentUser;
    }

    // Static method to set the currentUse  r
    public static void setCurrentUser(user user) {
        currentUser = user;
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}
