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

public class Game extends Application {

    private final Group root = new Group();
    private final Group gameStatusGroup = new Group();
    private final Group boardGroup = new Group();
    private final Object lock = new Object();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;
    private static final int TILE_SIZE = 80;
    private static final int LABEL_WIDTH = 300;
    private static final int CELL_SIDE_LENGTH = 60;
    private static final int CELL_SIDE_NUMBER = 7;
    private static final int CELL_ID_TEXT_SIZE = 15;
    private static final int TRIANGLE_HEIGHT = 30;
    private static final char[] PLAYER_COLORS = {'c', 'y', 'p', 'r'};  // Declare it as a class constant.


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
    // 示例检查方法
    private boolean checkAssamTurnCompleted() {
        // 逻辑判断Assam是否已旋转
        return false;  // 示例返回值
    }

    private boolean checkAssamMoveCompleted() {
        // 逻辑判断Assam是否已移动
        return false;  // 示例返回值
    }

    private boolean checkRugPlacementCompleted() {
        // 逻辑判断Rug是否已放置
        return false;  // 示例返回值
    }
    @Override
    public void start(Stage stage) throws Exception {
        // FIXME Task 7 and 15
        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);

        game = new MarrakechGame(4);
        guiMarrakech = new GUIMarrakech(game);
        guiMarrakech.update();
        root.getChildren().add(guiMarrakech);


