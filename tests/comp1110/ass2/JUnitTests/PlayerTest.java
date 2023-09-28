package comp1110.ass2.JUnitTests;
import comp1110.ass2.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

public class PlayerTest {

    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    @Test
    public void testAddDirhams() {
        Player player = new Player('r', 50, 5, true); // Assuming this constructor exists in your Player class.
        player.addDirhams(10);
        Assertions.assertEquals(60, player.getDirhams()); // Assuming getDirhams() method exists in your Player class.

        player.addDirhams(-10);  // Assuming that your addDirhams method handles negative values.
        Assertions.assertEquals(60, player.getDirhams());  // Assuming no change in dirhams as negative value is invalid.
    }

    @Test
    public void testCanAfford() {
        Player player = new Player('r', 50, 5, true);
        int remainingDirhams = player.canAfford(30);
        Assertions.assertEquals(30, remainingDirhams);
        Assertions.assertEquals(20, player.getDirhams());  // 50 - 30 = 20

        remainingDirhams = player.canAfford(30);  // Now player has only 20 dirhams, so can afford only 20.
        Assertions.assertEquals(20, remainingDirhams);
        Assertions.assertEquals(0, player.getDirhams());  // All dirhams are used.
    }
}
