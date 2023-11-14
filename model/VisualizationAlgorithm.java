package model;

public interface VisualizationAlgorithm {

    final int WIDTH = 800;
    final int HEIGHT = 600;
    final double CENTER_X = WIDTH / 2;
    final double CENTER_Y = HEIGHT / 2;

    public void initialize(Graph graph);

    public void execute();

    public void debug();

}
