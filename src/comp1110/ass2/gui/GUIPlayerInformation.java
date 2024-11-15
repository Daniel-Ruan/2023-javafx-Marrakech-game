package comp1110.ass2.gui;

import comp1110.ass2.MarrakechGame;
import javafx.scene.Group;
public class GUIPlayerInformation extends Group {

    MarrakechGame game;
    GUIPlayer[] guiPlayers;

    // Constructor for GUIPlayerInformation
    GUIPlayerInformation(MarrakechGame game) {
        this.game = game;

        // Determine the size of the guiPlayers array based on playerAmount
        guiPlayers = new GUIPlayer[game.playerAmount];

        for (int i = 0; i < game.playerAmount; i++) {
            var guiPlayer = new GUIPlayer(game);
            guiPlayers[i] = guiPlayer;

            // Set the layout positions for each GUIPlayer based on player index
            guiPlayer.setLayoutX((i % 2) * 180 + 200);
            guiPlayer.setLayoutY(40 +(i / 2) * 130 + 50);
        }

        this.getChildren().addAll(guiPlayers);
    }

    // Set player information for each GUIPlayer
    void setPlayers() {
        var len = game.players.length;
        for (int i = 0; i < guiPlayers.length; i++) {
            if (i < len) {
                guiPlayers[i].setPlayer(game.players[i]);
            } else {
                guiPlayers[i].setPlayer(null);
            }
        }
    }

    // Update the visual representation of player information
    void update() {
        for (GUIPlayer guiPlayer : guiPlayers) {
            guiPlayer.update();
        }
    }
}

// This class was written by Huizhe Ruan.  The code annotations was jointly completed by Kechun Ma.