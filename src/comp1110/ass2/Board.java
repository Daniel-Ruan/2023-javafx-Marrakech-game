package comp1110.ass2;

public class Board {
    // The board dimensions are fixed at 7x7.
    private static final int BOARD_SIZE = 7;

    // 2D array to hold the abbreviated rug Strings for each square on the board.
    private String[][] board;

    /**
     * Constructs a new empty Board.
     */
    public Board() {
        board = new String[BOARD_SIZE][BOARD_SIZE];
        // Initialize the board with the special null rug string 'n00'.
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = "n00";
            }
        }
    }

    /**
     * Sets the rug at a given position on the board.
     *
     * @param x the x-coordinate of the position
     * @param y the y-coordinate of the position
     * @param rugAbbrev the abbreviated rug String to set
     */
    public void setRugAt(int x, int y, String rugAbbrev) {
        board[x][y] = rugAbbrev;
    }

    /**
     * Generates the board string according to the given format.
     *
     * @return the board string
     */
    public String toBoardString() {
        StringBuilder sb = new StringBuilder("B");
        // Loop through the board in column-major order.
        for (int j = 0; j < BOARD_SIZE; j++) {
            for (int i = 0; i < BOARD_SIZE; i++) {
                sb.append(board[i][j]);
            }
        }
        return sb.toString();
    }

    // Other methods
}
