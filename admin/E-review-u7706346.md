## Code Review

Reviewed by: Anbo Wu, u7706346

Reviewing code written by: Kechun Ma, u7721335

Component: package comp1110.ass2.JUnitTests; 

### Comments 

```java
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
```


The given code is a unit test for the `Rug` class, specifically focusing on the `coversCell` method of the `Rug` class.

**Use:** The purpose of the test is to ensure that the `coversCell` method in the `Rug` class correctly identifies whether a given cell position is covered by the rug or not.

**Advantages:**

1. **Modularity**: The code separates the setup from the actual test, making it cleaner and more modular.
2. **Clarity**: The use of `@BeforeEach` to set up common preconditions makes the test method(s) cleaner and easier to understand.

**Disadvantages:**

1. **Limited Scope**: This test only checks the `coversCell` method. If the `Rug` class has other methods or functionalities, they aren't being tested here.

```java
public static int rollDie() {
    SpecialDie specialDie = new SpecialDie();
    return specialDie.roll();
    // FIXME: Task 6

}
```

**Use:** The purpose of the method is to provide an interface for rolling a `SpecialDie` and getting its outcome.

**Advantages:**

1. **Simplicity**: The method is straightforward and easy to understand.
2. **Abstraction**: It abstracts away the details of the `SpecialDie` class. Callers don't need to know how the `SpecialDie` is implemented, they just need to call `rollDie()` to get a result.

