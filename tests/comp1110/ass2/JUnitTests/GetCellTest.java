package comp1110.ass2.JUnitTests;

import comp1110.ass2.IntPair;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import comp1110.ass2.Board;
import comp1110.ass2.Cell;
import org.junit.jupiter.api.BeforeEach;

public class GetCellTest {
    //This class contains tests for the getCell method of the Board class.
    Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    void testGetCellValidPosition() {
        Cell cell = board.getCell(new IntPair(3,3));
        assertNotNull(cell, "Cell at a valid position should not be null");
        // Assuming that the cell at (3,3) is initialized to 'n', 0
        assertEquals('n', cell.getColor(), "Expected initial color to be 'n'");
        assertEquals(0, cell.getRugID(), "Expected initial rugID to be 0");
    }

    @Test
    void testGetCellOutOfBounds() {
        Cell cell = board.getCell(new IntPair(10,10));
        assertNull(cell, "Cell at an out-of-bounds position should be null");
    }
    //The fetched cell should be null since the position is outside the board's boundaries.
}
