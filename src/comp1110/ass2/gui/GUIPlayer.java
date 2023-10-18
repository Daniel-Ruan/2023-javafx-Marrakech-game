package comp1110.ass2.gui;

import comp1110.ass2.MarrakechGame;
import comp1110.ass2.Player;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GUIPlayer extends Group {

    MarrakechGame game;
    Rectangle mainRect = new Rectangle(150, 100);
    Rectangle faceRect = new Rectangle(150, 100);
    Circle circle = new Circle(135, 15, 10);
    Text coinText = new Text(10, 30, "dollars: ");
    Text remainRugNumText = new Text(10, 60, "rugs: ");
//    private Text scoreText;
    Player player;

    void setPlayer(Player player) {
        this.player = player;
        var javaColor = player == null ? Color.GRAY : GameTools.getColor(player.getColor());
        mainRect.setStroke(javaColor);
        faceRect.setFill(javaColor);

        coinText.setText("dollars: ");
        remainRugNumText.setText("rugs: ");
//        scoreText.setText("score: ");
    }

    void update() {
        if (player != null) {
            coinText.setText("dollars: " + player.getDirhams());
            remainRugNumText.setText("rugs: " + player.getRugs());
//            scoreText.setText("score: " + player());
            if (player.isActive()) {
                mainRect.setStroke(Color.GRAY);
            }
        }

        var currentPlayer = game.players[game.currentPlayerIndex];
        circle.setVisible(currentPlayer == player);
    }

    GUIPlayer(MarrakechGame game) {
        this.game = game;

        mainRect.setArcHeight(10);
        mainRect.setArcWidth(10);
        mainRect.setStrokeWidth(5);
        mainRect.setFill(Color.WHITE);
        faceRect.setArcHeight(10);
        faceRect.setArcWidth(10);

        circle.setFill(Color.BLACK);

        coinText.setFont(GameTools.font);
        remainRugNumText.setFont(GameTools.font);
//        scoreText.setFont(GameTools.font);
        this.getChildren().addAll(mainRect, faceRect, coinText, remainRugNumText, circle);
    }


}
