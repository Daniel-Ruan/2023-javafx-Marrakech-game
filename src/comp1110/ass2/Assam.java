package comp1110.ass2;

import java.util.HashMap;
import java.util.Map;

public class Assam {
    // The position of Assam represented as an IntPair (x, y)
    private IntPair position;

    // Assam's orientation ('N', 'E', 'S', 'W')
    private char orientation;

    private static final Map<Character, Character> RIGHT_ROTATION_MAP = new HashMap<>() {{
        put('N', 'E');
        put('E', 'S');
        put('S', 'W');
        put('W', 'N');
    }};

    private static final Map<Character, Character> LEFT_ROTATION_MAP = new HashMap<>() {{
        put('N', 'W');
        put('E', 'N');
        put('S', 'E');
        put('W', 'S');
    }};

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

    public static char getNewDirection(char currentDirection, int rotation) {
        if (rotation == 90) {
            return RIGHT_ROTATION_MAP.getOrDefault(currentDirection, currentDirection);
        } else if (rotation == -90 || rotation == 270) {
            return LEFT_ROTATION_MAP.getOrDefault(currentDirection, currentDirection);
        } else if (rotation == 0) {
            return currentDirection;
        } else {
            // 非法的旋转角度
            throw new IllegalArgumentException("Invalid rotation: " + rotation);
        }
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