package comp1110.ass2.JUnitTests;

import comp1110.ass2.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlayerColorTest {

    @Test
    public void testIsValidColorValid() {
        // create an object of player
        Player player = new Player('c', 30, 15, true);

        // test a valid color
        char validColor = 'c'; // assume 'c' is a valid color
        boolean isValid = player.isValidColor(validColor);
        Assertions.assertTrue(isValid, "Expected valid color to return true");
    }

    @Test
    public void testIsValidColorInvalid() {
        // create an object of player
        Player player1 = new Player('x', 30, 15, true);

        // test an invalid color
        char invalidColor = 'x'; // assume 'x' is an invalid color
        boolean isValid = player1.isValidColor(invalidColor);
        Assertions.assertFalse(isValid, "Expected invalid color to return false");
    }
}