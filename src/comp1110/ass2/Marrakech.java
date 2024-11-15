package comp1110.ass2;

public class Marrakech {

    /**
     * Determine whether a rug String is valid.
     * For this method, you need to determine whether the rug String is valid, but do not need to determine whether it
     * can be placed on the board (you will determine that in Task 10 ). A rug is valid if and only if all the following
     * conditions apply:
     *  - The String is 7 characters long
     *  - The first character in the String corresponds to the colour character of a player present in the game
     *  - The next two characters represent a 2-digit ID number
     *  - The next 4 characters represent coordinates that are on the board
     *  - The combination of that ID number and colour is unique
     * To clarify this last point, if a rug has the same ID as a rug on the board, but a different colour to that rug,
     * then it may still be valid. Obviously multiple rugs are allowed to have the same colour as well so long as they
     * do not share an ID. So, if we already have the rug c013343 on the board, then we can have the following rugs
     *  - c023343 (Shares the colour but not the ID)
     *  - y013343 (Shares the ID but not the colour)
     * But you cannot have c014445, because this has the same colour and ID as a rug on the board already.
     * @param gameString A String representing the current state of the game as per the README
     * @param rug A String representing the rug you are checking
     * @return true if the rug is valid, and false otherwise.
     */
    // Check if the rug is valid based on specific criteria.
    public static boolean isRugValid(String gameString, String rug) {
        if (rug.length() != 7) return false;
        char color = rug.charAt(0);
        if (!Player.isValidColor(color)) return false;

        String idStr = rug.substring(1, 3);

        if (!idStr.matches("\\d{2}")) {
            return false;
        }

        String coordinates = rug.substring(3);
        int x1 = Character.getNumericValue(coordinates.charAt(0));
        int y1 = Character.getNumericValue(coordinates.charAt(1));
        int x2 = Character.getNumericValue(coordinates.charAt(2));
        int y2 = Character.getNumericValue(coordinates.charAt(3));

        IntPair pair1 = new IntPair(x1, y1);
        IntPair pair2 = new IntPair(x2, y2);

        if (!pair1.isValidForBoard() || !pair2.isValidForBoard()) {
            return false;
        }

        String boardString = Board.extractBoardPart(gameString);

        String rugString = rug.substring(0, 3);

        // Split boardString into an array of strings for each cell
        int cellCount = boardString.length() / 3;
        String[] cells = new String[cellCount];
        for (int i = 0; i < cellCount; i++) {
            cells[i] = boardString.substring(i * 3, i * 3 + 3);
        }

        // Walk through the set, checking for any grids that match the rugString
        for (String cell : cells) {
            if (cell.equals(rugString)) {
                return false;
            }
        }

        return true;

        // FIXME: Task 4
    }

    /**
     * Roll the special Marrakech die and return the result.
     * Note that the die in Marrakech is not a regular 6-sided die, since there
     * are no faces that show 5 or 6, and instead 2 faces that show 2 and 3. That
     * is, of the 6 faces
     *  - One shows 1
     *  - Two show 2
     *  - Two show 3
     *  - One shows 4
     * As such, in order to get full marks for this task, you will need to implement
     * a die where the distribution of results from 1 to 4 is not even, with a 2 or 3
     * being twice as likely to be returned as a 1 or 4.
     * @return The result of the roll of the die meeting the criteria above
     */
    public static int rollDie() {
        // Roll the special Marrakech die with a specific distribution.
        SpecialDie specialDie = new SpecialDie();
        return specialDie.roll();
        // FIXME: Task 6

    }

    /**
     * Determine whether a game of Marrakech is over
     * Recall from the README that a game of Marrakech is over if a Player is about to enter the rotation phase of their
     * turn, but no longer has any rugs. Note that we do not encode in the game state String whose turn it is, so you
     * will have to think about how to use the information we do encode to determine whether a game is over or not.
     * @param currentGame A String representation of the current state of the game.
     * @return true if the game is over, or false otherwise.
     */
    public static boolean isGameOver(String currentGame) {
        // Check if the game is over based on the conditions provided in the README.

        String[] playerInfoArray = Player.extractPlayerInfo(currentGame);
        boolean allRugsPlaced = true;

        for (String playerInfo : playerInfoArray) {
            Player player = Player.fromString(playerInfo);
            int dirhams = player.getDirhams();
            int rugs = player.getRugs();

            // If a player's dirhams are 0, the number of rugs for that player is not taken into account
            if(dirhams > 0 && rugs > 0) {
                // If there are still dirhams and rugs left, the game is not over
                allRugsPlaced = false;
                break;
            }
        }
        return allRugsPlaced;  // If all rugs are placed, gave over
        // FIXME: Task 8
    }

