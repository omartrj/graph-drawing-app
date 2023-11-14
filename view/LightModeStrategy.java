package view;

import java.awt.Color;

public class LightModeStrategy extends AbstractDrawingStrategy {

    private static final Color BACKGROUND_COLOR = new Color(238, 238, 238);
    private static final double NODE_RADIUS = 15;
    private static final Color NODE_COLOR = new Color(255, 105, 105);
    private static final Color HIGHLIGHT_NODE_COLOR = new Color(210, 0, 57);
    private static final Color NODE_BORDER_COLOR = Color.BLACK;
    private static final int NODE_BORDER_WIDTH = 2;
    private static final Color NODE_TEXT_COLOR = Color.BLACK;
    private static final int NODE_TEXT_SIZE = 12;
    private static final Color EDGE_COLOR = Color.BLACK;
    private static final int EDGE_WIDTH = 1;
    private static final Color GRID_COLOR = Color.LIGHT_GRAY;
    private static final double GRID_SPACING = 20;

    public LightModeStrategy() {
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

}