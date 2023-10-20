package comp1110.ass2.gui;

import comp1110.ass2.Assam;
import comp1110.ass2.MarrakechGame;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;

public class GUIAssam extends Group {
    // Reference to the MarrakechGame and Assam instances
    MarrakechGame game;
    Assam assam;
    // Rectangle and Circle shapes for displaying the Assam
    Rectangle rect;
    Circle circle;
    // Constructor for GUIAssam
    GUIAssam(MarrakechGame game, Assam assam, int size) {
        this.game = game;
        this.assam = assam;
        // Create a rectangle with an image pattern to represent Assam
        var imgPat = new ImagePattern(new Image("file:assets/AssamExample.png"));
        rect = new Rectangle(size - 10, size -10);
        rect.setFill(imgPat);
        rect.setArcWidth(100);
        rect.setArcHeight(100);
        rect.setLayoutX(5);
        rect.setLayoutY(5);
        this.getChildren().add(rect);
    // Create a circle to represent the player's color
        circle = new Circle();
        circle.setCenterX(size - 12);
        circle.setCenterY(12);
        circle.setRadius(10);
        circle.setStrokeWidth(1);
        circle.setStroke(Color.BLACK);
        this.getChildren().add(circle);

    }
    // Update the Assam's appearance and color based on game state
    void update() {
//        int degrees = Assam.orientationToDegrees(assam.getOrientation());
        rect.setRotate(assam.getAngle());// Rotate the Assam based on its angle
        var player = game.players[game.currentPlayerIndex];
        var javaColor = GameTools.getColor(player.getColor());// Get the player's color
        circle.setFill(javaColor);// Set the circle's fill color to the player's color
    }
}

// This class was written by Huizhe Ruan. The code annotations was jointly completed by Kechun Ma.
