package comp1110.ass2;

public class Board {
    public static final int BOARD_SIZE = 7; // 假设棋盘是 7x7
    private Cell[][] board;

    public Board() {
        board = new Cell[BOARD_SIZE][BOARD_SIZE];
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                board[x][y] = new Cell(new IntPair(x, y), 'n', 0); // 初始化为没有地毯
            }
        }
    }

    public void placeRug(IntPair position, char color, int rugID) {
        if (isValidPosition(position)) {
            board[position.getX()][position.getY()].setRug(color, rugID);
        } else {
            // 处理越界尝试
        }
    }

    public Cell getCell(IntPair position) {
        if (isValidPosition(position)) {
            return board[position.getX()][position.getY()];
        } else {
            return null; // 处理越界查询 test
        }
    }

    // 验证给定位置是否在棋盘范围内
    private boolean isValidPosition(IntPair position) {
        return position.getX() >= 0 && position.getX() < BOARD_SIZE &&
                position.getY() >= 0 && position.getY() < BOARD_SIZE;
    }

    public static String extractBoardPart(String gameString) {
        // 查找字符'B'的位置
        int startPos = gameString.indexOf('B');

        // 如果没有找到'B'，返回一个空字符串或null（根据需要）
        if (startPos == -1) return "";

        // 从该位置的下一个字符开始截取字符串到末尾
        return gameString.substring(startPos + 1);
    }

    public String toBoardString() {
        StringBuilder boardString = new StringBuilder("B");  // 开头是 'B'

        // 按照列优先的顺序遍历棋盘
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 7; y++) {
                boardString.append(board[x][y].toRugString());

                // Cell方法toRugString

            }
        }

        return boardString.toString();
    }

    // 其他可能有用的方法，比如输出当前棋盘状态的字符串表示等
}