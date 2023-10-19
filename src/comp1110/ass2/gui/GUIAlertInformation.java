package comp1110.ass2.gui;
import javafx.animation.PauseTransition;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.Group;

public class GUIAlertInformation extends Group {
    // Tracks the count of displayed messages using a static variable
    private static int displayedMessagesCount = 0;
    // track the amount of information that has been displayed (静态变量用于跟踪已经显示的信息数量)
    public GUIAlertInformation(String message, double windowWidth, double windowHeight) {
        // Create a GUI alert with a given message, window width, and height
        Label toastLabel = new Label(message);
        // Create a label for displaying the message with specific styles
        toastLabel.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-padding: 20px; -fx-font-size: 20px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        StackPane toastContainer = new StackPane(toastLabel);
        // Adjust the x position of the container
        toastContainer.setLayoutX((windowWidth - toastLabel.getWidth()) / 2 + 200);

        // Adjust the y position based on the amount of information displayed (根据已经显示的信息数量调整y位置)
        double yOffset = displayedMessagesCount * (toastLabel.getHeight() + 100);
        toastContainer.setLayoutY((windowHeight - toastLabel.getHeight()) / 2 + yOffset + 30);

        this.getChildren().add(toastContainer);

        PauseTransition toastDisplayDelay = new PauseTransition(Duration.seconds(1));
        toastDisplayDelay.setOnFinished(e -> {
            // Start the fade transition (开始淡出过渡)
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), toastContainer);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(event -> {
                this.getChildren().remove(toastContainer);
                // Reduce the amount of information that is displayed (减少已显示的信息数量)
                displayedMessagesCount--;
            });
            fadeOut.play();
        });
        toastDisplayDelay.play();

        // Increases the amount of information that is displayed (增加已显示的信息数量)
        displayedMessagesCount++;
    }
}
