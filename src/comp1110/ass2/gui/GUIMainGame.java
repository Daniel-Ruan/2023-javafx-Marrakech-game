package comp1110.ass2.gui;

import comp1110.ass2.Cell;
import comp1110.ass2.Marrakech;
import comp1110.ass2.MarrakechGame;
import comp1110.ass2.Rug;
import javafx.scene.Group;

public class GUIMainGame extends Group {

    public MarrakechGame game;
    public GUIMarrakech guiMarrakech;

    public GUIMainGame(MarrakechGame game, double windowWidth, double windowHeight) {

        this.game = game;
        guiMarrakech = new GUIMarrakech(game);
        guiMarrakech.update();
        this.getChildren().add(guiMarrakech);

        // Handle scrolling for rotating the view
        this.setOnScroll(event -> {
            if (event.getDeltaY() > 0) { // Scroll Up
                if (game.phase == 0) {
                    game.assam.changeAngle(-90);
                    guiMarrakech.guiBoard.update();
                } else if (game.phase == 2) {
                    guiMarrakech.guiBoard.rotateHighLightDegree(-90);
                }
            } else if (event.getDeltaY() < 0) { // Scroll Down
                if (game.phase == 0) {
                    game.assam.changeAngle(90);
                    guiMarrakech.guiBoard.update();
                } else if (game.phase == 2) {
                    guiMarrakech.guiBoard.rotateHighLightDegree(90);
                }
            }
        });
        this.setOnMouseClicked(event -> {
            if (game.phase == 0) {
                if (Marrakech.isGameOver(game.generateGameState())) {
                    game.phase = -1;
                    return;
                }

                if (game.assam.isCurrentAngleValid()) {
                    game.assam.updateOrientationFromAngle();
                    String playerMoveTips = "Click again to move.";
                    GUIAlertInformation alertplayerMoveTips = new GUIAlertInformation(playerMoveTips, windowWidth, windowHeight);
                    this.getChildren().add(alertplayerMoveTips);
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
                        String alertResetInformation = "Failed to place, please re-place";
                        GUIAlertInformation alertPlayerTips = new GUIAlertInformation(alertResetInformation, windowWidth, windowHeight);
                        this.getChildren().add(alertPlayerTips);
                    }
                }
            }else {
                char winner = Marrakech.getWinner(game.generateGameState());
                String winnerInformation = "Game over, winner is " + winner +" color player.";
                GUIAlertInformation alertwinnerInformation = new GUIAlertInformation(winnerInformation, windowWidth, windowHeight);
                this.getChildren().add(alertwinnerInformation);
                System.out.println("Game over, winner is " + winner +" color player");
            }
            guiMarrakech.update();
        });



    }
}

// This class was written by Huizhe Ruan. The code annotations were jointly completed by Anbo Wu and Kechun Ma.
