package comp1110.ass2.gui;
import javafx.animation.PauseTransition;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.Group;

public class GUIAlertInformation extends Group {
    // 静态变量用于跟踪已经显示的信息数量
    private static int displayedMessagesCount = 0;

    public GUIAlertInformation(String message, double windowWidth, double windowHeight) {
        Label toastLabel = new Label(message);
        toastLabel.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-padding: 20px; -fx-font-size: 20px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        StackPane toastContainer = new StackPane(toastLabel);
        toastContainer.setLayoutX((windowWidth - toastLabel.getWidth()) / 2);

        // 根据已经显示的信息数量调整y位置
        double yOffset = displayedMessagesCount * (toastLabel.getHeight() + 100); // 10是两个信息之间的间距
        toastContainer.setLayoutY((windowHeight - toastLabel.getHeight()) / 2 + yOffset);

        this.getChildren().add(toastContainer);

        PauseTransition toastDisplayDelay = new PauseTransition(Duration.seconds(1));
        toastDisplayDelay.setOnFinished(e -> {
            // 开始淡出过渡
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), toastContainer);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(event -> {
                this.getChildren().remove(toastContainer);
                // 减少已显示的信息数量
                displayedMessagesCount--;
            });
            fadeOut.play();
        });
        toastDisplayDelay.play();

        // 增加已显示的信息数量
        displayedMessagesCount++;
    }
}
