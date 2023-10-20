package comp1110.ass2.gui;


import comp1110.ass2.IntPair;
import comp1110.ass2.MarrakechGame;
import javafx.scene.Group;
import comp1110.ass2.Board;
import comp1110.ass2.Cell;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class GUIBoard extends Group {

    public MarrakechGame game; // Reference to the game instance
    GUICell[][] guiCells; // Array to store GUICell instances
    public static final int CELL_SIZE = 73; // / Size of each cell
    public static final int CELL_GAP = 0;// Gap between cells
    GUIAssam guiAssam;// Represents the Assam on the board
    int highLightDegree;// Degree of highlighting
    IntPair highLightPosition;// Position to highlight
    Rectangle rect = new Rectangle(700, 700);// Background rectangle
    // Constructor for GUIBoard
    public GUIBoard(MarrakechGame game) {
        this.game = game;
        // Create an ImagePattern object and load the game board image resource
        ImagePattern imgPat = new ImagePattern(new Image("file:assets/gameBoard.png"));

        // Create a Rectangle and set the size
        rect.setLayoutX(-50);
        rect.setLayoutY(-50);
        // Fill the Rectangle with the ImagePattern
        rect.setFill(imgPat);

        // Add the Rectangle to the GUIBoard
        this.getChildren().add(rect);
        // Initialize the board and add the Assam
        initializeBoard();
        guiAssam = new GUIAssam(game, game.assam, CELL_SIZE);
        this.getChildren().add(guiAssam);


    }
    // Initialize the GUI board based on the game board
    private void initializeBoard() {
        Board board = game.board; //Get the game board
        guiCells = new GUICell[Board.BOARD_SIZE][Board.BOARD_SIZE]; // Initialize the GUICell array

        for (int col = 0; col < Board.BOARD_SIZE; col++) {
            for (int row = 0; row < Board.BOARD_SIZE; row++) {
                Cell cell = board.board[col][row]; // Get the Cell from the Board
                GUICell guiCell = new GUICell(game, cell, CELL_SIZE, this); // Create a GUICell
                guiCell.setLayoutX(col * (CELL_SIZE + CELL_GAP) + 48); // Set the GUICell's position
                guiCell.setLayoutY(row * (CELL_SIZE + CELL_GAP) + 47);
                guiCells[col][row] = guiCell; // Store the GUICell in the array
                this.getChildren().add(guiCell); // Add the GUICell to the GUIBoard
            }
        }
    }
    // Update the GUI board based on the game state
    void update() {
            System.out.println("board update");
        for (int col = 0; col < Board.BOARD_SIZE; col++) {
            for (int row = 0; row < Board.BOARD_SIZE; row++) {
                guiCells[col][row].update();
            }
        }
        guiAssam.setLayoutX(game.assam.getPosition().getX() * (CELL_SIZE + CELL_GAP) + 48);
        guiAssam.setLayoutY(game.assam.getPosition().getY() * (CELL_SIZE + CELL_GAP) + 47);
        guiAssam.update();
    }
    // Get the GUICell at a specific position
    GUICell getCell (IntPair pos) {
        if (pos == null || pos.getX() < 0 || pos.getX() >= Board.BOARD_SIZE || pos.getY() < 0 || pos.getY() >= Board.BOARD_SIZE) {
            return null;
        }
        return guiCells[pos.getX()][pos.getY()];
    }
    // Get an array of GUICells that are highlighted
    GUICell[] getHighLightCells() {
        if (highLightPosition == null) {
            return new GUICell[0];

        }
        int dx;
        int dy;
        if (highLightDegree == 0) {
            dx = 0;
            dy = -1;
        } else if (highLightDegree == 90) {
            dx = 1;
            dy = 0;
        } else if (highLightDegree == 180) {
            dx = 0;
            dy = 1;
        }else {
            dx = -1;
            dy = 0;
        }

        var pos1 = highLightPosition;
        var pos2 = new IntPair(highLightPosition.getX() + dx, highLightPosition.getY() + dy);
        var guiCell1 = this.getCell(pos1);
        var guiCell2 = this.getCell(pos2);

        if (guiCell2 ==null) return new GUICell[]{guiCell1};
        return new GUICell[]{guiCell1, guiCell2};
    }
    // Set the position to highlight
    void setHighLightPosition(IntPair pos) {
        updateHighlightStatus(false); //Clear the current highlight
        highLightPosition = pos;
        updateHighlightStatus(true); // Apply the new highlight
    }

    void rotateHighLightDegree(int degree) {
        int newDegree = (highLightDegree + degree) % 360;
        this.setHighLightDegree(newDegree);
    }
    //可能有问题

    void setHighLightDegree(int degree) {
        updateHighlightStatus(false); // Clear the current highlight
        highLightDegree = degree;
        updateHighlightStatus(true); // Apply the new highlight

    }

    private void updateHighlightStatus(boolean highlight) {
        for (GUICell guiCell : this.getHighLightCells()) {
            guiCell.setHighLight(highlight);
        }
    }
}
