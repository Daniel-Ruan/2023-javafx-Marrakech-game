package comp1110.ass2.gui;

import comp1110.ass2.MarrakechGame;
import javafx.scene.Group;

public class GUIMarrakech extends Group {

    MarrakechGame game;
    GUIBoard guiBoard;
    GUIPlayerInformation guiPlayerInformation;

    GUIMarrakech(MarrakechGame game) {
        this.game = game;

        guiBoard = new GUIBoard(game);
        guiBoard.setLayoutX(50);
        guiBoard.setLayoutY(50);
        this.getChildren().add(guiBoard);

        guiPlayerInformation = new GUIPlayerInformation(game);
        guiPlayerInformation.setPlayers();
        guiPlayerInformation.setLayoutX(600);
        guiPlayerInformation.setLayoutY(20);
        this.getChildren().add(guiPlayerInformation);
    }

    void update() {
        guiPlayerInformation.update();
        guiBoard.update();
    }
}
