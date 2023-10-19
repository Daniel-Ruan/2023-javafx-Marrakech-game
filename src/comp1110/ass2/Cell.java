package comp1110.ass2;

public class Cell {
    private IntPair position; // Use IntPair to store x and y coordinates (使用IntPair来存储x和y坐标)
    private char color;       // rug color include ('c', 'y', 'r', 'p', 'n') (地毯颜色 ('c', 'y', 'r', 'p', 'n'))
    private int rugID;        // rug id : 0-14 (地毯ID (0-14))

    public Cell(IntPair position, char color, int rugID) {
        this.position = position;
        this.color = color;
        this.rugID = rugID;
    }

    // Getter and Setter methods (Getter 和 Setter 方法)
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

    public boolean isEmpty() {
        return color == 'n';  // 'n' stand for no rug (假设 'n' 表示没有地毯)
    }

    // Generate carpet string (生成地毯字符串的一个简单方法)
    public String toRugString() {
        return String.format("%c%02d", color, rugID);
    }
    // Use String.format to ensure that the rugID is a two-digit number (使用String.format来确保 rugID 是两位数)
}
