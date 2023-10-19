package comp1110.ass2.gui;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

public class GUISellectPlayerInterface extends Group {
    private static final double WINDOW_WIDTH = 800;  // Window width, can adjust (根据您的窗口大小调整)
    private static final double WINDOW_HEIGHT = 600;  // Window height(根据您的窗口大小调整)

    public GUISellectPlayerInterface(Consumer<Integer> onPlayerCountSelected) {
        VBox vbox = new VBox(30);  // Container for holding buttons with spacing of 30 (用于存放按钮的容器，间距为30)
        vbox.setAlignment(Pos.CENTER);  // Align the contents of the VBox to the center (将 VBox 内容居中)

        for (int i = 2; i <= 4; i++) {
            int playerCount = i;

            // Create a button and set its text, click event, and style (创建按钮并设置文本、点击事件和样式)
            Button button = new Button("Player number: " + playerCount);
            button.setStyle("-fx-font-size: 14pt; -fx-background-color: #2196c0; -fx-padding: 10 20 10 20;");
            button.setOnAction(event -> {
                onPlayerCountSelected.accept(playerCount);
            });

            vbox.getChildren().add(button);
        }
        // Position the VBox in the center of the window
        vbox.setLayoutX(WINDOW_WIDTH / 2 - vbox.getBoundsInLocal().getWidth() / 2 + 125);
        vbox.setLayoutY(WINDOW_HEIGHT / 2 - vbox.getBoundsInLocal().getHeight() / 2 - 100);

        this.getChildren().add(vbox);
    }
}