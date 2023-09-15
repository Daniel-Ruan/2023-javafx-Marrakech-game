package comp1110.ass2.gui;

import comp1110.ass2.Assam;
import comp1110.ass2.Board;
import comp1110.ass2.Player;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game extends Application {

    private final Group root = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;

    private Player[] players;
    private Assam assam;
    private Board board;

    @Override
    public void start(Stage stage) throws Exception {
        // FIXME Task 7 and 15
        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

//    private void initializeGameState(String gameString) {
//        // Parse gameString to initialize players, assam, and board
//        String playerString = gameString.substring(0, /* length needed for players */);
//        String assamString = gameString.substring(/* start index */, /* end index */);
//        String boardString = gameString.substring(/* start index */);
//
//        // Initialize players, assam, and board using parsed strings
//        this.players = parsePlayers(playerString);
//        this.assam = parseAssam(assamString);
//        this.board = parseBoard(boardString);
//    }

    public String toGameString() {
        // Concatenation of player strings, assam string, and board string
        StringBuilder sb = new StringBuilder();

        for (Player player : players) {
            sb.append(player.toPlayerString());
        }

        sb.append(assam.toAssamString());
        sb.append(board.toBoardString());

        return sb.toString();
    }
}