        scene.setOnMouseClicked(event -> {
            if (game.phase == 0) {
                if (Marrakech.isGameOver(game.generateGameState())) {
                    game.phase = -1;
                    return;
                }
                System.out.println("phase 0 click");
                if (game.assam.isCurrentAngleValid()) {
                    game.assam.updateOrientationFromAngle();
                    game.phase = 1;
                    System.out.println("finish phase 0 click. gameString: " + game.generateGameState());
                }
            } else if (game.phase == 1) {
                System.out.println("phase 1 click");
                var step = Marrakech.rollDie();
                game.moveAssamInGame(step);
                System.out.println("1 gameString: " + game.generateGameState());
                var currentPlayer = game.players[game.currentPlayerIndex];
                var color = game.board.getCell(game.assam.getPosition()).getColor();
                System.out.println("phase 1 click payer color: " + color);
                var payer = game.getPlayer(color);
                int dollar = Marrakech.getPaymentAmount(game.generateGameState());
                System.out.println("Pay " + dollar);
                if (payer != null && payer != currentPlayer) {
                    if (payer.isActive()) {
                        game.payTo(currentPlayer, dollar);
                        payer.addDirhams(dollar);
                    }
                }
                guiMarrakech.guiBoard.update();
                if (!currentPlayer.isActive()) {
                    game.phase =0;
                    game.turnNext();
                } else {
                    game.phase =2;
                    System.out.println("finish phase 1 click Player: " + game.currentPlayerIndex);
                }
            } else if (game.phase == 2) {
                System.out.println("phase 2 click");
                var guiCells = guiMarrakech.guiBoard.getHighLightCells();
                if (guiCells.length == 2) {
                    var player = game.players[game.currentPlayerIndex];
//                    var position = new IntPair[]{guiCells[0].cell.getPosition(), guiCells[1].cell.getPosition()};
                    var newCell1 = new Cell(guiCells[0].cell.getPosition(),player.getColor(), 15 - player.getRugs());
                    var newCell2 = new Cell(guiCells[1].cell.getPosition(),player.getColor(), 15 - player.getRugs());
                    var rug = new Rug(newCell1, newCell2);
                    System.out.println("player" + player.toPlayerString());
                    System.out.println("cell1" + newCell1.getPosition().toString());
                    System.out.println("cell2" + newCell2.getPosition().toString());
                    System.out.println("Rug" + Rug.toRugString(rug));
                    System.out.println("phase 2 click bool" + game.isPlacedAllowed(rug));
                    System.out.println("phase 2 click isPlacementValid" + Marrakech.isPlacementValid(game.generateGameState(), Rug.toRugString(rug)));
                    System.out.println("phase 2 click isRugValid" + Marrakech.isRugValid(game.generateGameState(), Rug.toRugString(rug)));
                    System.out.println("phase 2 click" + game.isPlacedAllowed(rug));

                    if (game.isPlacedAllowed(rug)) {
                        game.makePlacementInGame(rug);
                        game.phase = 0;
                        game.turnNext();
//                        System.out.println("phase 2 click player " + game.currentPlayerIndex);
                    } else {
                        System.out.println("phase 2 click player " + game.generateGameState());

                    }

                }

            }else {
                System.out.println("game over");
            }
            guiMarrakech.update();


        });

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.RIGHT) { // 当按下右方向键时
                if (game.phase == 0) {
                    game.assam.changeAngle(90);
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

//        scene.setOnScroll(event -> {
//            if (game.phase == 0) {
//                game.assam.rotate(90);
//                guiMarrakech.guiBoard.update();
//            } else if (game.phase == 2) {
//                guiMarrakech.guiBoard.rotateHighLightDegree(90);
//
//            }
//        });

        stage.setScene(scene);
        stage.setTitle("Marrakech game");
//        showPlayerSelection();  // 显示玩家选择界面
        stage.show();

    }

    private void showPlayerSelection() {
        javafx.scene.control.Button btn2 = new javafx.scene.control.Button("2 Players");
        btn2.setLayoutX(WINDOW_WIDTH/2 - 150);
        btn2.setLayoutY(WINDOW_HEIGHT/2);
        btn2.setOnAction(e -> startGame(2));

        javafx.scene.control.Button btn3 = new javafx.scene.control.Button("3 Players");
        btn3.setLayoutX(WINDOW_WIDTH/2 - 50);
        btn3.setLayoutY(WINDOW_HEIGHT/2);
        btn3.setOnAction(e -> startGame(3));

        javafx.scene.control.Button btn4 = new javafx.scene.control.Button("4 Players");
        btn4.setLayoutX(WINDOW_WIDTH/2 + 50);
        btn4.setLayoutY(WINDOW_HEIGHT/2);
        btn4.setOnAction(e -> startGame(4));

        root.getChildren().addAll(btn2, btn3, btn4);
    }

    private void updateAssamStatusInGameGroup(javafx.scene.layout.VBox box) {
        javafx.scene.control.Label assamLabel = new javafx.scene.control.Label(
                "Assam Position: (" + assam.getPosition().getX() + ", " + assam.getPosition().getY() + "), Orientation: " + assam.getOrientation());
        box.getChildren().add(assamLabel);
    }

    private void updateBoardStatusInGameGroup(javafx.scene.layout.VBox box) {
        String boardString = board.toBoardString();  // 获取棋盘的字符串表示

        javafx.scene.control.Label boardLabel = new javafx.scene.control.Label("Board: " + boardString);  // 使用该字符串更新标签
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
        arc.setStartAngle(arcStart);  // 从3点钟方向开始
        arc.setLength(arcEnd);  // 使用传入的arcLength值
        arc.setType(ArcType.OPEN);  // 仅绘制轮廓，不填充
        arc.setStroke(Color.BLACK);
        arc.setStrokeWidth(2);
        arc.setFill(Color.TRANSPARENT);
        boardGroup.getChildren().add(arc);
    }

    private void drawBoardCircles(Group boardGroup, double boardOffsetX, double boardOffsetY) {
        // 在(0, 0)和(0, 1)之间的上边缘绘制半圆
        double halfCircleCenterX = boardOffsetX + CELL_SIDE_LENGTH;
        double halfCircleCenterY = boardOffsetY;
        drawArc(boardGroup, halfCircleCenterX, halfCircleCenterY, 0.5 * CELL_SIDE_LENGTH, 0, 180);

        // 在(0, 2)和(0, 3)之间的上边缘绘制半圆
        halfCircleCenterX += CELL_SIDE_LENGTH;  // Move to the next two cells
        halfCircleCenterX += CELL_SIDE_LENGTH;  // Move to the next two cells
        drawArc(boardGroup, halfCircleCenterX, halfCircleCenterY, 0.5 * CELL_SIDE_LENGTH, 0, 180);

        // 在(0, 4)和(0, 5)之间的上边缘绘制半圆
        halfCircleCenterX += CELL_SIDE_LENGTH;  // Move to the next two cells
        halfCircleCenterX += CELL_SIDE_LENGTH;  // Move to the next two cells
        drawArc(boardGroup, halfCircleCenterX, halfCircleCenterY, 0.5 * CELL_SIDE_LENGTH, 0, 180);

        // 在(1, 7)和(2, 7)之间的下边缘绘制半圆
        halfCircleCenterX = boardOffsetX + CELL_SIDE_LENGTH * 6.0; // Start from the middle of (1,7) and (2,7)
        halfCircleCenterY = boardOffsetY + CELL_SIDE_NUMBER * CELL_SIDE_LENGTH;  // Move it to the bottom edge
        drawArc(boardGroup, halfCircleCenterX, halfCircleCenterY, 0.5 * CELL_SIDE_LENGTH, 180, 180);

        // 在(3, 7)和(4, 7)之间的下边缘绘制半圆
        halfCircleCenterX -= CELL_SIDE_LENGTH * 2;  // Move to the previous two cells
        drawArc(boardGroup, halfCircleCenterX, halfCircleCenterY, 0.5 * CELL_SIDE_LENGTH, 180, 180);

        // 在(5, 7)和(6, 7)之间的下边缘绘制半圆
        halfCircleCenterX -= CELL_SIDE_LENGTH * 2;  // Move to the previous two cells
        drawArc(boardGroup, halfCircleCenterX, halfCircleCenterY, 0.5 * CELL_SIDE_LENGTH, 180, 180);

        // 左边缘的半圆绘制

        // 在(0, 1)和(1, 1)之间的左边缘绘制半圆
        halfCircleCenterX = boardOffsetX;
        halfCircleCenterY = boardOffsetY + CELL_SIDE_LENGTH * 1.0;  // 1.5 represents the middle between two cells
        drawArc(boardGroup, halfCircleCenterX, halfCircleCenterY, 0.5 * CELL_SIDE_LENGTH, 90, 180);

        // 在(0, 3)和(1, 3)之间的左边缘绘制半圆
        halfCircleCenterY += CELL_SIDE_LENGTH * 2;  // Move to the next two cells
        drawArc(boardGroup, halfCircleCenterX, halfCircleCenterY, 0.5 * CELL_SIDE_LENGTH, 90, 180);

        // 在(0, 5)和(1, 5)之间的左边缘绘制半圆
        halfCircleCenterY += CELL_SIDE_LENGTH * 2;  // Move to the next two cells
        drawArc(boardGroup, halfCircleCenterX, halfCircleCenterY, 0.5 * CELL_SIDE_LENGTH, 90, 180);

        // 在(6, 1)和(7, 1)之间的右边缘绘制半圆
        halfCircleCenterX = boardOffsetX + CELL_SIDE_LENGTH * 7;  // Move to the rightmost edge
        halfCircleCenterY = boardOffsetY + CELL_SIDE_LENGTH * 2.0;  // Reset the Y position
        drawArc(boardGroup, halfCircleCenterX, halfCircleCenterY, 0.5 * CELL_SIDE_LENGTH, -90, 180);

        // 在(6, 3)和(7, 3)之间的右边缘绘制半圆
        halfCircleCenterY += CELL_SIDE_LENGTH * 2;  // Move to the next two cells
        drawArc(boardGroup, halfCircleCenterX, halfCircleCenterY, 0.5 * CELL_SIDE_LENGTH, -90, 180);

        // 在(6, 5)和(7, 5)之间的右边缘绘制半圆
        halfCircleCenterY += CELL_SIDE_LENGTH * 2;  // Move to the next two cells
        drawArc(boardGroup, halfCircleCenterX, halfCircleCenterY, 0.5 * CELL_SIDE_LENGTH, -90, 180);

        // 在Board的右上角为圆心绘制270度的圆
        double arcCenterX = boardOffsetX + CELL_SIDE_LENGTH * CELL_SIDE_NUMBER;
        double arcCenterY = boardOffsetY;
        drawArc(boardGroup, arcCenterX, arcCenterY, 0.5 * CELL_SIDE_LENGTH, -90, 270);

        // 在Board的左下角为圆心绘制270度的圆
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
            root.getChildren().add(boardGroup); // 添加boardGroup到root
        }

        // 绘制Assam
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


        private void displayInfo() {

            javafx.scene.layout.VBox leftInfoBox = new javafx.scene.layout.VBox(10);
            leftInfoBox.setLayoutX(10);
            leftInfoBox.setLayoutY(10);
            updateGameStateInGameGroup(leftInfoBox);  // 新增的方法调用
            updateAssamStatusInGameGroup(leftInfoBox);
            updateBoardStatusInGameGroup(leftInfoBox);
            root.getChildren().add(leftInfoBox);

            javafx.scene.layout.VBox playerInfoBox = new javafx.scene.layout.VBox(10);
            int totalPlayerInfoHeight = (players.length * 30) + (players.length - 1) * 10;
            playerInfoBox.setLayoutX(WINDOW_WIDTH - LABEL_WIDTH - 10);
            playerInfoBox.setLayoutY(WINDOW_HEIGHT - totalPlayerInfoHeight - 10);
            updatePlayerStatusInGameGroup(players, playerInfoBox);
            root.getChildren().add(playerInfoBox);
    }

    private String generateGameState() {
        StringBuilder gameState = new StringBuilder();

        // 添加玩家信息
        for (Player player : players) {
            gameState.append(player.toPlayerString());
        }

        // 添加Assam信息
        gameState.append(assam.toAssamString());

        // 添加棋盘信息
        gameState.append(board.toBoardString());

        return gameState.toString();
    }

    public void playGame() throws InterruptedException {
        while (!Marrakech.isGameOver(generateGameState())) {
            for (Player player : players) {
                isTurnedAssam = false;
                hasMoved = false;
                hasPlacedRug = false;
                if (player.isActive()) {
                    playerTurn(player);
                }
            }
        }
        char winner = Marrakech.getWinner(generateGameState());
        endGame(winner);
    }

    private void playerTurn(Player player) throws InterruptedException {
        synchronized (lock) {
//            rotateAssam(player);
            while (!isTurnedAssam) {
                lock.wait();
            }

//            moveAssam(player);
            while (!hasMoved) {
                lock.wait();
            }

//            placeRug(player);
            while (!hasPlacedRug) {
                lock.wait();
            }
        }
    }

    private void rotateAssam(Player player) {
        // Logic for rotating Assam
        // Once done, set player's isTurnedAssam to true and notify
        synchronized (lock) {
            // ... logic ...
            isTurnedAssam = false;
            lock.notifyAll();
        }
    }

    private void moveAssam(Player player) {
        // Logic for moving Assam
        // Once done, set player's hasMoved to true and notify
        synchronized (lock) {
            // ... logic ...
            hasMoved = false;
            lock.notifyAll();
        }
    }

    private void placeRug(Player player) {
        // Logic for placing rug
        // Once done, set player's hasPlacedRug to true and notify
        synchronized (lock) {
            // ... logic ...
            hasPlacedRug = false;
            lock.notifyAll();
        }
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



    private void startGame(int numPlayers) {
        // private variable to store the number of players.
        root.getChildren().clear();

        boardGroup.getChildren().clear();  // 清空boardGroup
        gameStatusGroup.getChildren().clear();  // 清空gameStatusGroup

        this.players = new Player[4];  // 总是实例化四个玩家

        for (int i = 0; i < 4; i++) {
            // 如果当前索引小于玩家数量，则设置isActive为true，否则为false
            boolean isActive = i < numPlayers;
            players[i] = new Player(PLAYER_COLORS[i], 30, 15, isActive);
        }

        this.assam = new Assam(new IntPair(3, 3), 'N');
        this.board = new Board();
        initializeGame();
//        startActualGame();
    }

    private void initializeGame() {
        displayInfo();
        drawBoard(this.board);  // 使用drawBoard绘制刚刚实例化的Board

    }



    public void startActualGame() {
        try {
            playGame();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Game was interrupted!");
        }
    }

}

