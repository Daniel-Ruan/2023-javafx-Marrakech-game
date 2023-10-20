package comp1110.ass2;

import java.util.ArrayList;
import java.util.List;

public class MarrakechGame {

    public int playerAmount;
    public int phase;
    public Player[] players;
    public int currentPlayerIndex;
    public Assam assam;
    public Board board;
    public static final char[] PLAYER_COLORS = {'c', 'y', 'p', 'r'};  // Declare it as a class constant.

    public MarrakechGame(int playerAmount) {

        if (playerAmount < 2 || playerAmount > 4) {
            throw new IllegalArgumentException("Invalid number of players. Must be 2, 3, or 4.");
        }
        this.playerAmount = playerAmount;
        this.phase = 0;
        this.currentPlayerIndex = 0;

        initializePlayers(playerAmount);

    }

    // Initialize the player objects
    private void initializePlayers(int numPlayers) {

        this.players = new Player[4];  // Always instantiate four player

        for (int i = 0; i < 4; i++) {
            // Set isActive to true for players up to the specified number of players
            boolean isActive = i < numPlayers;
            players[i] = new Player(PLAYER_COLORS[i], 30, 15, isActive);
        }

        this.assam = new Assam(new IntPair(3, 3), 'N');
        this.board = new Board();
    }

    public int getGamePhase() {
        return phase;
    }

    public void setGamePhase(int gamePhase) {
        // Can add more logic to validate if the phase change is valid
        if (gamePhase < -1 || gamePhase > 2) {
            throw new IllegalArgumentException("Invalid game phase. Must be -1, 0, 1, or 2.");
        }
        this.phase = gamePhase;
    }

    public int getNumberOfPlayers() {
        return playerAmount;
    }

    // Generate the current game state as a string
    public String generateGameState() {
        StringBuilder gameState = new StringBuilder();
        // Add player information
        for (Player player : players) {
            gameState.append(player.toPlayerString());
        }
        // Add Assam information
        gameState.append(assam.toAssamString());
        // Add board information
        gameState.append(board.toBoardString());
        return gameState.toString();
    }

    // Move Assam within the game
    public void moveAssamInGame(int steps) {
        String currentAssamStr = assam.toAssamString();
        String newAssamStr = Marrakech.moveAssam(currentAssamStr, steps);

        int x = Character.getNumericValue(newAssamStr.charAt(1));
        int y = Character.getNumericValue(newAssamStr.charAt(2));
        char orientation = newAssamStr.charAt(3);

        this.assam.setPosition(new IntPair(x,y));
        this.assam.setOrientation(orientation);
    }

    public Player getPlayer(char color) {
        for (Player player : players) {
            if (player.getColor() == color) {
                return player;
            }
        }
        return null;  // Return null if no matching player is found
    }

    // Deduct dirhams from a player's account
    public void payTo(Player player, int amount) {
        // Get the player's current dirhams
        int currentDirhams = player.getDirhams();

        // Deduct the specified amount of dirhams
        int newDirhams = currentDirhams - amount;

        if (newDirhams < 0) {
            newDirhams = 0;  // Ensure dirhams do not go negative
        }

        // Update the player's dirhams count
        player.setDirhams(newDirhams);

        // If the player's dirhams drop to 0, set the player to inactive
        if (newDirhams == 0) {
            player.setActive(false);
        }
    }


    // Determine if a rug placement is allowed
    public boolean isPlacedAllowed(Rug rug) {
        String currentGame = generateGameState();
        String rugStr = Rug.toRugString(rug);

        if (Marrakech.isRugValid(currentGame, rugStr) && Marrakech.isPlacementValid(currentGame, rugStr)) {
            return true;
        } else {
            return false;
        }
    }

    // Make a rug placement in the game
    public void makePlacementInGame(Rug rug) {
        // Generate the current game state
        String currentGame = generateGameState();
        String rugStr = Rug.toRugString(rug);
        // Attempt to make the placement
        String resultGameString = Marrakech.makePlacement(currentGame, rugStr);
        System.out.println("resultGameString" + resultGameString);
        updateGameState(resultGameString);
    }

    // Move to the next active player's turn
    public void turnNext() {
        List<Player> activePlayers = new ArrayList<>();
        for (char color : PLAYER_COLORS) {
            for (Player player : players) {
                if (player.getColor() == color && player.isActive() && player.getDirhams() > 0) {
                    activePlayers.add(player);
                }
            }
        }

        if (activePlayers.isEmpty()) {
            return;  // Return if there are no active players
        }

        // Get the current player's color
        char currentPlayerColor = players[currentPlayerIndex].getColor();
        Player currentPlayer = getPlayerByColor(currentPlayerColor);

        int currentIndex = -1;
        if (currentPlayer.isActive()) { // Check if player is active
            for (int i = 0; i < activePlayers.size(); i++) {
                if (currentPlayerColor == activePlayers.get(i).getColor()) {
                    currentIndex = i;
                    break;
                }
            }
        } else { // if the current player is no longer active, seek for another active player.
            for (int i = 0; i < PLAYER_COLORS.length; i++) {
                if (PLAYER_COLORS[i] == currentPlayerColor) {
                    for (int j = i + 1; j < PLAYER_COLORS.length; j++) {
                        for (Player activePlayer : activePlayers) {
                            if (activePlayer.getColor() == PLAYER_COLORS[j]) {
                                currentIndex = activePlayers.indexOf(activePlayer) - 1;
                                //The following code will add 1 to the currentIndex, so subtract 1 here
                                break;
                            }
                        }
                        if (currentIndex != -1) break;
                    }
                    if (currentIndex != -1) break;
                }
            }
        }

        // Get the index of the next player
        char nextPlayerColor = activePlayers.get((currentIndex + 1) % activePlayers.size()).getColor();
        currentPlayerIndex = getPlayerIndexByColor(nextPlayerColor);
    }

    // Get the player by color
    private Player getPlayerByColor(char color) {
        for (Player player : players) {
            if (player.getColor() == color) {
                return player;
            }
        }
        return null;
    }

    // Get the player's index by color
    private int getPlayerIndexByColor(char color) {
        for (int i = 0; i < players.length; i++) {
            if (players[i].getColor() == color) {
                return i;
            }
        }
        return currentPlayerIndex;  // If color is not found for some reason
    }

    public void updateGameState(String state) {
        String boardString = Board.extractBoardPart(state);  // Remove the 'B' at the start. String boardString = state.substring(37);
        String assamString = Assam.getContentBetweenAandB(state);
        String[] playerInfoArray = Player.extractPlayerInfo(state);

        board.populateBoardFromString(boardString);
        assam.setAssam(assamString);

        for (int i = 0; i < players.length && i < playerInfoArray.length; i++) {
            players[i].setPlayer(playerInfoArray[i]);
        }
        System.out.println("updateGameState" + generateGameState());
    }
}
