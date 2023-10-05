## Code Review

Reviewed by: <Huizhe Ruan>, <u7723366>

Reviewing code written by: <Anbo Wu> <u7706346>

Component: <
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
        char invalidNumber = '4'; // assume '4' is an invalid color
        boolean isValid = Player.isValidColor(invalidNumber);
        Assertions.assertFalse(isValid, "Expected invalid number to return false");
    }

    @Test
    public void testIsValidCharInvalid() {
        // create an object of player
        Player player3 = new Player('#', 30, 15, true);

        // test an invalid character
        char invalidChar = '#'; // assume '#' is an invalid color
        boolean isValid = Player.isValidColor(invalidChar);
        Assertions.assertFalse(isValid, "Expected invalid character to return false");
    }
}>

### Comments 

<Obviously the code comes form the test file called 'PlayerColorTest'. It is designed to test if the 
input char of color is right. The test includes four sub-parts which test a valid color, an invalid 
color, a number and a symbol. According to the rule, only the first character can be valid. I think 
this code has been considered comprehensively with appropriate annotations and strong readability.>


