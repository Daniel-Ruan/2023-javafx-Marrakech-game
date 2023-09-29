package comp1110.ass2.JUnitTests;

import comp1110.ass2.Cell;
import comp1110.ass2.IntPair;
import comp1110.ass2.Rug;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RugTest {
        private Cell cell1;
        private Cell cell2;
        private Rug rug;

        @BeforeEach
        public void setUp() {
            cell1 = new Cell(new IntPair(0, 0), 'c', 1);
            cell2 = new Cell(new IntPair(0, 1), 'c', 1);
            rug = new Rug(cell1, cell2);
        }//set up a new rug

        @Test
        public void testCoversCell() {
            // test if the rug correctly covers its constituent cells

            Assertions.assertTrue(rug.coversCell(new IntPair(0, 0)));
            Assertions.assertTrue(rug.coversCell(new IntPair(0, 1)));

            Assertions.assertFalse(rug.coversCell(new IntPair(2, 2)));
            Assertions.assertFalse(rug.coversCell(new IntPair(1, 1)));
        }
}
