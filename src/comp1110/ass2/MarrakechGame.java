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

    private void initializePlayers(int numPlayers) {

        this.players = new Player[4];  // 总是实例化四个玩家

        for (int i = 0; i < 4; i++) {
            // 如果当前索引小于玩家数量，则设置isActive为true，否则为false
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
        // 您可以添加更多的逻辑来验证阶段的更改是否有效
        if (gamePhase < -1 || gamePhase > 2) {
            throw new IllegalArgumentException("Invalid game phase. Must be -1, 0, 1, or 2.");
        }
        this.phase = gamePhase;
    }

    public int getNumberOfPlayers() {
        return playerAmount;
    }

    public String generateGameState() {
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
        return null;  // 如果没有找到匹配的玩家，返回null
    }

    public void payTo(Player player, int amount) {
        // 获取玩家当前的dirhams
        int currentDirhams = player.getDirhams();

        // 扣除指定数量的dirhams
        int newDirhams = currentDirhams - amount;

        if (newDirhams < 0) {
            newDirhams = 0;  // 确保dirhams不会为负数
        }

        // 更新玩家的dirhams数量
        player.setDirhams(newDirhams);

        // 如果玩家的dirhams降到0，则将玩家设置为非活动状态
        if (newDirhams == 0) {
            player.setActive(false);
        }
    }

//    public void turnNext() {
//        int originalCurrentPlayerIndex = currentPlayerIndex;
//        int nextIndex = currentPlayerIndex;
//
//        for (int i = 1; i <= 4; i++) {
//            nextIndex = (currentPlayerIndex + i) % 4;  // 循环访问0, 1, 2, 3
//            Player nextPlayer = players[nextIndex];
//
//            if (nextPlayer.isActive() && nextPlayer.getDirhams() > 0) {
//                break;
//            }
//        }
//
//        if (nextIndex != originalCurrentPlayerIndex) {
//            currentPlayerIndex = nextIndex;
//        }
//    }

    public boolean isPlacedAllowed(Rug rug) {
        String currentGame = generateGameState();
        String rugStr = Rug.toRugString(rug);

        if (Marrakech.isRugValid(currentGame, rugStr) && Marrakech.isPlacementValid(currentGame, rugStr)) {
            return true;
        } else {
            return false;
        }
    }

    public void makePlacementInGame(Rug rug) {
        // Generate the current game state
        String currentGame = generateGameState();
        String rugStr = Rug.toRugString(rug);
        // Attempt to make the placement
        String resultGameString = Marrakech.makePlacement(currentGame, rugStr);
        System.out.println("resultGameString" + resultGameString);
        updateGameState(resultGameString);
    }

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
            return;  // 如果没有活着的玩家，直接返回
        }

        int currentIndex = -1;
        for (int i = 0; i < activePlayers.size(); i++) {
            if (players[currentPlayerIndex].getColor() == activePlayers.get(i).getColor()) {
                currentIndex = i;
                break;
            }
        }

        // 获取下一个玩家的索引
        char nextPlayerColor = activePlayers.get((currentIndex + 1) % activePlayers.size()).getColor();
        currentPlayerIndex = getPlayerIndexByColor(nextPlayerColor);
    }

    private int getPlayerIndexByColor(char color) {
        for (int i = 0; i < players.length; i++) {
            if (players[i].getColor() == color) {
                return i;
            }
        }
        return currentPlayerIndex;  // 如果某种原因找不到颜色，返回当前玩家索引
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
