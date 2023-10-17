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
    MarrakechGame game;
    Assam assam;
    Rectangle rect;
    Circle circle;

    GUIAssam(MarrakechGame game, Assam assam, int size) {
        this.game = game;
        this.assam = assam;

        var imgPat = new ImagePattern(new Image("file:assets/AssamExample.png"));
        rect = new Rectangle(size - 10, size -10);
        rect.setFill(imgPat);
        rect.setArcWidth(100);
        rect.setArcHeight(100);
        rect.setLayoutX(5);
        rect.setLayoutY(5);
        this.getChildren().add(rect);

        circle = new Circle();
        circle.setCenterX(size - 12);
        circle.setCenterY(12);
        circle.setRadius(10);
        circle.setStrokeWidth(1);
        circle.setStroke(Color.BLACK);
        this.getChildren().add(circle);

    }

    void update() {
//        int degrees = Assam.orientationToDegrees(assam.getOrientation());
        rect.setRotate(assam.getAngle());
        var player = game.players[game.currentPlayerIndex];
        var javaColor = GameTools.getColor(player.getColor());
        circle.setFill(javaColor);
    }
}
