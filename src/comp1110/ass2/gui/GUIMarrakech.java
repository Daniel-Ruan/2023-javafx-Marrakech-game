package comp1110.ass2.gui;

import comp1110.ass2.MarrakechGame;
import javafx.scene.Group;

public class GUIMarrakech extends Group {

    MarrakechGame game;
    GUIBoard guiBoard;
    GUIPlayerInformation guiPlayerInformation;

    // Create a graphical board to represent the game state
    GUIMarrakech(MarrakechGame game) {
        this.game = game;

        guiBoard = new GUIBoard(game);
        guiBoard.setLayoutX(50);
        guiBoard.setLayoutY(50);
        this.getChildren().add(guiBoard);

        // Create an interface for displaying player information
        guiPlayerInformation = new GUIPlayerInformation(game);
        guiPlayerInformation.setPlayers();
        guiPlayerInformation.setLayoutX(600);
        guiPlayerInformation.setLayoutY(20);
        this.getChildren().add(guiPlayerInformation);
    }

    // Update the GUI components with the current game state
    void update() {
        guiPlayerInformation.update();
        guiBoard.update();
    }
}
// This class was written by Huizhe Ruan.  The code annotations was jointly completed by Kechun Ma.
