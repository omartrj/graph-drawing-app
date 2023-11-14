package view;

public class Translation implements Transformation {

    private double translationX;
    private double translationY;

    public Translation(double translationX, double translationY) {
        this.translationX = translationX;
        this.translationY = translationY;
    }

    @Override
    public void apply(GraphPainter painter) {
        /* painter.setTranslationX(painter.getTranslationX() + translationX);
        painter.setTranslationY(painter.getTranslationY() + translationY); */
        //Also based on the scale
        painter.setTranslationX(painter.getTranslationX() + translationX / painter.getScale());
        painter.setTranslationY(painter.getTranslationY() + translationY / painter.getScale());
    }
}