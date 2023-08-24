# Ass2 Analysis and design

### IntPair

**Primary Role**: To store a pair of integers often used for representing coordinates.

- Methods: `getX()`, `getY()`

### Assam

**Primary Role**: To represent the Assam piece, including its position and orientation.

- Methods: `getPosition()`, `getOrientation()`, `move()`

### Board

**Primary Role**: To maintain the state of the board, including which squares are covered by which rugs.

- Methods: `placeRug()`, `removeRug()`, `getSquareState()`

### Rug

**Primary Role**: To represent a rug piece, including its ID, owner's color, and the squares it covers.

- Methods: `getID()`, `getColor()`, `getCoveredSquares()`

### SpecialDie

**Primary Role**: To simulate the special 6-sided die used in the game.

- Methods: `roll()`

### IPlayer (Interface)

**Primary Role**: To define the contract for any kind of player, including human and computer players.

- Methods: `getColor()`, `getDirhams()`, `setDirhams()`, `getRugs()`, `setRugs()`, `isActive()`, `setActive()`, `toPlayerString()`

### Player

**Primary Role**: To represent a player in the game, keeping track of their color, dirhams, rugs, and active state.

- Methods: Implement all methods from `IPlayer`

### RandomComputerPlayer

**Primary Role**: To serve as a computer-controlled player that makes moves randomly.

- Methods: Implements all methods from `IPlayer` and adds `makeRandomMove()`

### IntelligentComputerPlayer

**Primary Role**: To serve as a computer-controlled player that makes moves based on some intelligent algorithm.

- Methods: Implements all methods from `IPlayer` and adds `makeIntelligentMove()`

By using this set of classes and interface, you can model all aspects of the Marrakech game, from the board and pieces to different types of players.