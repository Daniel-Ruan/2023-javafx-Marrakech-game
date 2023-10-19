package comp1110.ass2.gui;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameTools {
    // Define a tool class for game-related tools and constants
    static Font font = Font.font("Consoles", FontWeight.EXTRA_BOLD, 16);
    // Define a static font object with a specific style and thickness
    public static Color getColor(char color) {
        // Define a method to map character colour codes to JavaFX colours
        if (color =='y') return Color.web("#fdcb6e");
        if (color =='c') return Color.web("#00cec9");
        if (color =='r') return Color.web("#ff7675");
        if (color =='p') return Color.web("#a29bfe");
        // If the character color is not recognized, return a transparent color
        return Color.web("#dfe6e9", 0);
    }
}
