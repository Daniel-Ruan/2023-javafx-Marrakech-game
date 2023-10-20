package comp1110.ass2;

public class Board {
    public static final int BOARD_SIZE = 7; // Assuming the board is 7x7
    public Cell[][] board;

    public Board() {
        //Initialize the board with empty cells
        board = new Cell[BOARD_SIZE][BOARD_SIZE];
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                board[x][y] = new Cell(new IntPair(x, y), 'n', 0);
                // Initialize as no rug
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
                // Populate the board based on a string representation
            }
        }
    }

    // Place a rug on the board at the specified position
    public void placeRug(IntPair position, char color, int rugID) {
        if (isValidPosition(position)) {
            board[position.getX()][position.getY()].setRug(color, rugID);
        } else {
            // Handle out-of-bounds attempt
        }
    }

    // Get the cell at the specified position on the board
    public Cell getCell(IntPair position) {
        if (isValidPosition(position)) {
            return board[position.getX()][position.getY()];
        } else {
            return null; // Handle out-of-bounds query (处理越界查询 test)
        }
    }

    // Validate if a given position is within the board's boundaries
    private boolean isValidPosition(IntPair position) {
        return position.getX() >= 0 && position.getX() < BOARD_SIZE &&
                position.getY() >= 0 && position.getY() < BOARD_SIZE;
    }

    // Count the number of connected rugs of a specific color
    public int countConnectedRugs(IntPair position, char color, boolean[][] visited) {
        // Recursive depth-first search to count connected rugs
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

    // Get the count of rugs of a specific color on the board
    public int getRugCountForColor(char color) {
        int count = 0;

        // Iterate through the board array
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                // If the current Cell's color matches the given color, increment the count
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

    // Extract the board part from a game string
    public static String extractBoardPart(String gameString) {
        // Find the position of 'B' character
        int startPos = gameString.indexOf('B');

        // If 'B' is not found, return an empty string or null (as needed)
        if (startPos == -1) return "";

        // Extract the substring starting from the position of 'B' + 1
        return gameString.substring(startPos + 1);
    }

    // Generate a string representation of the current board state
    public String toBoardString() {
        StringBuilder boardString = new StringBuilder("B");  // The string starts with 'B'(开头是 'B')

        // Iterate through the board in column-major order
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 7; y++) {
                boardString.append(board[x][y].toRugString());
                // Utilize the Cell method toRugString
            }
        }
        return boardString.toString();
    }
}