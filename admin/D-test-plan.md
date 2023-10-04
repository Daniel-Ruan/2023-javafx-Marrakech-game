
# Test plan

## List of classes

* List below all classes in your implementation that should have unit tests.
* For each class, list methods that can be tested in isolation.
* For each class, if there are conditions on the class' behaviour that cannot
  be tested by calling one method in isolation, give at least one example of
  a test for such a condition.

Do **not** include in your test plan the `Marrakech` class or the predefined
static methods for which we have already provided unit tests.

1. ### **Assam**:  

   - Getter & setter:  `public IntPair getPosition()`,  `public void setPosition(IntPair position)`,  `public char getOrientation()`,  `public void setOrientation(char orientation)`

     ```java
     @Test
     public void testSetPosition() {
         Assam assam = new Assam(new IntPair(1, 1), 'N');
         assam.setPosition(new IntPair(2, 3));
         assertEquals(new IntPair(2, 3), assam.getPosition());
     }
     ```

     ```java
     @Test
     public void testSetOrientation() {
         Assam assam = new Assam(new IntPair(1, 1), 'N');
         assam.setOrientation('E');
         assertEquals('E', assam.getOrientation());
     }
     ```

     

   - Static Method: `public static Assam fromString(String str)`

     - Validate that an `Assam` object is correctly constructed from a string representation.

   - Static Method: `public static String getContentBetweenAandB(String input)`

     - Validate that the method returns the correct substring between the characters 'A' and 'B', or an empty string when conditions are not met.

   - Method: `public String toAssamString()`

     - Verify that the method returns a correctly formatted string representation of the Assam object.

2. ### **Board**: 

   - Method: `public void populateBoardFromString(String boardString)`
     - Validate that the board is correctly populated from a string representation.
     
     ```java
     @Test
     public void testPopulateBoardFromString() {
         Board board = new Board();
         String boardString = "Bnn00nn00nn00nn00nn00nn00nn00nn00nn00nn00nn00nn00";
         board.populateBoardFromString(boardString);
         assertEquals('n', board.getCell(new IntPair(0, 0)).getRugColor());
         assertEquals(0, board.getCell(new IntPair(0, 0)).getRugID());
     }
     ```
     
     
     
   - Method: `public Cell getCell(IntPair position)`
     - Verify correct retrieval of a `Cell` object from the board at a valid position, and handle invalid position attempts.
     
   - Method: `private boolean isValidPosition(IntPair position)`
     - Validate that the method correctly determines whether a given position is valid on the board.
     
   - Static Method: `public static String extractBoardPart(String gameString)`
     - Validate that the method correctly extracts the board part from a game string.
     
   - Method: `public String toBoardString()`
     - Verify that the method returns a correctly formatted string representation of the board.

3. ### **Cell**:

   - Getter & setter: `public IntPair getPosition()`, `public char getColor()`,  `public int getRugID()`, `public void setRug(char color, int id)`

   ```java
   @Test
   public void testSetRug() {
       Cell cell = new Cell(new IntPair(0, 0), 'r', 1);
       cell.setRug('p', 2);
       assertEquals('p', cell.getColor());
       assertEquals(2, cell.getRugID());
   }
   ```

   - Method: `public String toRugString()`

4. ### **Player**: 

   - Getter & setter: `public char getColor()`, `public int getDirhams()`, `public String getPlayerStates()`, `public int getRugs()`

   ```java
   @Test
   public void testSetDirhamsAndSetRugs() {
       Player player = new Player('r', 500, 10, true);
       player.setDirhams(300);
       player.setRugs(5);
       assertEquals(300, player.getDirhams());
       assertEquals(5, player.getRugs());
   }
   ```

   

   - Method: `public int canAfford(int x)`, `public void addDirhams(int amount)`

   ```java
   @Test
   public void testCanAfford() {
       Player player = new Player('r', 500, 10, true);
       int amount = player.canAfford(200);
       assertEquals(200, amount);
       assertEquals(300, player.getDirhams());
   
       amount = player.canAfford(400);
       assertEquals(300, amount);
       assertEquals(0, player.getDirhams());
   }
   ```

   - Method: `public static Player fromString(String str)`
   - Method: `public static String[] extractPlayerInfo(String state)`
   - Method: `public String toPlayerString()`

5. ### **Rug**:

   - Getter & setter: `public Cell getCell1()`, `public Cell getCell2()`




