package comp1110.ass2.gui;

import comp1110.ass2.Cell;
import comp1110.ass2.MarrakechGame;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Objects;
public class GUICell extends Group {

    MarrakechGame game;
    Cell cell;
    GUIBoard guiBoard;
    Rectangle rect;
    Text text;
    boolean highLight;

    // Constructor for GUICell
    GUICell(MarrakechGame game, Cell cell, int size, GUIBoard guiBoard) {
        this.game = game;
        this.cell = cell;
        this.guiBoard = guiBoard;

        rect = new Rectangle(size, size);
        rect.setArcWidth(0);
        rect.setArcHeight(0);
        // Mouse hover event to highlight cells when in phase 2
        rect.setOnMouseEntered(event -> {
            if (game.phase == 2) {
                guiBoard.setHighLightPosition(cell.getPosition());
            }
        });

        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(5);
        this.getChildren().add(rect);

        text = new Text();
        text.setLayoutX(size / 2);
        text.setLayoutY(size / 2);
        text.setFont(GameTools.font);
        this.getChildren().add(text);

    }
    // Set the highlighting status for the cell
    void  setHighLight(Boolean valve) {
        highLight = valve;
        this.update();
    }
    // Update the visual representation of the GUICell
    void update() {
        var color = cell.getColor();
        var javaColor = GameTools.getColor(color);
        rect.setFill(javaColor);
        // Display rug ID or empty string
        if (Objects.equals(cell.toRugString(), "n00")) {
            text.setText("");
        }else {
            text.setText(String.format("%02d", cell.getRugID()));
        }
        // Highlight cell based on phase and highlighting status
        rect.setStrokeWidth(highLight && guiBoard.game.phase == 2 ? 5 : 0);
    }

}

// This class was written by Huizhe Ruan. The code annotations was jointly completed by Kechun Ma.
