package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.HashSet;

import model.Edge;
import model.Graph;
import model.Node;

public class AbstractDrawingStrategy implements DrawingStrategy {
    
    protected final Color BACKGROUND_COLOR;
    protected final double NODE_RADIUS;
    protected final Color NODE_COLOR;
    protected final Color HIGHLIGHT_NODE_COLOR;
    protected final Color NODE_BORDER_COLOR;
    protected final int NODE_BORDER_WIDTH;
    protected final Color NODE_TEXT_COLOR;
    protected final int NODE_TEXT_SIZE;
    protected final Color EDGE_COLOR;
    protected final int EDGE_WIDTH;
    protected final Color GRID_COLOR;
    protected final double GRID_SPACING;

    private double translationX;
    private double translationY;
    private double scale;
    protected Node highlightedNode;

    public AbstractDrawingStrategy(
            Color BACKGROUND_COLOR,
            double NODE_RADIUS,
            Color NODE_COLOR,
            Color HIGHLIGHT_NODE_COLOR,
            Color NODE_BORDER_COLOR,
            int NODE_BORDER_WIDTH,
            Color NODE_TEXT_COLOR,
            int NODE_TEXT_SIZE,
            Color EDGE_COLOR,
            int EDGE_WIDTH,
            Color GRID_COLOR,
            double GRID_SPACING) {
        this.BACKGROUND_COLOR = BACKGROUND_COLOR;
        this.NODE_RADIUS = NODE_RADIUS;
        this.NODE_COLOR = NODE_COLOR;
        this.HIGHLIGHT_NODE_COLOR = HIGHLIGHT_NODE_COLOR;
        this.NODE_BORDER_COLOR = NODE_BORDER_COLOR;
        this.NODE_BORDER_WIDTH = NODE_BORDER_WIDTH;
        this.NODE_TEXT_COLOR = NODE_TEXT_COLOR;
        this.NODE_TEXT_SIZE = NODE_TEXT_SIZE;
        this.EDGE_COLOR = EDGE_COLOR;
        this.EDGE_WIDTH = EDGE_WIDTH;
        this.GRID_COLOR = GRID_COLOR;
        this.GRID_SPACING = GRID_SPACING;
    }


    @Override
    public void drawGraph(Graphics2D g2d, Graph graph, GraphPainter painter) {
        HashSet<Node> nodes = graph.getNodes();
        HashSet<Edge> edges = graph.getEdges();

        // Save the current transformation
        this.scale = painter.getScale();
        this.translationX = painter.getTranslationX();
        this.translationY = painter.getTranslationY();

        // Draw edges
        for (Edge edge : edges) {
            drawEdge(edge, g2d);
        }

        // Draw nodes
        for (Node node : nodes) {
            drawNode(node, g2d);
        }
    }

    @Override
    public void drawBackground(Graphics2D g2d) {
        g2d.setColor(BACKGROUND_COLOR);
        g2d.fillRect(0, 0, (int) g2d.getClipBounds().getWidth(), (int) g2d.getClipBounds().getHeight());
    }

    @Override
    public void drawGrid(Graphics2D g2d, GraphPainter painter) {

        // Save the current transformation
        this.scale = painter.getScale();
        this.translationX = painter.getTranslationX();
        this.translationY = painter.getTranslationY();
        
        double gridSpacing = GRID_SPACING * scale;
        double gridOriginX = transformX(0);
        double gridOriginY = transformY(0);

        // Draw lines
        g2d.setColor(GRID_COLOR);
        g2d.setStroke(new BasicStroke(1));
        // Draw vertical lines
        for (double x = gridOriginX; x < g2d.getClipBounds().getWidth(); x += gridSpacing) {
            g2d.draw(new Line2D.Double(x, 0, x, g2d.getClipBounds().getHeight()));
        }
        for (double x = gridOriginX; x > 0; x -= gridSpacing) {
            g2d.draw(new Line2D.Double(x, 0, x, g2d.getClipBounds().getHeight()));
        }
        // Draw horizontal lines
        for (double y = gridOriginY; y < g2d.getClipBounds().getHeight(); y += gridSpacing) {
            g2d.draw(new Line2D.Double(0, y, g2d.getClipBounds().getWidth(), y));
        }
        for (double y = gridOriginY; y > 0; y -= gridSpacing) {
            g2d.draw(new Line2D.Double(0, y, g2d.getClipBounds().getWidth(), y));
        }
        

    }

