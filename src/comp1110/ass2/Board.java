package comp1110.ass2;

public class Board {
    public static final int BOARD_SIZE = 7; // 假设棋盘是 7x7
    public Cell[][] board;

    public Board() {
        board = new Cell[BOARD_SIZE][BOARD_SIZE];
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                board[x][y] = new Cell(new IntPair(x, y), 'n', 0); // 初始化为没有地毯
            }
        }
    }

    public void populateBoardFromString(String boardString) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                int index = 3 * (row * BOARD_SIZE + col);
                String cellString = boardString.substring(index, index + 3);
                char colorChar = cellString.charAt(0);
                int rugID = Integer.parseInt(cellString.substring(1, 3));
                board[row][col].setRug(colorChar, rugID);
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

    public int countConnectedRugs(IntPair position, char color, boolean[][] visited) {
        int x = position.getX();
        int y = position.getY();

        if (x < 0 || x >= BOARD_SIZE || y < 0 || y >= BOARD_SIZE) return 0;
        if (visited[x][y]) return 0;

        Cell cell = getCell(position);
        if (cell.getColor() != color) return 0;

        visited[x][y] = true;

        int count = 1;
        count += countConnectedRugs(new IntPair(x + 1, y), color, visited);  // East
        count += countConnectedRugs(new IntPair(x - 1, y), color, visited);  // West
        count += countConnectedRugs(new IntPair(x, y + 1), color, visited);  // South
        count += countConnectedRugs(new IntPair(x, y - 1), color, visited);  // North

        return count;
    }

    public int getRugCountForColor(char color) {
        int count = 0;

        // 遍历board数组
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                // 如果当前Cell的颜色与给定颜色相匹配，则增加计数
                if (board[i][j].getColor() == color) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * Place a rug string on the board.
     * @param rug The rug string in the format: color + id + x1y1x2y2.
     */
    public void placeRugOnBoard(String rug) {
        // Extract the rug's details.
        char rugColor = rug.charAt(0);
        int rugID = Integer.parseInt(rug.substring(1, 3));

        int rugX1 = Character.getNumericValue(rug.charAt(3));
        int rugY1 = Character.getNumericValue(rug.charAt(4));
        int rugX2 = Character.getNumericValue(rug.charAt(5));
        int rugY2 = Character.getNumericValue(rug.charAt(6));

        // Place the rug cells on the board.
        placeRug(new IntPair(rugX1, rugY1), rugColor, rugID);
        placeRug(new IntPair(rugX2, rugY2), rugColor, rugID);
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