    /**
     * Implement Assam's rotation.
     * Recall that Assam may only be rotated left or right, or left alone -- he cannot be rotated a full 180 degrees.
     * For example, if he is currently facing North (towards the top of the board), then he could be rotated to face
     * East or West, but not South. Assam can also only be rotated in 90 degree increments.
     * If the requested rotation is illegal, you should return Assam's current state unchanged.
     * @param currentAssam A String representing Assam's current state
     * @param rotation The requested rotation, in degrees. This degree reading is relative to the direction Assam
     *                 is currently facing, so a value of 0 for this argument will keep Assam facing in his
     *                 current orientation, 90 would be turning him to the right, etc.
     * @return A String representing Assam's state after the rotation, or the input currentAssam if the requested
     * rotation is illegal.
     */
    public static String rotateAssam(String currentAssam, int rotation) {

        if (currentAssam == null || currentAssam.isEmpty()) {
            return "";  // If enter invalid, return empty
        }

        char currentDirection = currentAssam.charAt(currentAssam.length() - 1);
        // get current direction
        char newDirection = currentDirection;
        // Initialize the new direction to the current direction

        try {
            newDirection = Assam.getNewDirection(currentDirection, rotation);
        } catch (IllegalArgumentException e) {
            return currentAssam;
            // If there is an illegal rotation Angle, return to the original state
        }

        // Constructs a new Assam status string and returns it
        return currentAssam.substring(0, currentAssam.length() - 1) + newDirection;
        // FIXME: Task 9
    }

    /**
     * Determine whether a potential new placement is valid (i.e that it describes a legal way to place a rug).
     * There are a number of rules which apply to potential new placements, which are detailed in the README but to
     * reiterate here:
     *   1. A new rug must have one edge adjacent to Assam (not counting diagonals)
     *   2. A new rug must not completely cover another rug. It is legal to partially cover an already placed rug, but
     *      the new rug must not cover the entirety of another rug that's already on the board.
     * @param gameState A game string representing the current state of the game
     * @param rug A rug string representing the candidate rug which you must check the validity of.
     * @return true if the placement is valid, and false otherwise.
     */
    public static boolean isPlacementValid(String gameState, String rug) {

        // Get Assam information from gameState
        Assam assam = null;
        String assamString = Assam.getContentBetweenAandB(gameState);
        if (assamString != null && assamString.length() >= 3) {
            int x = Character.getNumericValue(assamString.charAt(0));
            int y = Character.getNumericValue(assamString.charAt(1));
            char orientation = assamString.charAt(2);

            // Create a new Assam object using the extracted x and y coordinates and directions
            IntPair position = new IntPair(x, y);
            assam = new Assam(position, orientation);
        }
        IntPair assamPosition = assam.getPosition();

        String boardString = Board.extractBoardPart(gameState);
        Board board = new Board();
        board.populateBoardFromString(boardString);

        // Calculate the 4 neighbour position
        IntPair northPos = new IntPair(assamPosition.getX(), assamPosition.getY() - 1);
        IntPair southPos = new IntPair(assamPosition.getX(), assamPosition.getY() + 1);
        IntPair westPos = new IntPair(assamPosition.getX() - 1, assamPosition.getY());
        IntPair eastPos = new IntPair(assamPosition.getX() + 1, assamPosition.getY());

        // get rug position
        int rugX1 = Character.getNumericValue(rug.charAt(3));
        int rugY1 = Character.getNumericValue(rug.charAt(4));
        int rugX2 = Character.getNumericValue(rug.charAt(5));
        int rugY2 = Character.getNumericValue(rug.charAt(6));
        char rugColor = rug.charAt(0);
        int rugID = Integer.parseInt(rug.substring(1, 3));

        // Extract the coordinates of the rug and create an IntPair object
        IntPair rugPos1 = new IntPair(rugX1, rugY1);
        IntPair rugPos2 = new IntPair(rugX2, rugY2);

        Cell cell1 = board.getCell(rugPos1);
        Cell cell2 = board.getCell(rugPos2);

        // Check if rug position is equal to Assam's
        if (rugPos1.equals(assamPosition) || rugPos2.equals(assamPosition)) {
            return false;
        }

        // Check if new rug have position neighbour with Assam
        boolean isAdjacentToAssam = rugPos1.equals(northPos) || rugPos1.equals(southPos) ||
                rugPos1.equals(westPos) || rugPos1.equals(eastPos) ||
                rugPos2.equals(northPos) || rugPos2.equals(southPos) ||
                rugPos2.equals(westPos) || rugPos2.equals(eastPos);

        if (!isAdjacentToAssam) {
            return false;
        }

        if (cell1.getColor() == cell2.getColor() && cell1.getRugID() == cell2.getRugID()) {
            if (cell1.getColor() != 'n' || cell1.getRugID() != 0) {
                return false; // If the new carpet completely covers an already placed carpet, return false
            }
        }
        // FIXME: Task 10
        return true;
    }

