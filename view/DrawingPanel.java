package view;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import model.Graph;
import model.Node;
import model.VisualizationAlgorithm;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DrawingPanel extends JPanel {

    public static final int WIDTH = MainFrame.WIDTH - ControlPanel.WIDTH;
    public static final int HEIGHT = MainFrame.HEIGHT;

    private GraphPainter graphPainter;
    private Graph graph;
    private Node selectedNode;
    private Point mousePosition;

    public DrawingPanel() {
        super(true);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.setBackground(new Color(255, 245, 244));

        this.selectedNode = null;
        this.mousePosition = null;
        this.graphPainter = new GraphPainter();

        // Add interaction listeners
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mousePosition = e.getPoint();
                /* selectedNode = graphPainter.getNodeAtPosition(mousePosition);
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); */
                //If left click
                if (e.getButton() == MouseEvent.BUTTON1) {
                    selectedNode = graphPainter.getNodeAtPosition(mousePosition);
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
                //If right click
                else if (e.getButton() == MouseEvent.BUTTON3) {
                    Node node = graphPainter.getNodeAtPosition(mousePosition);
                    if (node != null) {
                        JPopupMenu popupMenu = new JPopupMenu();
                        popupMenu.add("Delete node").addActionListener(e1 -> {
                            graph.removeNode(node);
                        });

                        popupMenu.add("Add edge").addActionListener(e1 -> {
                            Node node1 = graphPainter.getNodeAtPosition(mousePosition);
                            if (node1 != null) {
                                JPopupMenu popupMenu2 = new JPopupMenu();
                                for (Node node2 : graph.getNodes()) {
                                    if (node1 != node2 && !graph.hasEdge(node1, node2)) {
                                        popupMenu2.add("To " + node2.getIndex()).addActionListener(e2 -> {
                                            graph.addEdge(node1, node2);
                                        });
                                    }
                                }
                                DrawingPanel.this.repaint();
                                popupMenu2.show(DrawingPanel.this, e.getX(), e.getY());
                            }
                        });

                        popupMenu.add("Delete edge").addActionListener(e1 -> {
                            Node node1 = graphPainter.getNodeAtPosition(mousePosition);
                            if (node1 != null) {
                                JPopupMenu popupMenu2 = new JPopupMenu();
                                for (Node node2 : graph.getNodes()) {
                                    if (node1 != node2 && graph.hasEdge(node1, node2)) {
                                        popupMenu2.add("To " + node2.getIndex()).addActionListener(e2 -> {
                                            graph.removeEdge(node1, node2);
                                        });
                                    }
                                }
                                DrawingPanel.this.repaint();
                                popupMenu2.show(DrawingPanel.this, e.getX(), e.getY());
                            }
                        });

                        DrawingPanel.this.repaint();
                        popupMenu.show(DrawingPanel.this, e.getX(), e.getY());
                    } else {
                        JPopupMenu popupMenu = new JPopupMenu();
                        popupMenu.add("Add node").addActionListener(e1 -> {
                            graph.addNode(mousePosition.x, mousePosition.y);
                        });

                        popupMenu.add("Add edge").addActionListener(e1 -> {
                            JPopupMenu popupMenuFrom = new JPopupMenu();
                            for (Node nodeFrom : graph.getNodes()) {
                                popupMenuFrom.add("From " + nodeFrom.getIndex()).addActionListener(e2 -> {
                                    JPopupMenu popupMenuTo = new JPopupMenu();
                                    for (Node nodeTo : graph.getNodes()) {
                                        if (nodeFrom != nodeTo && !graph.hasEdge(nodeFrom, nodeTo)) {
                                            popupMenuTo.add("To " + nodeTo.getIndex()).addActionListener(e3 -> {
                                                graph.addEdge(nodeFrom, nodeTo);
                                            });
                                        }
                                    }
                                    DrawingPanel.this.repaint();
                                    popupMenuTo.show(DrawingPanel.this, e.getX(), e.getY());
                                });
                            }
                            DrawingPanel.this.repaint();
                            popupMenuFrom.show(DrawingPanel.this, e.getX(), e.getY());
                        });

                        popupMenu.add("Delete edge").addActionListener(e1 -> {
                            JPopupMenu popupMenuFrom = new JPopupMenu();
                            for (Node nodeFrom : graph.getNodes()) {
                                popupMenuFrom.add("From " + nodeFrom.getIndex()).addActionListener(e2 -> {
                                    JPopupMenu popupMenuTo = new JPopupMenu();
                                    for (Node nodeTo : graph.getNodes()) {
                                        if (nodeFrom != nodeTo && graph.hasEdge(nodeFrom, nodeTo)) {
                                            popupMenuTo.add("To " + nodeTo.getIndex()).addActionListener(e3 -> {
                                                graph.removeEdge(nodeFrom, nodeTo);
                                            });
                                        }
                                    }
                                    DrawingPanel.this.repaint();
                                    popupMenuTo.show(DrawingPanel.this, e.getX(), e.getY());
                                });
                            }
                            DrawingPanel.this.repaint();
                            popupMenuFrom.show(DrawingPanel.this, e.getX(), e.getY());
                        });
                        
                        DrawingPanel.this.repaint();
                        popupMenu.show(DrawingPanel.this, e.getX(), e.getY());
                    }
                }

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                selectedNode = null;
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });

        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            // Move node or move the graph
            public void mouseDragged(MouseEvent e) {
                Point newPosition = e.getPoint();
                int dx = newPosition.x - mousePosition.x;
                int dy = newPosition.y - mousePosition.y;
                if (selectedNode != null) {
                    graphPainter.moveNode(selectedNode, newPosition.x, newPosition.y);
                    mousePosition = newPosition;
                } else if (e.getModifiersEx() == MouseEvent.BUTTON1_DOWN_MASK) {
                    graphPainter.applyTransformation(new Translation(dx, dy));
                    mousePosition = newPosition;
                }
            }

            // Tooltip
            @Override
            public void mouseMoved(MouseEvent e) {
                Node node = graphPainter.getNodeAtPosition(e.getPoint());
                if (node != null) {
                    graphPainter.highlightNode(node);
                    setToolTipText(graph.nodeToHTML(node));
                } else {
                    graphPainter.highlightNode(null);
                    setToolTipText(null);
                }
            }
        });

        // Zoom
        this.addMouseWheelListener(e -> {
            int rotation = e.getWheelRotation();
            double scale = 1;
            if (rotation < 0) {
                scale = 1.1;
            } else if (rotation > 0) {
                scale = 0.9;
            }
            graphPainter.applyTransformation(new Zoom(scale, e.getPoint()));
        });

        // Button to show/hide the grid
        JButton showGridButton = new JButton("Hide Grid");
        showGridButton.setFocusable(false);
        showGridButton.setPreferredSize(new Dimension(100, 30));
        showGridButton.addActionListener(e -> {
            graphPainter.switchGrid();
            if (graphPainter.isShowGrid()) {
                showGridButton.setText("Hide Grid");
            } else {
                showGridButton.setText("Show Grid");
            }
        });
        this.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.add(showGridButton);
        

    }

    public void setGraph(Graph graph) {
        this.graph = graph;
        this.graph.addObserver(this);
        this.graphPainter.setGraph(graph);
    }

    public void setVisualizationAlgorithm(VisualizationAlgorithm visualizationAlgorithm) {
        this.graphPainter.setVisualizationAlgorithm(visualizationAlgorithm);
    }

    public void setDrawingStrategy(DrawingStrategy drawingStrategy) {
        this.graphPainter.setDrawingStrategy(drawingStrategy);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) (g.create());

        // Anti-aliasing
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Paint the background
        graphPainter.drawBackground(g2d);

        // Paint the grid
        graphPainter.drawGrid(g2d);

        // Paint the graph
        graphPainter.drawGraph(g2d);
    }

}
