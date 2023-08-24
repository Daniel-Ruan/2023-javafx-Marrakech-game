package comp1110.ass2;

public class Assam {
    // The position of Assam represented as an IntPair (x, y)
    private IntPair position;

    // Assam's orientation ('N', 'E', 'S', 'W')
    private char orientation;

    /**
     * Constructs a new Assam with the given parameters.
     *
     * @param position the position of Assam represented as an IntPair
     * @param orientation the orientation of Assam
     */
    public Assam(IntPair position, char orientation) {
        this.position = position;
        this.orientation = orientation;
    }

    // Getter and setter methods
    public IntPair getPosition() {
        return position;
    }

    public void setPosition(IntPair position) {
        this.position = position;
    }

    public char getOrientation() {
        return orientation;
    }

    public void setOrientation(char orientation) {
        this.orientation = orientation;
    }

    /**
     * Returns a string representation of Assam according to the given format.
     *
     * @return the Assam string
     */
    public String toAssamString() {
        return String.format("A%d%d%c", position.getX(), position.getY(), orientation);
    }

    // Other methods
}
