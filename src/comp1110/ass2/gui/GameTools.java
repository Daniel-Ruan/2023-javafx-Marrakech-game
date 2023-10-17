package comp1110.ass2.gui;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameTools {

    static Font font = Font.font("Consoles", FontWeight.EXTRA_BOLD, 16);

    public static Color getColor(char color) {
        if (color =='y') return Color.web("#ffd700");
        if (color =='c') return Color.web("#00cccc");
        if (color =='r') return Color.RED;
        if (color =='p') return Color.MEDIUMPURPLE;
        return Color.LIGHTGREY;
    }
}