    /**
     * Determine the amount of payment required should another player land on a square.
     * For this method, you may assume that Assam has just landed on the square he is currently placed on, and that
     * the player who last moved Assam is not the player who owns the rug landed on (if there is a rug on his current
     * square). Recall that the payment owed to the owner of the rug is equal to the number of connected squares showing
     * on the board that are of that colour. Similarly to the placement rules, two squares are only connected if they
     * share an entire edge -- diagonals do not count.
     * @param gameString A String representation of the current state of the game.
     * @return The amount of payment due, as an integer.
     */
    public static int getPaymentAmount(String gameString) {

        Assam assam = null;
        String boardString = Board.extractBoardPart(gameString);
        Board board = new Board();
        board.populateBoardFromString(boardString);

        String assamString = Assam.getContentBetweenAandB(gameString);

        if (assamString != null && assamString.length() >= 3) {
            int x = Character.getNumericValue(assamString.charAt(0));
            int y = Character.getNumericValue(assamString.charAt(1));
            char orientation = assamString.charAt(2);

            // use the new x,y and direction create a new Assam
            IntPair position = new IntPair(x, y);
            assam = new Assam(position, orientation);
        }
        IntPair assamPosition = assam.getPosition();
        Cell cell = board.getCell(assamPosition);
        char rugColor = cell.getColor();

        // If no rug in Assam position, return 0
        if (rugColor == 'n') return 0;

        boolean[][] visited = new boolean[Board.BOARD_SIZE][Board.BOARD_SIZE];
        // FIXME: Task 11
        return board.countConnectedRugs(assamPosition, rugColor, visited);
    }

    /**
     * Determine the winner of a game of Marrakech.
     * For this task, you will be provided with a game state string and have to return a char representing the colour
     * of the winner of the game. So for example if the cyan player is the winner, then you return 'c', if the red
     * player is the winner return 'r', etc...
     * If the game is not yet over, then you should return 'n'.
     * If the game is over, but is a tie, then you should return 't'.
     * Recall that a player's total score is the sum of their number of dirhams and the number of squares showing on the
     * board that are of their colour, and that a player who is out of the game cannot win. If multiple players have the
     * same total score, the player with the largest number of dirhams wins. If multiple players have the same total
     * score and number of dirhams, then the game is a tie.
     * @param gameState A String representation of the current state of the game
     * @return A char representing the winner of the game as described above.
     */
    public static char getWinner(String gameState) {
        // Step 1: Extract player information and create an array of player objects
        String[] playerInfoArray = Player.extractPlayerInfo(gameState);
        Player[] players = new Player[4];
        for (int i = 0; i < 4; i++) {
            players[i] = Player.fromString(playerInfoArray[i]);
        }

        String boardString = Board.extractBoardPart(gameState);
        // Step 2: Calculate the score for each player
        Board board = new Board();
        board.populateBoardFromString(boardString);

        char currentWinner = 'n';
        int highestScore  = -1;
        int highestDirhams  = -1;
        boolean isTie = false;

        for (Player player : players) {
            if (player.isActive()) {  // Only consider active players
                char color = player.getColor();
                int playerScore = player.getDirhams() + board.getRugCountForColor(color);
                if (playerScore > highestScore) {
                    highestScore = playerScore;
                    highestDirhams = player.getDirhams();
                    currentWinner = color;
                    isTie = false;  // New winner found, so reset the tie flag
                } else if (playerScore == highestScore) {
                    if (player.getDirhams() > highestDirhams) {
                        highestDirhams = player.getDirhams();
                        currentWinner = color;
                        isTie = false;  // New winner found based on dirhams, so reset the tie flag
                    } else if (player.getDirhams() == highestDirhams) {
                        isTie = true;  // Potential tie situation
                    }
                }
            }
        }
        return isTie ? 't' : currentWinner;
        // FIXME: Task 12
    }

