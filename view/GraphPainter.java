package view;

import java.awt.Graphics2D;
import java.awt.Point;

import model.Graph;
import model.Node;
import model.RandomAlgorithm;
import model.UndirectedGraph;
import model.VisualizationAlgorithm;

public class GraphPainter {

    private static final DrawingStrategy DEFAULT_DRAWING_STRATEGY = new LightModeStrategy();
    private static final VisualizationAlgorithm DEFAULT_ALGORITHM = new RandomAlgorithm();
    private static final Graph DEFAULT_GRAPH = new UndirectedGraph();
    
    private Graph graph;
    private DrawingStrategy drawingStrategy;
    private VisualizationAlgorithm algorithm;
    private double translationX;
    private double translationY;
    private double scale;
    private boolean showGrid;

    public GraphPainter() {
        this.drawingStrategy = DEFAULT_DRAWING_STRATEGY;
        this.algorithm = DEFAULT_ALGORITHM;
        this.graph = DEFAULT_GRAPH;
        this.translationX = 0;
        this.translationY = 0;
        this.scale = 1;
        this.showGrid = true;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
        this.executeVisualization();
        this.graph.notifyObservers();
    }

    public Node getNodeAtPosition(Point point) {
        return drawingStrategy.getNodeAtPosition(this.graph, point);
    }

    public void setVisualizationAlgorithm(VisualizationAlgorithm algorithm) {
        this.algorithm = algorithm;
        this.executeVisualization();
        this.graph.notifyObservers();
    }

    private void executeVisualization() {
        this.algorithm.initialize(this.graph);
        this.algorithm.execute();
    }

    public void debug() {
        this.algorithm.debug();
    }
    
    public void setDrawingStrategy(DrawingStrategy drawingStrategy) {
        this.drawingStrategy = drawingStrategy;
        this.graph.notifyObservers();
    }
    
    public DrawingStrategy getDrawingStrategy() {
        return drawingStrategy;
    }

    public void drawGraph(Graphics2D g2d) {
        drawingStrategy.drawGraph(g2d, this.graph, this);
    }

    public void drawGrid(Graphics2D g2d) {
        if (showGrid) {
            drawingStrategy.drawGrid(g2d, this);
        }
    }

    public void drawBackground(Graphics2D g2d) {
        drawingStrategy.drawBackground(g2d);
    }

    public void moveNode(Node node, int newX, int newY) {
        drawingStrategy.moveNode(node, newX, newY);
        this.graph.notifyObservers();
    }

    public void setTranslationX(double translationX) {
        this.translationX = translationX;
    }

    public double getTranslationX() {
        return translationX;
    }

    public void setTranslationY(double translationY) {
        this.translationY = translationY;
    }

    public double getTranslationY() {
        return translationY;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public double getScale() {
        return scale;
    }

    public void switchGrid() {
        this.showGrid = !this.showGrid;
        this.graph.notifyObservers();
    }

    public boolean isShowGrid() {
        return showGrid;
    }

    public void applyTransformation(Transformation transformation) {
        transformation.apply(this);
        this.graph.notifyObservers();
    }

    public void highlightNode(Node node) {
        drawingStrategy.highlightNode(node);
        this.graph.notifyObservers();
    }
}
