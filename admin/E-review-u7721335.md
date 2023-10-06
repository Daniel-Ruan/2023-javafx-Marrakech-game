## Code Review

Reviewed by: Kechun Ma, u7721335

Reviewing code written by: Huizhe Ruan, u7723366

Component: package comp1110.ass2;

### Comments 

```java
public static char getWinner(String gameState) {
    // Step 1: 提取玩家信息并创建玩家对象数组
    String[] playerInfoArray = Player.extractPlayerInfo(gameState);
    Player[] players = new Player[4];
    for (int i = 0; i < 4; i++) {
        players[i] = Player.fromString(playerInfoArray[i]);
    }

    String boardString = Board.extractBoardPart(gameState);
    // Step 2: 为每个玩家计算得分
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
```

The given method `getWinner` aims to determine the winner of a game given a `gameState` string. The winner is determined based on player scores, and in case of ties, the number of dirhams.

**Advantages:**

1. **Structured Steps**: The method is divided into clear steps: extracting player information, initializing the board, and then calculating the winner.
2. **Iterative Player Analysis**: The loop iteratively analyzes each player's score and maintains a track of the player with the highest score.
3. **Descriptive Variable Naming**: The variable names like `highestScore`, `highestDirhams`, and `currentWinner` make the code easy to follow.
4. **Return Logic**: The method correctly returns 't' for ties and the winning color character otherwise.

**Areas for Improvement:**

1. **Comments**: While the method has some comments, adding a few more comments detailing complex logic or decisions would further improve readability.
