package view;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import model.Graph;
import model.Node;

public interface DrawingStrategy {

    public static final DrawingStrategy LIGHT_MODE = new LightModeStrategy();
    public static final DrawingStrategy DARK_MODE = new DarkModeStrategy();
    public static final DrawingStrategy SMALL_NODES = new SmallNodeStrategy();
    
    public void drawGraph(Graphics2D g2d, Graph graph, GraphPainter painter);

    public void drawGrid(Graphics2D g2d, GraphPainter painter);

    public void drawBackground(Graphics2D g2d);

    public Node getNodeAtPosition(Graph graph,Point2D point);

    public void moveNode(Node node, int newX, int newY);

    public void highlightNode(Node node);
}

