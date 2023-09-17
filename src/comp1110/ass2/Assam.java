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
     * Constructs a new Assam from a string representation.
     *
     * @param str the string representation of Assam
     * @return a new Assam object
     */
    public static Assam fromString(String str) {
        int x = Character.getNumericValue(str.charAt(1));
        int y = Character.getNumericValue(str.charAt(2));
        char orientation = str.charAt(3);
        return new Assam(new IntPair(x, y), orientation);
    }

    public static String getContentBetweenAandB(String input) {
        int indexA = input.indexOf('A');
        int indexB = input.indexOf('B');

        if (indexA == -1 || indexB == -1 || indexA >= indexB) {
            return "";  // 如果找不到'A'或'B'，或者'A'在'B'之后，返回""
        }

        return input.substring(indexA + 1, indexB);
    }

    /**
     * Returns a string representation of Assam according to the given format.
     *
     * @return the Assam string
     */
    public String toAssamString() {
        return String.format("A%d%d%c", position.getX(), position.getY(), orientation);
    }

    // Other methods  other push test
}