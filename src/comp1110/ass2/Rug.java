package comp1110.ass2;

public class Rug {
    // Rug color
    private char color;

    // Rug ID
    private int id;

    // The coordinates of the squares covered by the rug
    private IntPair firstSquare;
    private IntPair secondSquare;

    /**
     * Constructs a new Rug with the given parameters.
     *
     * @param color the color of the rug
     * @param id the rug ID
     * @param firstSquare the first square covered by the rug, represented as an IntPair
     * @param secondSquare the second square covered by the rug, represented as an IntPair
     */
    public Rug(char color, int id, IntPair firstSquare, IntPair secondSquare) {
        this.color = color;
        this.id = id;
        this.firstSquare = firstSquare;
        this.secondSquare = secondSquare;
    }

    // Getter and setter methods
    public char getColor() {
        return color;
    }

    public void setColor(char color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public IntPair getFirstSquare() {
        return firstSquare;
    }

    public void setFirstSquare(IntPair firstSquare) {
        this.firstSquare = firstSquare;
    }

    public IntPair getSecondSquare() {
        return secondSquare;
    }

    public void setSecondSquare(IntPair secondSquare) {
        this.secondSquare = secondSquare;
    }

    /**
     * Returns a string representation of the rug according to the given format.
     *
     * @return the rug string
     */
    public String toRugString() {
        return String.format("%c%02d%d%d%d%d", color, id,
                firstSquare.getX(), firstSquare.getY(),
                secondSquare.getX(), secondSquare.getY());
    }

    // Other methods
}