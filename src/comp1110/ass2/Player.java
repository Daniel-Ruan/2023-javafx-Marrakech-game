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

        /**
         * Constructs a new Player with the given parameters.
         *
         * @param color    the color of the player
         * @param dirhams  the initial amount of dirhams
         * @param rugs     the initial number of rugs
         * @param isActive the initial state of the player (active or not)
         */
        public Player(char color, int dirhams, int rugs, boolean isActive) {
            this.color = color;
            this.dirhams = dirhams;
            this.rugs = rugs;
            this.isActive = isActive;
        }

        // Getter and setter methods
        public char getColor() {
            return color;
        }

        public int getDirhams() {
            return dirhams;
        }

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
            isActive = active;
        }

        /**
         * Constructs a new Player from a string representation.
         *
         * @param str the string representation of the player
         * @return a new Player object
         */
        public static Player fromString(String str) {
            char color = str.charAt(1);
            int dirhams = Integer.parseInt(str.substring(2, 5));
            int rugs = Integer.parseInt(str.substring(5, 7));
            boolean isActive = (str.charAt(7) == 'i');
            return new Player(color, dirhams, rugs, isActive);
        }

        /**
         * Extracts the information for all players from the given game state string.
         *
         * @param state The game state string.
         * @return An array containing the information for all players.
         */
        public static String[] extractPlayerInfo(String state) {
            String[] playerInfo = new String[4];  // 假设有4个玩家
            for (int i = 0; i < 4; i++) {  // 有4个玩家
                String playerString = state.substring(i * 8, (i + 1) * 8);
                playerInfo[i] = playerString;  // .substring(1)
            }
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
            return String.format("P%c%03d%02d%c", color, dirhams, rugs, isActive ? 'i' : 'o');
        }
    }