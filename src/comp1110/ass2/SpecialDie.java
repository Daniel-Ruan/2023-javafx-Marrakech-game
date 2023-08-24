package comp1110.ass2;

import java.util.Random;

public class SpecialDie {
    // An array to hold the faces of the die. Notice that the numbers are not equally common.
    private static final int[] FACES = {1, 2, 2, 3, 3, 4};
    private final Random rand;

    /**
     * Constructs a new SpecialDie with a fresh random number generator.
     */
    public SpecialDie() {
        this.rand = new Random();
    }

    /**
     * Constructs a new SpecialDie with a specified random number generator. Useful for testing.
     *
     * @param rand a Random object to use for generating numbers
     */
    public SpecialDie(Random rand) {
        this.rand = rand;
    }

    /**
     * Rolls the die and returns the face value.
     *
     * @return the face value rolled
     */
    public int roll() {
        // Pick a random index from the FACES array.
        int index = rand.nextInt(FACES.length);
        // Return the face value at that index.
        return FACES[index];
    }
}
