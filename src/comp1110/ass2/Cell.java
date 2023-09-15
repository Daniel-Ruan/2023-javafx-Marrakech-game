package comp1110.ass2;

public class Cell {
    private IntPair position; // 使用IntPair来存储x和y坐标
    private char color;       // 地毯颜色 ('c', 'y', 'r', 'p', 'n')
    private int rugID;        // 地毯ID (0-14)

    public Cell(IntPair position, char color, int rugID) {
        this.position = position;
        this.color = color;
        this.rugID = rugID;
    }

    // Getter 和 Setter 方法
    public IntPair getPosition() {
        return position;
    }

    public void setPosition(IntPair position) {
        this.position = position;
    }

    public char getColor() {
        return color;
    }

    public void setColor(char color) {
        this.color = color;
    }

    public int getRugID() {
        return rugID;
    }

    public void setRugID(int rugID) {
        this.rugID = rugID;
    }

    public void setRug(char color, int id) {
        this.color = color;
        this.rugID = id;
    }

    // 其他方法，例如检查该单元格是否为空（没有地毯）
    public boolean isEmpty() {
        return color == 'n';  // 假设 'n' 表示没有地毯
    }

    // 生成地毯字符串的一个简单方法
    public String toRugString() {
        return String.format("%c%02d", color, rugID);
    }
    // 使用String.format来确保 rugID 是两位数
}
