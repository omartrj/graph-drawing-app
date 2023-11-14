package view;

import java.awt.Color;

public class DarkModeStrategy extends AbstractDrawingStrategy {

    private static final Color BACKGROUND_COLOR = new Color(47, 48, 50);
    private static final double NODE_RADIUS = 15;
    private static final Color NODE_COLOR = new Color(237, 230, 138);
    private static final Color HIGHLIGHT_NODE_COLOR = new Color(176, 165, 101);
    private static final Color NODE_BORDER_COLOR = Color.BLACK;
    private static final int NODE_BORDER_WIDTH = 2;
    private static final Color NODE_TEXT_COLOR = Color.BLACK;
    private static final int NODE_TEXT_SIZE = 12;
    private static final Color EDGE_COLOR = Color.WHITE;
    private static final int EDGE_WIDTH = 1;
    private static final Color GRID_COLOR = new Color(92, 84, 112);
    private static final double GRID_SPACING = 20;

    public DarkModeStrategy() {
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
