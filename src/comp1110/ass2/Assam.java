package comp1110.ass2;

import java.util.HashMap;
import java.util.Map;

public class Assam {
    // The position of Assam represented as an IntPair (x, y)
    private IntPair position;

    // Assam's orientation ('N', 'E', 'S', 'W')
    private char orientation;

    private int angle;

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
        this.angle = orientationToDegrees(orientation);
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


    public void setAngle(char ori) {
        this.angle = orientationToDegrees(ori);
    }

    public int getAngle() {
        return angle;
    }
    // Change the angle by the given value, if it's a valid rotation
    public void changeAngle(int a) {
        int proposedAngle = this.angle + a;

        if (isValidRotation(proposedAngle)) {
            this.angle = proposedAngle % 360;  // // Ensure the angle value is in [0, 360) (确保angle值在[0, 360)范围内)
        } else {
            this.angle = orientationToDegrees(orientation);
        }
    }

    // Check if the proposed angle is a valid rotation
    public boolean isValidRotation(int proposedAngle) {
        int difference = (proposedAngle - orientationToDegrees(orientation) + 360) % 360;
        return difference == 0 || difference == 90 || difference == 270;
    }


    // Update the orientation based on the current angle
    public void updateOrientationFromAngle() {
        // Normalize angle to [0, 360) range
        int normalizedAngle = ((angle % 360) + 360) % 360;

        if (normalizedAngle == 0 ) {
            this.orientation = 'N';
        } else if (normalizedAngle == 90) {
            this.orientation = 'E';
        } else if (normalizedAngle == 180) {
            this.orientation = 'S';
        } else {
            this.orientation = 'W';
        }
    }

    // Check if the current angle is valid
    public boolean isCurrentAngleValid() {
        return isValidRotation(this.angle);
    }


    /**
     * Constructs a new Assam from a string representation.
     *
     * @param str the string representation of Assam
     * @return a new Assam object
     */
    public static Assam fromString(String assamString) {
        int x = Character.getNumericValue(assamString.charAt(0));
        int y = Character.getNumericValue(assamString.charAt(1));
        char orientation = assamString.charAt(2);
        return new Assam(new IntPair(x, y), orientation);
    }

    // Convert orientation to degrees (0, 90, 180, 270)
    public static int orientationToDegrees(char orientation) {
        switch (orientation) {
            case 'N':
                return 0;
            case 'E':
                return 90;
            case 'S':
                return 180;
            case 'W':
                return 270;
            default:
                throw new IllegalArgumentException("Invalid orientation: " + orientation);
        }
    }

    // Extract content between 'A' and 'B' from a string
    public static String getContentBetweenAandB(String input) {
        int indexA = input.indexOf('A');
        int indexB = input.indexOf('B');

        if (indexA == -1 || indexB == -1 || indexA >= indexB) {
            return "";  // Return an empty string if 'A' or 'B' is not found or if 'A' comes after 'B'(如果找不到'A'或'B'，或者'A'在'B'之后，返回"")
        }

        return input.substring(indexA + 1, indexB);
    }

    // Get the new direction after a rotation
    public static char getNewDirection(char currentDirection, int rotation) {
        if (rotation == 90) {
            return RIGHT_ROTATION_MAP.getOrDefault(currentDirection, currentDirection);
        } else if (rotation == -90 || rotation == 270) {
            return LEFT_ROTATION_MAP.getOrDefault(currentDirection, currentDirection);
        } else if (rotation == 0) {
            return currentDirection;
        } else {
            // Invalid rotation angle (非法的旋转角度)
            throw new IllegalArgumentException("Invalid rotation: " + rotation);
        }
    }
    // Set Assam's position, orientation, and angle from a string
    public void setAssam(String assamString) {
        int x = Character.getNumericValue(assamString.charAt(0));
        int y = Character.getNumericValue(assamString.charAt(1));
        char orientation = assamString.charAt(2);
        setPosition(new IntPair(x, y));
        setOrientation(orientation);
        setAngle(orientation);
    }

    // Rotate Assam by the given degree
    public void rotate(int degree) {
        this.orientation = getNewDirection(this.orientation, degree);
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