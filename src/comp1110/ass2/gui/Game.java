package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Game extends Application {
    // Define the main application class for the game
    private final Group root = new Group();
    private Button restartButton;
    // Define various properties for the game interface
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;
    GUIMarrakech guiMarrakech;
    MarrakechGame game;
    // Declare game-related objects and variables
    private Color getColorFromChar(char colorChar) {
        Color color;
        switch (colorChar) {
            case 'c':
                color = Color.CYAN;
                break;
            case 'y':
                color = Color.YELLOW;
                break;
            case 'r':
                color = Color.RED;
                break;
            case 'p':
                color = Color.PURPLE;
                break;
            case 'n':
                color = Color.WHITE;
                break;
            default:
                color = Color.BLACK;
                break;
        }
        return color;
    }
    // Convert a character representing a color to a JavaFX Color object
    @Override
    public void start(Stage stage) throws Exception {
        // Start the game application
        // FIXME Task 7 and 15
        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);

        restartButton = new Button("Restart Game");
        restartButton.setOnAction(e -> initializeGame()); // reinitialize the game when click this button

        // Use Group to manually position the button to the lower right corner of the screen
        restartButton.setTranslateX(WINDOW_WIDTH - restartButton.getWidth() - 150);
        restartButton.setTranslateY(WINDOW_HEIGHT - restartButton.getHeight() - 50);
        restartButton.setStyle("-fx-background-color: #00fbff; -fx-font-size: 12pt; -fx-padding: 10px 20px;");

        root.getChildren().add(restartButton);

        initializeGame();
        stage.setScene(scene);
        stage.setTitle("Marrakech game");
        stage.show();
    }

    private void initializeGame() {
        // Initialize the game and display the player selection interface
        GUISellectPlayerInterface selectPlayerInterface = new GUISellectPlayerInterface(playerCount -> {
            game = new MarrakechGame(playerCount);
            GUIMainGame mainGame = new GUIMainGame(game, WINDOW_WIDTH, WINDOW_HEIGHT);
            root.getChildren().clear();
            root.getChildren().add(restartButton);
            root.getChildren().add(mainGame);
        });

        root.getChildren().clear();
        root.getChildren().add(restartButton);
        root.getChildren().add(selectPlayerInterface);
    }
}

// This class was written by Huizhe Ruan.

