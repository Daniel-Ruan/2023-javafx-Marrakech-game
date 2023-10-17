package comp1110.ass2;

public class Rug {
    private Cell cell1;  // 地毯的第一个单元格
    private Cell cell2;  // 地毯的第二个单元格

    // 构造函数
    public Rug(Cell cell1, Cell cell2) {
        // 假设 cell1 和 cell2 已经有有效的 color 和 rugID
        if(cell1.getColor() != cell2.getColor() || cell1.getRugID() != cell2.getRugID()) {
            // 抛出异常或其他处理，因为这两个单元格不应该在同一个地毯上
        }
        this.cell1 = cell1;
        this.cell2 = cell2;
    }

    // Getter 和 Setter 方法
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

        // 检查是否上下相邻
        if (x1 == x2 && Math.abs(y1 - y2) == 1) {
            return true;
        }

        // 检查是否左右相邻
        if (y1 == y2 && Math.abs(x1 - x2) == 1) {
            return true;
        }

        // 不是上下或左右相邻
        return false;
    }

    public static String toRugString(Rug rug) {
        Cell cell1 = rug.getCell1();
        Cell cell2 = rug.getCell2();

        IntPair position1 = cell1.getPosition();
        IntPair position2 = cell2.getPosition();

        String cell1RugString = cell1.toRugString(); // 使用 Cell 类里的 toRugString()

        // 假设 cell1 和 cell2 有相同的颜色和 rugID，我们只需取一个即可
        return String.format("%s%d%d%d%d", cell1RugString, position1.getX(),position1.getY(), position2.getX(), position2.getY());
    }
}