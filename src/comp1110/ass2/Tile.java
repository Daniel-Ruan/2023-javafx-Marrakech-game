package comp1110.ass2;
import java.util.Objects;

public class Tile {
    private Rug rug; // can be null, indicating no rug on this tile
    private Assam assam; // can be null, indicating no Assam on this tile

    // Constructors, getters, and setters

    /*public Tile() {
        this.rug = null;
        this.assam = null;
    }*/

    public Rug getRug() {
        return rug;
    }

    public void setRug(Rug rug) {
        this.rug = rug;
    }

    public Assam getAssam() {
        return assam;
    }

    public void setAssam(Assam assam) {
        this.assam = assam;
    }

    // Check if tile is empty
    public boolean isEmpty() {
        return rug == null && assam == null;
    }

    // Check if only Assam is on the tile
    public boolean hasOnlyAssam() {
        return rug == null && assam != null;
    }

    // Check if only Rug is on the tile
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return Objects.equals(rug, tile.rug) &&
                Objects.equals(assam, tile.assam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rug, assam);
    }
}