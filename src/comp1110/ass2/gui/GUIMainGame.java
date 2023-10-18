package comp1110.ass2.gui;
import comp1110.ass2.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;

public class GUIMainGame extends Group {

    public MarrakechGame game;
    public GUIMarrakech guiMarrakech;

    public GUIMainGame(MarrakechGame game, double windowWidth, double windowHeight) {
        this.setFocusTraversable(true);
        this.game = game;
        guiMarrakech = new GUIMarrakech(game);
        guiMarrakech.update();
        this.getChildren().add(guiMarrakech);

        this.setOnMouseClicked(event -> {
            if (game.phase == 0) {
                if (Marrakech.isGameOver(game.generateGameState())) {
                    game.phase = -1;
                    return;
                }

                if (game.assam.isCurrentAngleValid()) {
                    game.assam.updateOrientationFromAngle();
                    game.phase = 1;
                }
            } else if (game.phase == 1) {
                var step = Marrakech.rollDie();
                String alertMoveInformation = "Current player moves " + step + " steps.";
                game.moveAssamInGame(step);
                GUIAlertInformation alertStep = new GUIAlertInformation(alertMoveInformation, windowWidth, windowHeight);
                this.getChildren().add(alertStep);

                var currentPlayer = game.players[game.currentPlayerIndex];
                var color = game.board.getCell(game.assam.getPosition()).getColor();
                var payer = game.getPlayer(color);
                int dollar = Marrakech.getPaymentAmount(game.generateGameState());
//                System.out.println("Pay " + dollar);
                if (payer != null && payer != currentPlayer) {
                    if (payer.isActive()) {
                        game.payTo(currentPlayer, dollar);
                        payer.addDirhams(dollar);
                        String alertPaymentInformation = "player " + currentPlayer.getColor() + " paid " + dollar + " dirhams.";
                        GUIAlertInformation alertPayment = new GUIAlertInformation(alertPaymentInformation, windowWidth, windowHeight);
                        this.getChildren().add(alertPayment);

                    }
                }
                guiMarrakech.guiBoard.update();
                if (!currentPlayer.isActive()) {
                    game.phase =0;
                    game.turnNext();
                } else {
                    game.phase =2;
//                    System.out.println("finish phase 1 click Player: " + game.currentPlayerIndex);
                }
            } else if (game.phase == 2) {
                var guiCells = guiMarrakech.guiBoard.getHighLightCells();
                if (guiCells.length == 2) {
                    var player = game.players[game.currentPlayerIndex];
                    var newCell1 = new Cell(guiCells[0].cell.getPosition(),player.getColor(), 15 - player.getRugs());
                    var newCell2 = new Cell(guiCells[1].cell.getPosition(),player.getColor(), 15 - player.getRugs());
                    var rug = new Rug(newCell1, newCell2);

                    if (game.isPlacedAllowed(rug)) {
                        game.makePlacementInGame(rug);
                        game.phase = 0;
                        game.turnNext();
                    } else {
                        String alertResetInformation = "Failed to place, please re-place in appropriate";
                        GUIAlertInformation alertPlayerTips = new GUIAlertInformation(alertResetInformation, windowWidth, windowHeight);
                        this.getChildren().add(alertPlayerTips);
//                        System.out.println("Placement failure " + game.generateGameState());
                    }
                }
            }else {
                char winner = Marrakech.getWinner(game.generateGameState());
                String winnerInformation = "Game over, winner is " + winner +" color player.";
                GUIAlertInformation alertwinnerInformation = new GUIAlertInformation(winnerInformation, windowWidth, windowHeight);
                this.getChildren().add(alertwinnerInformation);
//                System.out.println("game over, winner is " + winner +" color player");
            }
            guiMarrakech.update();
        });

        this.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.RIGHT) { // 当按下右方向键时
                if (game.phase == 0) {
                    game.assam.changeAngle(-270);
                    guiMarrakech.guiBoard.update();
                } else if (game.phase == 2) {
                    guiMarrakech.guiBoard.rotateHighLightDegree(90);
                }
            } else if (event.getCode() == KeyCode.LEFT) {
                if (game.phase == 0) {
                    game.assam.changeAngle(-90);
                    guiMarrakech.guiBoard.update();
                } else if (game.phase == 2) {
                    guiMarrakech.guiBoard.rotateHighLightDegree(-90);
                }
            }
            // 可以为其他方向键添加更多的操作，例如使用KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT等
        });

    }
}
