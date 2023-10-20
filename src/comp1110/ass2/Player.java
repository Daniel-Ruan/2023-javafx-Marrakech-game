    package comp1110.ass2;

    public class Player {
        // Player's color ('c', 'y', 'r', 'p')
        private final char color;

        // Amount of dirhams the player possesses (0-999)
        private int dirhams;

        // Number of rugs remaining for the player to place (0-99)
        private int rugs;

        // Indicates if the player is active or out of the game
        private boolean isActive;

        private String PlayerStates;

        /**
         * Constructs a new Player with the given parameters.
         *
         * @param color    the color of the player
         * @param dirhams  the initial amount of dirhams
         * @param rugs     the initial number of rugs
         * @param isActive the initial state of the player (active or not)
         */

        //create a constructor which is designed to create an object of class 'Player'
        public Player(char color, int dirhams, int rugs, boolean isActive) {
            this.color = color;
            this.dirhams = dirhams;
            this.rugs = rugs;
            this.isActive = isActive;
        }

        //create a bunch of Get and set methods
        public char getColor() {
            return color;
        }

        public int getDirhams() {
            return dirhams;
        }

        public String getPlayerStates(){
            if(isActive) return "in the game";
        else return "out of the game";}

        public void setDirhams(int dirhams) {
            this.dirhams = dirhams;
        }

        public int getRugs() {
            return rugs;
        }

        public void setRugs(int rugs) {
            this.rugs = rugs;
        }

        public boolean isActive() {
            return isActive;
        }

        public void setActive(boolean active) {
            this.isActive = active;
        }

        //Calculate the amount of dirhams that can be afforded
        public int canAfford(int x) {
            if (this.dirhams >= x) {
                this.dirhams -= x;  // Deduct the cost
                return x;  // Return the full paid amount
            } else {
                int actualPaidAmount = this.dirhams;  // Store the actual paid amount
                this.dirhams = 0;  // Clear the player's currency
                return actualPaidAmount;  // Return the actual paid amount
            }
        }

        // Add dirhams to the player's account
        public void addDirhams(int amount) {
            // Check if the amount entered is a positive integer
            if (amount >= 0) {
                this.dirhams += amount;  // Increase the dirhams count
            } else {
                System.err.println("Invalid amount. Amount should be a positive integer.");
                // Output an error message for invalid input
            }
        }

        // Set the player's attributes from a player string
        public void setPlayer(String playerString) {

            int newDirhams = Integer.parseInt(playerString.substring(2, 5));
            int newRugs = Integer.parseInt(playerString.substring(5, 7));
            boolean newIsActive = (playerString.charAt(7) == 'i');

            setRugs(newDirhams);
            setRugs(newRugs);
            setActive(newIsActive);

        }

        /**
         * Constructs a new Player from a string representation.
         *
         * @param str the string representation of the player
         * @return a new Player object
         */
        public static Player fromString(String str) {
            //this method is to accept a string argument 'str' which includes info of players.
            char color = str.charAt(1);
            //charAt() method is to obtain the character with index 1 in the string, which represents color.
            int dirhams = Integer.parseInt(str.substring(2, 5));
            //substring() method is to obtain substrings with indices 2 to 4 in the string.
            //Integer.parseInt() method is to transfer the string into an integer, which to calculate currency.
            int rugs = Integer.parseInt(str.substring(5, 7));
            //the logic is the same as above.
            boolean isActive = (str.charAt(7) == 'i');
            //obtain the character with index 7 in the string, if the string is 'i', then player is active.
            return new Player(color, dirhams, rugs, isActive);
            //create a new object 'Player', use the info before to initiate the game condition.
        }

        // Check if the game has ended
        public static boolean hasGameEnded(String currentGame) {
            String[] playerInfoArray = extractPlayerInfo(currentGame);
            boolean allRugsPlaced = true;
            for(String playerInfo : playerInfoArray) {
                int dirhams = Integer.parseInt(playerInfo.substring(2, 5));
                int rugs = Integer.parseInt(playerInfo.substring(5, 7));

                // If a player's dirhams is 0, the number of rugs for that player is not considered
                if(dirhams > 0 && rugs > 0) {
                    // If there are still dirhams and rugs that have not been placed, it indicates that the game has not ended yet
                    allRugsPlaced = false;
                    break;
                }
            }
            return allRugsPlaced;
        }

        /**
         * Extracts the information for all players from the given game state string.
         *
         * @param state The game state string.
         * @return An array containing the information for all players.
         */
        public static String[] extractPlayerInfo(String state) {
            //this method is to accept a string argument 'state' which stores info of players.
            String[] playerInfo = new String[4];
            // the length of array is 4 for we assumed there are 4 players.
            for (int i = 0; i < 4; i++) {
                String playerString = state.substring(i * 8, (i + 1) * 8);
                //the length of char for each player is 8.
                playerInfo[i] = playerString;
                //Store the extracted player info in the i-th position of the playerInfo array.
            }
            //now the playerInfo array contains 4 players info extracted from the input string state.
            return playerInfo;
        }

        /**
         * Check if a given character is a valid color.
         *
         * @param color the color character to check
         * @return true if the color is valid, false otherwise
         */
        public static boolean isValidColor(char color) {
            return color == 'c' || color == 'y' || color == 'r' || color == 'p';
        }

        /**
         * Returns a string representation of the player according to the given format.
         *
         * @return the player string
         */
        public String toPlayerString() {
            //This method has no parameters and is used to format the attributes of the player object into a string.
            return String.format("P%c%03d%02d%c", color, dirhams, rugs, isActive ? 'i' : 'o');
            //String.format() method is to creates a formatted string that contains the attribute info of the player object.
            //%c: This is a format descriptor that indicates the insertion of a char after it.

            //%03d: indicates the insertion of an integer after it, and requires at least 3 characters to represent it.
            // Integers less than 3 digits will be filled with zeros.

            //isActive ? 'i' : 'o'：This is a conditional expression that inserts the characters' i '(if active) or' o '(if inactive) based on the player's activity state (isActive).
        }
    }