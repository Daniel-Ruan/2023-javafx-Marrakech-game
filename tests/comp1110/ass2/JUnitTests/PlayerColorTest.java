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
        boolean isValid = Player.isValidColor(validColor);
        Assertions.assertTrue(isValid, "Expected valid color to return true");
    }

    @Test
    public void testIsValidColorInvalid() {
        // create an object of player
        Player player1 = new Player('x', 30, 15, true);

        // test an invalid color
        char invalidColor = 'x'; // assume 'x' is an invalid color
        boolean isValid = Player.isValidColor(invalidColor);
        Assertions.assertFalse(isValid, "Expected invalid color to return false");
    }

    @Test
    public void testIsValidNumberInvalid() {
        // create an object of player
        Player player2 = new Player('4', 30, 15, true);

        // test an invalid number
        char invalidNumber = '4'; // assume 'x' is an invalid color
        boolean isValid = Player.isValidColor(invalidNumber);
        Assertions.assertFalse(isValid, "Expected invalid number to return false");
    }

    @Test
    public void testIsValidCharInvalid() {
        // create an object of player
        Player player3 = new Player('#', 30, 15, true);

        // test an invalid charater
        char invalidChar = '#'; // assume 'x' is an invalid color
        boolean isValid = Player.isValidColor(invalidChar);
        Assertions.assertFalse(isValid, "Expected invalid character to return false");
    }
}