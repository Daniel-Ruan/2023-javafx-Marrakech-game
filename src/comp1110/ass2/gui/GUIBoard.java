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

    public MarrakechGame game; // 游戏实例
    GUICell[][] guiCells; // 用于存储GUICell的数组
    public static final int CELL_SIZE = 73; // 假设每个单元格的尺寸为50，你可以根据需要修改这个值
    public static final int CELL_GAP = 0;
    GUIAssam guiAssam;
    int highLightDegree;
    IntPair highLightPosition;
    Rectangle rect = new Rectangle(700, 700);

    public GUIBoard(MarrakechGame game) {
        this.game = game;
        // 1. 创建一个ImagePattern对象并加载图片资源
        ImagePattern imgPat = new ImagePattern(new Image("file:assets/gameBoard.png"));

        // 2. 创建一个Rectangle对象并设置它的尺寸
        rect.setLayoutX(-50);
        rect.setLayoutY(-50);
        // 3. 使用ImagePattern填充Rectangle
        rect.setFill(imgPat);

        // 4. 将Rectangle添加到GUIBoard中
        this.getChildren().add(rect);

        initializeBoard();
        guiAssam = new GUIAssam(game, game.assam, CELL_SIZE);
        this.getChildren().add(guiAssam);


    }

    private void initializeBoard() {
        Board board = game.board; // 从游戏实例中获取Board
        guiCells = new GUICell[Board.BOARD_SIZE][Board.BOARD_SIZE]; // 初始化GUICell数组

        for (int col = 0; col < Board.BOARD_SIZE; col++) {
            for (int row = 0; row < Board.BOARD_SIZE; row++) {
                Cell cell = board.board[col][row]; // 从Board获取Cell
                GUICell guiCell = new GUICell(game, cell, CELL_SIZE, this); // 创建GUICell
                guiCell.setLayoutX(col * (CELL_SIZE + CELL_GAP) + 48); // 设置GUICell的位置
                guiCell.setLayoutY(row * (CELL_SIZE + CELL_GAP) + 47);
                guiCells[col][row] = guiCell; // 将GUICell存储在数组中
                this.getChildren().add(guiCell); // 将GUICell添加到GUIBoard
            }
        }
    }

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

    GUICell getCell (IntPair pos) {
        if (pos == null || pos.getX() < 0 || pos.getX() >= Board.BOARD_SIZE || pos.getY() < 0 || pos.getY() >= Board.BOARD_SIZE) {
            return null;
        }
        return guiCells[pos.getX()][pos.getY()];
    }
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
    void setHighLightPosition(IntPair pos) {
        updateHighlightStatus(false); // 取消当前高亮
        highLightPosition = pos;
        updateHighlightStatus(true); // 应用新高亮
    }

    void rotateHighLightDegree(int degree) {
        int newDegree = (highLightDegree + degree) % 360;
        this.setHighLightDegree(newDegree);
    }
    //可能有问题

    void setHighLightDegree(int degree) {
        updateHighlightStatus(false); // 取消当前高亮
        highLightDegree = degree;
        updateHighlightStatus(true); // 应用新高亮

    }

    private void updateHighlightStatus(boolean highlight) {
        for (GUICell guiCell : this.getHighLightCells()) {
            guiCell.setHighLight(highlight);
        }
    }


}