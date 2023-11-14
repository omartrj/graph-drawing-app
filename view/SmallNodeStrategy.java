package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import model.Node;

public class SmallNodeStrategy extends AbstractDrawingStrategy {

    private static final Color BACKGROUND_COLOR = new Color(238, 238, 238);
    private static final double NODE_RADIUS = 5;
    private static final Color NODE_COLOR = new Color(255, 105, 105);
    private static final Color HIGHLIGHT_NODE_COLOR = new Color(210, 0, 57);
    private static final Color NODE_BORDER_COLOR = Color.BLACK;
    private static final int NODE_BORDER_WIDTH = 1;
    private static final Color NODE_TEXT_COLOR = Color.BLACK;
    private static final int NODE_TEXT_SIZE = 0;
    private static final Color EDGE_COLOR = Color.BLACK;
    private static final int EDGE_WIDTH = 1;
    private static final Color GRID_COLOR = Color.LIGHT_GRAY;
    private static final double GRID_SPACING = 20;

    public SmallNodeStrategy() {
        super(
            BACKGROUND_COLOR,
            NODE_RADIUS,
            NODE_COLOR,
            HIGHLIGHT_NODE_COLOR,
            NODE_BORDER_COLOR,
            NODE_BORDER_WIDTH,
            NODE_TEXT_COLOR,
            NODE_TEXT_SIZE,
            EDGE_COLOR,
            EDGE_WIDTH,
            GRID_COLOR,
            GRID_SPACING
        );
    }

    public void drawNode(Graphics2D g2d, Node node) {
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
    }
    
}