    /**
     * Implement Assam's movement.
     * Assam moves a number of squares equal to the die result, provided to you by the argument dieResult. Assam moves
     * in the direction he is currently facing. If part of Assam's movement results in him leaving the board, he moves
     * according to the tracks diagrammed in the assignment README, which should be studied carefully before attempting
     * this task. For this task, you are not required to do any checking that the die result is sensible, nor whether
     * the current Assam string is sensible either -- you may assume that both of these are valid.
     * @param currentAssam A string representation of Assam's current state.
     * @param dieResult The result of the die, which determines the number of squares Assam will move.
     * @return A String representing Assam's state after the movement.
     */
    public static String moveAssam(String currentAssam, int dieResult){

        int x = Character.getNumericValue(currentAssam.charAt(1));
        int y = Character.getNumericValue(currentAssam.charAt(2));
        char direction = currentAssam.charAt(3);

        while (dieResult > 0) {
            switch (direction) {
                case 'N':
                    if (y == 0 && (x == 0 || x == 2 || x == 4)) {
                        direction = 'S';
                        x++;
                    } else if (y == 0 && (x == 1 || x == 3 || x == 5)) {
                        direction = 'S';
                        x--;
                    } else if (y == 0 && x == 6) {
                        direction = 'W';
                    } else {
                        y--;
                    }
                    break;
                case 'S':
                    if (y == 6 && (x == 1 || x == 3 || x == 5)) {
                        direction = 'N';
                        x++;
                    } else if (y == 6 && (x == 2 || x == 4 || x == 6)) {
                        direction = 'N';
                        x--;
                    } else if (y == 6 && x == 0) {
                        direction = 'E';
                    } else {
                        y++;
                    }
                    break;
                case 'W':
                    if (x == 0 && (y == 0 || y == 2 || y == 4)) {
                        direction = 'E';
                        y++;
                    } else if (x == 0 && (y == 1 || y == 3 || y == 5)) {
                        direction = 'E';
                        y--;
                    } else if (x == 0 && y == 6) {
                        direction = 'N';
                    } else {
                        x--;
                    }
                    break;
                case 'E':
                    if (x == 6 && (y == 1 || y == 3 || y == 5)) {
                        direction = 'W';
                        y++;
                    } else if (x == 6 && (y == 2 || y == 4 || y == 6)) {
                        direction = 'W';
                        y--;
                    } else if (x == 6 && y == 0) {
                        direction = 'S';
                    } else {
                        x++;
                    }
                    break;
            }

            dieResult--;
        }

        return "A" + x + y + direction;
        // FIXME: Task 13
    }

    /**
     * Place a rug on the board
     * This method can be assumed to be called after Assam has been rotated and moved, i.e in the placement phase of
     * a turn. A rug may only be placed if it meets the conditions listed in the isPlacementValid task. If the rug
     * placement is valid, then you should return a new game string representing the board after the placement has
     * been completed. If the placement is invalid, then you should return the existing game unchanged.
     * @param currentGame A String representation of the current state of the game.
     * @param rug A String representation of the rug that is to be placed.
     * @return A new game string representing the game following the successful placement of this rug if it is valid,
     * or the input currentGame unchanged otherwise.
     */
    public static String makePlacement(String currentGame, String rug) {

        // Step 1: Check if the rug is valid.
        if (!isRugValid(currentGame, rug)) {
            return currentGame;  // Rug is invalid. Return the current game state unchanged.
        }

        // Step 2: Check if the rug's placement is valid.
        if (!isPlacementValid(currentGame, rug)) {
            return currentGame;  // Rug placement is invalid. Return the current game state unchanged.
        }


        // Determine the index of the player based on the rug color.
        int playerIndex;
        char rugColor = rug.charAt(0);
        switch (rugColor) {
            case 'c': playerIndex = 0; break;
            case 'y': playerIndex = 1; break;
            case 'p': playerIndex = 2; break;
            case 'r': playerIndex = 3; break;
            default: throw new IllegalArgumentException("Invalid rug color");
        }

        // Extract the player's info from the current game.
        String playerString = currentGame.substring(playerIndex * 8, (playerIndex + 1) * 8);
        Player player = Player.fromString(playerString);
        player.setRugs(player.getRugs() - 1);
        String updatedPlayerString = player.toPlayerString();

        // Extract the board part from the current game state.
        String boardString = Board.extractBoardPart(currentGame);
        Board board = new Board();
        board.populateBoardFromString(boardString);
        board.placeRugOnBoard(rug);

        // Extract the non-board part (Assam, Player states, etc.) from the current game state.
        String nonBoardPart = currentGame.substring(0, currentGame.indexOf('B'));

        // Combine the non-board part, updated player string, and the updated board string to form the updated game state.
        String updatedGame = nonBoardPart.substring(0, playerIndex * 8) + updatedPlayerString +
                nonBoardPart.substring((playerIndex + 1) * 8) + board.toBoardString();

        return updatedGame;

        // FIXME: Task 14
    }

}

// This class was written by Huizhe Ruan. The code annotations were jointly completed by Anbo Wu and Kechun Ma.
