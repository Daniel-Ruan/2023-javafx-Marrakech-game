package comp1110.ass2.gui;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameTools {

    static Font font = Font.font("Consoles", FontWeight.EXTRA_BOLD, 16);

    public static Color getColor(char color) {
        if (color =='y') return Color.web("#fdcb6e");
        if (color =='c') return Color.web("#00cec9");
        if (color =='r') return Color.web("#ff7675");
        if (color =='p') return Color.web("#a29bfe");
        return Color.web("#dfe6e9");
    }
}
