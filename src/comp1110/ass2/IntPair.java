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