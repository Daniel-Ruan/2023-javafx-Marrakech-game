package comp1110.ass2.gui;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import comp1110.ass2.Assam;
import comp1110.ass2.Board;
import comp1110.ass2.Cell;
import comp1110.ass2.Player;
import comp1110.ass2.IntPair;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Viewer extends Application {

    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;

    private static final int CELL_SIDE_LENGTH = 60;
    private static final int CELL_SIDE_NUMBER = 7;
    private static final int CELL_ID_TEXT_SIZE = 15;
    private static final int TRIANGLE_HEIGHT = 30;
    private final Group root = new Group();
    private final Group controls = new Group();
    private final Group boardGroup = new Group();
    private TextField boardTextField;

    private Circle createPlayerCircle(Color color) {
        Circle circle = new Circle();
        circle.setRadius(20);  // 设置圆形的半径
        circle.setFill(color);  // 设置圆形的颜色
        return circle;
    }

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

    /**
     * Draw a placement in the window, removing any previously drawn placements
     *
     * @param state an array of two strings, representing the current game state
     */
    void displayState(String state) {

        boardGroup.getChildren().clear();  // Clear previous state
        makeControls();  // Add the controls back after clearing

        //处理字符串
        String boardString = Board.extractBoardPart(state);  // Remove the 'B' at the start. String boardString = state.substring(37);
        String assamString = Assam.getContentBetweenAandB(state);
        String[] playerInfoArray = Player.extractPlayerInfo(state);

        //创建实例

        Assam assam = null;
        Board board = new Board();
        board.populateBoardFromString(boardString);

        Player player1 = null;
        Player player2 = null;
        Player player3 = null;
        Player player4 = null;

        if (playerInfoArray != null) {
            // 创建Player实例
            player1 = Player.fromString(playerInfoArray[0]);
            player2 = Player.fromString(playerInfoArray[1]);
            player3 = Player.fromString(playerInfoArray[2]);
            player4 = Player.fromString(playerInfoArray[3]);
        }

        if (assamString != null && assamString.length() >= 3) {
            int x = Character.getNumericValue(assamString.charAt(0));
            int y = Character.getNumericValue(assamString.charAt(1));
            char orientation = assamString.charAt(2);

            // 使用提取的x和y坐标以及方向创建一个新的Assam对象
            IntPair position = new IntPair(x, y);
            assam = new Assam(position, orientation);
        }

        //字串处理Assam，已弃用。
//        char assamX = state.charAt(33); // Assam的x坐标 char assamX = state.charAt(33);
//        char assamY = state.charAt(34); // Assam的y坐标 char assamY = state.charAt(34);
//        char assamO = state.charAt(35); // Assam的朝向 (N, E, S, W) char assamO = state.charAt(35);

//        int ax = Character.getNumericValue(assamX);
//        int ay = Character.getNumericValue(assamY);

        int boardSize = CELL_SIDE_NUMBER * CELL_SIDE_LENGTH;  // 7 cells, each 50x50 pixels
        int boardOffsetX = (VIEWER_WIDTH - boardSize) / 2;
        int boardOffsetY = (VIEWER_HEIGHT - boardSize) / 2;

        int boardRightEdge = boardOffsetX + CELL_SIDE_NUMBER * CELL_SIDE_LENGTH;
        int playerInfoStartY = boardOffsetY;
        int playerInfoGap = 50;  // 两个玩家信息之间的间隔

        //绘制Board

        if (board != null) {
            for (int row = 0; row < CELL_SIDE_NUMBER; row++) {
                for (int col = 0; col < CELL_SIDE_NUMBER; col++) {

                    IntPair position = new IntPair(row, col);
                    Cell cell = board.getCell(position);

                    if (cell == null) {
                        continue; // 如果获取的cell是null（例如，越界），则跳过当前迭代
                    }
                    char colorChar = cell.getColor();
                    int rugID = cell.getRugID();

                    //字串处理Board，已弃用。
//                int index = 3 * (row * CELL_SIDE_NUMBER + col);
//                String cellString = boardString.substring(index, index + 3);
//                char colorChar = cellString.charAt(0);

                    // Create a new Rectangle for each cell, adjusted for centering
                    Rectangle rect = new Rectangle(boardOffsetX + row * CELL_SIDE_LENGTH, boardOffsetY + col * CELL_SIDE_LENGTH, CELL_SIDE_LENGTH, CELL_SIDE_LENGTH);

                    // Determine the color based on the color character
                    Color cellColor = getColorFromChar(colorChar);

                    rect.setFill(cellColor);
                    rect.setStroke(Color.BLACK);  // Set the stroke color to black
                    rect.setStrokeWidth(2);       // Set the stroke width to 2 pixels
                    boardGroup.getChildren().add(rect);  // Add the Rectangle to the board group

                    if (colorChar != 'n') {
                        String cellID = String.format("%02d", rugID); // 格式化rugID以确保它总是两位数
//                    String cellID = cellString.substring(1, 3);  // 获取cellString的后两个字符作为cellID
                        Text idText = new Text(cellID);
                        idText.setFont(new Font(CELL_ID_TEXT_SIZE));
                        idText.setX(boardOffsetX + row * CELL_SIDE_LENGTH + CELL_SIDE_LENGTH / 2 - 10); // Adjusted the -10 to center align the text
                        idText.setY(boardOffsetY + col * CELL_SIDE_LENGTH + CELL_SIDE_LENGTH / 2 + 5); // Adjusted the +5 to center align the text
                        //idText.setTextAlignment(TextAlignment.CENTER);
                        boardGroup.getChildren().add(idText);
                    }
                }
            }
        }

        //绘制Assam

        if (assam != null) {  // 确保Assam对象已经创建了
            IntPair pos = assam.getPosition();  // 从Assam对象中获取位置
            char orientation = assam.getOrientation();  // 从Assam对象中获取方向

            int ax = pos.getX();  // 假设IntPair有一个getX()方法
            int ay = pos.getY();  // 假设IntPair有一个getY()方法

            // ...使用ax, ay, orientation代替原先的assamX, assamY, assamO...


            // 计算三角形的位置
            double centerX = boardOffsetX + (ax * CELL_SIDE_LENGTH) + (CELL_SIDE_LENGTH / 2);
            double centerY = boardOffsetY + (ay * CELL_SIDE_LENGTH) + (CELL_SIDE_LENGTH / 2);

            // 计算三角形底边的一半长度
            double halfBase = TRIANGLE_HEIGHT * Math.sin(Math.PI / 3);

            // 创建等边三角形
            Polygon triangle = new Polygon();
            triangle.getPoints().addAll(
                    centerX, centerY - TRIANGLE_HEIGHT / 2,  // 顶点
                    centerX - halfBase, centerY + TRIANGLE_HEIGHT / 2,  // 左下角
                    centerX + halfBase, centerY + TRIANGLE_HEIGHT / 2   // 右下角
            );
            // 根据Assam的朝向旋转三角形
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

            triangle.setFill(Color.rgb(0, 0, 0, 0.5));  // 设置三角形的颜色，你可以根据需要更改
            boardGroup.getChildren().add(triangle);  // 将三角形添加到boardGroup中

        }

        //绘制Player

        if (player1 != null && player2 != null && player3 != null && player4 != null) {

            Player[] players = {player1, player2, player3, player4};

            for (int i = 0; i < 4; i++) {  // 有4个玩家
                Player currentPlayer = players[i];

                char colorChar = currentPlayer.getColor();
                Color playerColor = getColorFromChar(colorChar);

                // 创建代表玩家的圆形
                Circle playerCircle = createPlayerCircle(playerColor);
                playerCircle.setCenterX(boardRightEdge + 40);  // 设置圆心的X坐标
                playerCircle.setCenterY(playerInfoStartY + i * playerInfoGap);  // 设置圆心的Y坐标

                // 创建一个标签来显示玩家的其他信息（如dirhams数量、剩余的rugs等）
                Label playerInfo = new Label("Dirhams: " + currentPlayer.getDirhams() + ", Rugs: " + currentPlayer.getRugs());
                playerInfo.setLayoutX(boardRightEdge + 70);  // 标签的X坐标
                playerInfo.setLayoutY(playerInfoStartY + i * playerInfoGap - 10);  // 标签的Y坐标（微调以使其与圆形对齐）

                boardGroup.getChildren().addAll(playerCircle, playerInfo);  // 将圆形和标签添加到界面中
            }


        }

        //字串处理Player，已弃用。
//        for (int i = 0; i < 4; i++) {  // 有4个玩家
//            String playerString = state.substring(i * 8, (i + 1) * 8);
//
//            char colorChar = playerString.charAt(1);
//            Color playerColor = getColorFromChar(colorChar);
//
//            // 创建代表玩家的圆形
//            Circle playerCircle = createPlayerCircle(playerColor);
//            playerCircle.setCenterX(boardRightEdge + 40);  // 设置圆心的X坐标
//            playerCircle.setCenterY(playerInfoStartY + i * playerInfoGap);  // 设置圆心的Y坐标
//
//            // 创建一个标签来显示玩家的其他信息（如dirhams数量、剩余的rugs等）
//            Label playerInfo = new Label("Dirhams: " + playerString.substring(2, 5) + ", Rugs: " + playerString.substring(5, 7));
//            playerInfo.setLayoutX(boardRightEdge + 70);  // 标签的X坐标
//            playerInfo.setLayoutY(playerInfoStartY + i * playerInfoGap - 10);  // 标签的Y坐标（微调以使其与圆形对齐）
//
//            boardGroup.getChildren().addAll(playerCircle, playerInfo);  // 将圆形和标签添加到界面中
//        }

        if (!root.getChildren().contains(boardGroup)) {
            root.getChildren().add(boardGroup);  // Add the boardGroup to the root if it's not already there
        }
        // FIXME Task 5: implement the simple state viewer
    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label boardLabel = new Label("Game State:");
        boardTextField = new TextField();
        boardTextField.setPrefWidth(800);
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                displayState(boardTextField.getText());
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(boardLabel,
                boardTextField, button);
        hb.setSpacing(10);
        hb.setLayoutX(50);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Marrakech Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        root.getChildren().add(controls);

        makeControls();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
