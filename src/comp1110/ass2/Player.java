package comp1110.ass2;

public class Player implements IPlayer {
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
     * @param color the color of the player
     * @param dirhams the initial amount of dirhams
     * @param rugs the initial number of rugs
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
     * Returns a string representation of the player according to the given format.
     *
     * @return the player string
     */
    public String toPlayerString() {
        return String.format("P%c%03d%02d%c", color, dirhams, rugs, isActive ? 'i' : 'o');
    }

    // Other methods
}