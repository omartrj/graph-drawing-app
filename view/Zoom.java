package view;

import java.awt.Point;

public class Zoom implements Transformation {

    private double scale;
    private Point mousePosition;

    public Zoom(double scale, Point mousePosition) {
        this.scale = scale;
        this.mousePosition = mousePosition;
    }

    public void apply(GraphPainter painter) {
        painter.setScale(painter.getScale() * scale);
        painter.setTranslationX(painter.getTranslationX() + mousePosition.x * (1 - scale));
        painter.setTranslationY(painter.getTranslationY() + mousePosition.y * (1 - scale));
    }
    
}
