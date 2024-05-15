package controllers.CategoryController;

import javafx.application.Platform;
import javafx.fxml.FXML;
import org.controlsfx.control.Notifications;
import java.time.Duration;
import java.util.*;

public class NotificationController {
    private List<String> notificationMessages;
    private Random random;

    public NotificationController() {
        // Initialize notification messages
        notificationMessages = new ArrayList<>();
        notificationMessages.add("A new NFT category has been added: Art");
        notificationMessages.add("Top trending NFT category: Digital Collectibles");
        notificationMessages.add("Explore the latest NFT categories: Art, Music, Gaming");
        notificationMessages.add("Get exclusive access to premium NFT categories!");


        // Initialize random number generator
        random = new Random();
    }

    // Method to display a random notification
    public void displayRandomNotification() {
        int index = random.nextInt(notificationMessages.size());
        String message = notificationMessages.get(index);

        // Display notification
        Platform.runLater(() -> {
            Notifications.create()
                    .title("Notification")
                    .text(message)
                    .show();
        });
    }

    // Method to start periodic notifications
    public void startPeriodicNotifications() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                displayRandomNotification();
            }
        };

        timer.scheduleAtFixedRate(task, 0, 60000);
    }
}
