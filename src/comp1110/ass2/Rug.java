package comp1110.ass2;

public class Rug {
    private Cell cell1;  // The first cell of the rug (地毯的第一个单元格)
    private Cell cell2;  // The second cell of the rug (地毯的第二个单元格)

    public Rug(Cell cell1, Cell cell2) {
        // Assuming cell1 and cell2 already have valid color and rugID (假设 cell1 和 cell2 已经有有效的 color 和 rugID)
        if(cell1.getColor() != cell2.getColor() || cell1.getRugID() != cell2.getRugID()) {
            // Throw an exception or perform other handling because these two cells should not be on the same rug (抛出异常或其他处理，因为这两个单元格不应该在同一个地毯上)
        }
        this.cell1 = cell1;
        this.cell2 = cell2;
    }

    // Getter and Setter methods(Getter 和 Setter 方法)
    public Cell getCell1() {
        return cell1;
    }

    public Cell getCell2() {
        return cell2;
    }

    public void setCell1(Cell cell1) {
        this.cell1 = cell1;
    }

    public void setCell2(Cell cell2) {
        this.cell2 = cell2;
    }


    // 其他可能的方法，比如检查该地毯是否覆盖了特定单元格
    public boolean coversCell(IntPair position) {
        return cell1.getPosition().equals(position) || cell2.getPosition().equals(position);
    }

    public boolean areAdjacent(Cell cell1, Cell cell2) {
        IntPair position1 = cell1.getPosition();
        IntPair position2 = cell2.getPosition();

        int x1 = position1.getX();
        int y1 = position1.getY();
        int x2 = position2.getX();
        int y2 = position2.getY();

        // Check if they are vertically adjacent (检查是否上下相邻)
        if (x1 == x2 && Math.abs(y1 - y2) == 1) {
            return true;
        }

        // Check if they are horizontally adjacent (检查是否左右相邻)
        if (y1 == y2 && Math.abs(x1 - x2) == 1) {
            return true;
        }

        // not vertically or horizontally adjacent (不是上下或左右相邻)
        return false;
    }

    // Convert a Rug object to its string representation
    public static String toRugString(Rug rug) {
        Cell cell1 = rug.getCell1();
        Cell cell2 = rug.getCell2();

        IntPair position1 = cell1.getPosition();
        IntPair position2 = cell2.getPosition();

        String cell1RugString = cell1.toRugString(); // Use the toRugString() method from the Cell class (使用 Cell 类里的 toRugString())

        // Assuming cell1 and cell2 have the same color and rugID, we only need one of them (假设 cell1 和 cell2 有相同的颜色和 rugID，我们只需取一个即可)
        return String.format("%s%d%d%d%d", cell1RugString, position1.getX(),position1.getY(), position2.getX(), position2.getY());
    }
}