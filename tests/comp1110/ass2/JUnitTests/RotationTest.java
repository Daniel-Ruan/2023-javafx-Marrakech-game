package comp1110.ass2.JUnitTests;
import comp1110.ass2.Assam;
import comp1110.ass2.IntPair;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RotationTest {
    @Test
    public void testRotate() {
        Assam assam = new Assam(new IntPair(3, 4), 'N');
        assertEquals(0, Assam.orientationToDegrees('N'));
        assertEquals(90, Assam.orientationToDegrees('E'));
        assertEquals(180, Assam.orientationToDegrees('S'));
        assertEquals(270, Assam.orientationToDegrees('W'));

    }
}