    private void drawNode(Node node, Graphics2D g2d) {

        // Original coordinates
        double x = node.getX();
        double y = node.getY();

        // Transformed coordinates
        double tx = transformX(x);
        double ty = transformY(y);

        // Draw the highlighted node
        if (node == highlightedNode) {
            g2d.setColor(HIGHLIGHT_NODE_COLOR);
            //Slighly bigger radius
            g2d.fill(new Ellipse2D.Double(tx - NODE_RADIUS * 1.2, ty - NODE_RADIUS * 1.2, NODE_RADIUS * 2.4, NODE_RADIUS * 2.4));
            g2d.setColor(NODE_BORDER_COLOR);
            g2d.setStroke(new BasicStroke(NODE_BORDER_WIDTH));
            g2d.draw(new Ellipse2D.Double(tx - NODE_RADIUS * 1.2, ty - NODE_RADIUS * 1.2, NODE_RADIUS * 2.4, NODE_RADIUS * 2.4));
        } else {
            // Draw the node
            g2d.setColor(NODE_COLOR);
            g2d.fill(new Ellipse2D.Double(tx - NODE_RADIUS, ty - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2));
            g2d.setColor(NODE_BORDER_COLOR);
            g2d.setStroke(new BasicStroke(NODE_BORDER_WIDTH));
            g2d.draw(new Ellipse2D.Double(tx - NODE_RADIUS, ty - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2));
        }

        // Write the index
        g2d.setColor(NODE_TEXT_COLOR);
        g2d.setFont(g2d.getFont().deriveFont((float) NODE_TEXT_SIZE));
        // Print the index in the center of the node even if it is a two-digit number
        String index = Integer.toString(node.getIndex());
        g2d.drawString(index, (float) (tx - g2d.getFontMetrics().stringWidth(index) / 2),
                (float) (ty + g2d.getFontMetrics().getHeight() / 4));
    }

    private void drawEdge(Edge edge, Graphics2D g2d) {
        Node node1 = edge.getNode1();
        Node node2 = edge.getNode2();

        // Original coordinates
        double x1 = node1.getX();
        double y1 = node1.getY();
        double x2 = node2.getX();
        double y2 = node2.getY();

        // Transformed coordinates
        double tx1 = transformX(x1);
        double ty1 = transformY(y1);
        double tx2 = transformX(x2);
        double ty2 = transformY(y2);

        // Draw the edge
        g2d.setColor(EDGE_COLOR);
        g2d.setStroke(new BasicStroke(EDGE_WIDTH));
        if (edge.isDirected()) {
            // Draw the arrow
            double dx = tx2 - tx1;
            double dy = ty2 - ty1;
            double angle = Math.atan2(dy, dx);
            double arrowLength = 10 * scale;
            double arrowAngle = Math.PI / 6;
            // Draw the arrow before the node
            double distanceFromNode = 10 * scale;
            double arrowX = tx2 - (NODE_RADIUS + distanceFromNode) * Math.cos(angle);
            double arrowY = ty2 - (NODE_RADIUS + distanceFromNode) * Math.sin(angle);
            double[] xPoints = {arrowX, arrowX - arrowLength * Math.cos(angle - arrowAngle), arrowX - arrowLength * Math.cos(angle + arrowAngle)};
            double[] yPoints = {arrowY, arrowY - arrowLength * Math.sin(angle - arrowAngle), arrowY - arrowLength * Math.sin(angle + arrowAngle)};
            g2d.draw(new Line2D.Double(tx1, ty1, tx2, ty2));
            g2d.fillPolygon(new int[] {(int) xPoints[0], (int) xPoints[1], (int) xPoints[2]}, new int[] {(int) yPoints[0], (int) yPoints[1], (int) yPoints[2]}, 3);


        } else {
            g2d.draw(new Line2D.Double(tx1, ty1, tx2, ty2));
        }
    }

    public Point2D.Double transformCoordinates(double x, double y) {
        return new Point2D.Double((x + translationX) * scale, (y + translationY) * scale);
    }

    public double transformX(double x) {
        return (x + translationX) * scale;
    }

    public double transformY(double y) {
        return (y + translationY) * scale;
    }

    public double inverseTransformX(double x) {
        return x / scale - translationX;
    }

    public double inverseTransformY(double y) {
        return y / scale - translationY;
    }

    @Override
    public Node getNodeAtPosition(Graph graph, Point2D point) {
        HashSet<Node> nodes = graph.getNodes();
        for (Node node : nodes) {
            Point2D.Double transformedPosition = transformCoordinates(node.getX(), node.getY());
            double distance = transformedPosition.distance(point);
            if (distance < NODE_RADIUS) {
                return node;
            }
        }
        return null;
    }

    @Override
    public void moveNode(Node node, int newX, int newY) {
        double x = inverseTransformX(newX);
        double y = inverseTransformY(newY);
        node.setPosition(x, y);
    }

    @Override
    public void highlightNode(Node node) {
        this.highlightedNode = node;
    }
}
