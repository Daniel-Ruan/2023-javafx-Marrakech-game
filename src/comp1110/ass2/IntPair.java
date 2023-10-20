package comp1110.ass2;

import java.util.Objects;

public class IntPair {
    // x-coordinate
    private final int x;

    // y-coordinate
    private final int y;

    /**
     * Constructs a new IntPair with the given x and y coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public IntPair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x-coordinate.
     *
     * @return the x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate.
     *
     * @return the y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Checks if the coordinates represented by this IntPair are within a 7x7 board.
     * @return true if the coordinates are within the board, false otherwise.
     */
    public boolean isValidForBoard() {
        return x >= 0 && x < 7 && y >= 0 && y < 7;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntPair intPair = (IntPair) o;
        return x == intPair.x && y == intPair.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "IntPair{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

// This class was written by Huizhe Ruan.