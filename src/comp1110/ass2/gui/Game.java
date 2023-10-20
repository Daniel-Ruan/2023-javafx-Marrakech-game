package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game extends Application {
    // Define the main application class for the game
    private final Group root = new Group();
    private Button restartButton;
    private final Group gameStatusGroup = new Group();
    private final Group boardGroup = new Group();
    private final Object lock = new Object();
    // Define various properties for the game interface
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;
    private static final int TILE_SIZE = 80;
    private static final int LABEL_WIDTH = 300;
    private static final int CELL_SIDE_LENGTH = 60;
    private static final int CELL_SIDE_NUMBER = 7;
    private static final int CELL_ID_TEXT_SIZE = 15;
    private static final int TRIANGLE_HEIGHT = 30;
    private static final char[] PLAYER_COLORS = {'c', 'y', 'p', 'r'};  // Declare it as a class constant.
    // Define constants for game window dimensions and UI elements


    GUIMarrakech guiMarrakech;
    MarrakechGame game;
    private Player[] players;
    private Assam assam;
    private Board board;
    private int currentPlayer = 0;
    private int yOffset = 10;
    private String currentGameState;
    private boolean isTurnedAssam;
    private boolean hasMoved;
    private boolean hasPlacedRug;
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


    private void showPlayerSelection() {}

    private void updateAssamStatusInGameGroup(javafx.scene.layout.VBox box) {
        javafx.scene.control.Label assamLabel = new javafx.scene.control.Label(
                "Assam Position: (" + assam.getPosition().getX() + ", " + assam.getPosition().getY() + "), Orientation: " + assam.getOrientation());
        box.getChildren().add(assamLabel);
    }

    private void updateBoardStatusInGameGroup(javafx.scene.layout.VBox box) {
        String boardString = board.toBoardString();
        // Gets a string representation of the checkerboard

        javafx.scene.control.Label boardLabel = new javafx.scene.control.Label("Board: " + boardString);
        // update the tag
        box.getChildren().add(boardLabel);
    }

    private void updateGameStateInGameGroup(javafx.scene.layout.VBox box) {
        currentGameState = generateGameState();
        javafx.scene.control.Label gameStateLabel = new javafx.scene.control.Label("Game State: " + currentGameState);
        System.out.println(currentGameState);
        box.getChildren().add(gameStateLabel);
    }

    private void updatePlayerStatusInGameGroup(Player[] players, javafx.scene.layout.VBox box) {
        for (Player player : players) {
            javafx.scene.control.Label playerLabel = new javafx.scene.control.Label(
                    "Player " + player.getColor() + ": " +
                            player.getDirhams() + " dirhams, " +
                            player.getRugs() + " rugs, " +
                            "Status: " + player.getPlayerStates()
            );
            box.getChildren().add(playerLabel);
        }
    }
    private void drawArc(Group boardGroup, double centerX, double centerY, double radius, double arcStart, double arcEnd) {
        Arc arc = new Arc();
        arc.setCenterX(centerX);
        arc.setCenterY(centerY);
        arc.setRadiusX(radius);
        arc.setRadiusY(radius);
        arc.setStartAngle(arcStart);  // Start at 3 o 'clock
        arc.setLength(arcEnd);  // Use the arcLength value passed in
        arc.setType(ArcType.OPEN);  //  Create outline,no fill
        arc.setStroke(Color.BLACK);
        arc.setStrokeWidth(2);
        arc.setFill(Color.TRANSPARENT);
        boardGroup.getChildren().add(arc);
    }

    private void drawBoardCircles(Group boardGroup, double boardOffsetX, double boardOffsetY) {
        // Draw a half circle at the top edge between (0, 0) and (0, 1)
        double halfCircleCenterX = boardOffsetX + CELL_SIDE_LENGTH;
        double halfCircleCenterY = boardOffsetY;
        drawArc(boardGroup, halfCircleCenterX, halfCircleCenterY, 0.5 * CELL_SIDE_LENGTH, 0, 180);

        // Draw a half circle at the top edge between (0, 2) and (0, 3)
        halfCircleCenterX += CELL_SIDE_LENGTH;  // Move to the next two cells
        halfCircleCenterX += CELL_SIDE_LENGTH;  // Move to the next two cells
        drawArc(boardGroup, halfCircleCenterX, halfCircleCenterY, 0.5 * CELL_SIDE_LENGTH, 0, 180);

        // Draw a half circle at the top edge between (0, 4) and (0, 5)
        halfCircleCenterX += CELL_SIDE_LENGTH;  // Move to the next two cells
        halfCircleCenterX += CELL_SIDE_LENGTH;  // Move to the next two cells
        drawArc(boardGroup, halfCircleCenterX, halfCircleCenterY, 0.5 * CELL_SIDE_LENGTH, 0, 180);

        // Draw a half circle at the down edge between (1, 7) and (2, 7)
        halfCircleCenterX = boardOffsetX + CELL_SIDE_LENGTH * 6.0; // Start from the middle of (1,7) and (2,7)
        halfCircleCenterY = boardOffsetY + CELL_SIDE_NUMBER * CELL_SIDE_LENGTH;  // Move it to the bottom edge
        drawArc(boardGroup, halfCircleCenterX, halfCircleCenterY, 0.5 * CELL_SIDE_LENGTH, 180, 180);

        // Draw a half circle at the down edge between (3, 7) and (4, 7)
        halfCircleCenterX -= CELL_SIDE_LENGTH * 2;  // Move to the previous two cells
        drawArc(boardGroup, halfCircleCenterX, halfCircleCenterY, 0.5 * CELL_SIDE_LENGTH, 180, 180);

        // Draw a half circle at the down edge between (5, 7) and (6, 7)
        halfCircleCenterX -= CELL_SIDE_LENGTH * 2;  // Move to the previous two cells
        drawArc(boardGroup, halfCircleCenterX, halfCircleCenterY, 0.5 * CELL_SIDE_LENGTH, 180, 180);

        // The left part of the half circle

        // Draw a half circle at the left edge between (0, 1) and (1, 1)
        halfCircleCenterX = boardOffsetX;
        halfCircleCenterY = boardOffsetY + CELL_SIDE_LENGTH * 1.0;  // 1.5 represents the middle between two cells
        drawArc(boardGroup, halfCircleCenterX, halfCircleCenterY, 0.5 * CELL_SIDE_LENGTH, 90, 180);

        // Draw a half circle at the left edge between (0, 3) and (1, 3)
        halfCircleCenterY += CELL_SIDE_LENGTH * 2;  // Move to the next two cells
        drawArc(boardGroup, halfCircleCenterX, halfCircleCenterY, 0.5 * CELL_SIDE_LENGTH, 90, 180);

        // Draw a half circle at the left edge between (0, 5) and (1, 5)
        halfCircleCenterY += CELL_SIDE_LENGTH * 2;  // Move to the next two cells
        drawArc(boardGroup, halfCircleCenterX, halfCircleCenterY, 0.5 * CELL_SIDE_LENGTH, 90, 180);

        // Draw a half circle at the right edge between (6, 1) and (7, 1)
        halfCircleCenterX = boardOffsetX + CELL_SIDE_LENGTH * 7;  // Move to the rightmost edge
        halfCircleCenterY = boardOffsetY + CELL_SIDE_LENGTH * 2.0;  // Reset the Y position
        drawArc(boardGroup, halfCircleCenterX, halfCircleCenterY, 0.5 * CELL_SIDE_LENGTH, -90, 180);

        // Draw a half circle at the right edge between (6, 3) and (7, 3)
        halfCircleCenterY += CELL_SIDE_LENGTH * 2;  // Move to the next two cells
        drawArc(boardGroup, halfCircleCenterX, halfCircleCenterY, 0.5 * CELL_SIDE_LENGTH, -90, 180);

        // Draw a half circle at the right edge between (6, 5) and (7, 5)
        halfCircleCenterY += CELL_SIDE_LENGTH * 2;  // Move to the next two cells
        drawArc(boardGroup, halfCircleCenterX, halfCircleCenterY, 0.5 * CELL_SIDE_LENGTH, -90, 180);

        // Draw a 270 degree circle  with the top right corner of the Board as the center
        double arcCenterX = boardOffsetX + CELL_SIDE_LENGTH * CELL_SIDE_NUMBER;
        double arcCenterY = boardOffsetY;
        drawArc(boardGroup, arcCenterX, arcCenterY, 0.5 * CELL_SIDE_LENGTH, -90, 270);

        // Draw a 270 degree circle  with the lower left corner of the Board as the center
        arcCenterX = boardOffsetX;
        arcCenterY = boardOffsetY + CELL_SIDE_LENGTH * CELL_SIDE_NUMBER;
        drawArc(boardGroup, arcCenterX, arcCenterY, 0.5 * CELL_SIDE_LENGTH, 90, 270);
    }

    public void drawBoard(Board board) {
        int boardSize = CELL_SIDE_NUMBER * CELL_SIDE_LENGTH;
        int boardOffsetX = (WINDOW_WIDTH - boardSize) / 2;
        int boardOffsetY = (WINDOW_HEIGHT - boardSize) / 2;

        if (board != null) {
            for (int row = 0; row < CELL_SIDE_NUMBER; row++) {
                for (int col = 0; col < CELL_SIDE_NUMBER; col++) {

                    IntPair position = new IntPair(row, col);
                    Cell cell = board.getCell(position);

                    if (cell == null) {
                        continue;
                    }

                    char colorChar = cell.getColor();
                    int rugID = cell.getRugID();

                    Rectangle rect = new Rectangle(boardOffsetX + row * CELL_SIDE_LENGTH, boardOffsetY + col * CELL_SIDE_LENGTH, CELL_SIDE_LENGTH, CELL_SIDE_LENGTH);
                    Color cellColor = getColorFromChar(colorChar);

                    rect.setFill(cellColor);
                    rect.setStroke(Color.BLACK);
                    rect.setStrokeWidth(2);
                    boardGroup.getChildren().add(rect);

                    if (colorChar != 'n') {
                        String cellID = String.format("%02d", rugID);
                        Text idText = new Text(cellID);
                        idText.setFont(new Font(CELL_ID_TEXT_SIZE));
                        idText.setX(boardOffsetX + row * CELL_SIDE_LENGTH + CELL_SIDE_LENGTH / 2 - 10);
                        idText.setY(boardOffsetY + col * CELL_SIDE_LENGTH + CELL_SIDE_LENGTH / 2 + 5);
                        boardGroup.getChildren().add(idText);
                    }
                }
            }
            drawBoardCircles(boardGroup, boardOffsetX, boardOffsetY);
            root.getChildren().add(boardGroup); // Add boardGroup to root
        }

        // Draw Assam
        if (assam != null) {
            IntPair pos = assam.getPosition();
            char orientation = assam.getOrientation();

            int ax = pos.getX();
            int ay = pos.getY();

            double centerX = boardOffsetX + (ax * CELL_SIDE_LENGTH) + (CELL_SIDE_LENGTH / 2);
            double centerY = boardOffsetY + (ay * CELL_SIDE_LENGTH) + (CELL_SIDE_LENGTH / 2);
            double halfBase = TRIANGLE_HEIGHT * Math.sin(Math.PI / 3);

            Polygon triangle = new Polygon();
            triangle.getPoints().addAll(
                    centerX, centerY - TRIANGLE_HEIGHT / 2,
                    centerX - halfBase, centerY + TRIANGLE_HEIGHT / 2,
                    centerX + halfBase, centerY + TRIANGLE_HEIGHT / 2
            );

            switch (orientation) {
                case 'N':
                    triangle.setRotate(0);
                    break;
                case 'E':
                    triangle.setRotate(90);
                    break;
                case 'S':
                    triangle.setRotate(180);
                    break;
                case 'W':
                    triangle.setRotate(270);
                    break;
                default:
                    break;
            }

            triangle.setFill(Color.rgb(0, 0, 0, 0.5));
            boardGroup.getChildren().add(triangle);
        }
    }


    private String generateGameState() {
        StringBuilder gameState = new StringBuilder();

        // Add player info
        for (Player player : players) {
            gameState.append(player.toPlayerString());
        }

        // Add Assam info
        gameState.append(assam.toAssamString());

        // Add board info
        gameState.append(board.toBoardString());

        return gameState.toString();
    }


    private void endGame(char winner) {
        switch (winner) {
            case 't':
                System.out.println("The game ended in a tie!");
                break;
            case 'c':
                System.out.println("The cyan player is the winner!");
                break;
            case 'y':
                System.out.println("The yellow player is the winner!");
                break;
            case 'p':
                System.out.println("The purple player is the winner!");
                break;
            case 'r':
                System.out.println("The red player is the winner!");
                break;
            case 'n':
                System.out.println("The game is still ongoing!");
                break;
            default:
                System.out.println("Unexpected result: No valid winner determined!");
                break;
        }
    }
    /**
     * This method is responsible for handling the conclusion of the game.
     * Based on the input character representing the winner or game state,
     * it prints an appropriate message indicating the game's outcome.
     *
     * @param winner a character representing the game's result or the winner's color:
     *               't' - Tie
     *               'c' - Cyan player wins
     *               'y' - Yellow player wins
     *               'p' - Purple player wins
     *               'r' - Red player wins
     *               'n' - Game is ongoing
     *               Any other character indicates an unexpected result.
     */
}

