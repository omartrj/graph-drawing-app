package model;

import java.util.HashSet;

public class BarycenterAlgorithm implements VisualizationAlgorithm {

    /* Input: G = (V, E) with V = V0 ∩ V1, with fixed vertices V0 and free vertices V1; 
    a strictly convex polygon P with |V0| vertices.
    Output: a position pv for each vertex of V, 
    such that the fixed vertices form a convex polygon P .

    Initialize V0: place fixed vertices u ∈ V0 at corners of P ;
    Initialize V1: place free vertices v ∈ V1 at the origin;
    repeat
        foreach free vertex v ∈ V1 do
            xv = 1/deg(v) * sum_{u ∈ N(v)} xu;
            yv = 1/deg(v) * sum_{u ∈ N(v)} yu;
    until xv and yv converge for all free vertices v; */

    final double RADIUS = Math.min(WIDTH, HEIGHT) / 2;
    final int MAX_ITERATIONS = 1000;

    private Graph graph;
    private HashSet<Node> nodes;
    private int numFixedVertices;
    private double angle;

    public BarycenterAlgorithm(int numFixedVertices) {
        this.numFixedVertices = numFixedVertices;
        if (numFixedVertices < 3) {
            this.numFixedVertices = 3;
        }
    }

    @Override
    public void initialize(Graph graph) {
        this.graph = graph;
        this.nodes = graph.getNodes();

        // Max value for numFixedVertices is the number of nodes in the graph
        if (numFixedVertices > nodes.size()) {
            numFixedVertices = nodes.size();
        }

        this.angle = 2 * Math.PI / numFixedVertices;
    }

    @Override
    public void execute() {
        HashSet<Node> fixedNodes = new HashSet<>();
        HashSet<Node> freeNodes = new HashSet<>();
        
        //Put the first numFixedVertices nodes in the fixed set
        int k = 0;
        for (Node v : nodes) {
            if (k < numFixedVertices) {
                fixedNodes.add(v);
            } else {
                freeNodes.add(v);
            }
            k++;
        }

        //Put the fixed nodes in a convex polygon
        int i = 0;
        for (Node v : fixedNodes) {
            double x = CENTER_X + RADIUS * Math.cos(i * angle);
            double y = CENTER_Y + RADIUS * Math.sin(i * angle);
            v.setPosition(x, y);
            i++;
        }

        //Put the free nodes in the center
        freeNodes.stream().forEach(e -> {
            e.setPosition(0, 0);
        });

        //Iterate until convergence
        for (int j = 0; j < MAX_ITERATIONS; j++) {
            for (Node v : freeNodes) {
                double sumX = 0;
                double sumY = 0;
                for (Node u : graph.getNeighbors(v)) {
                    sumX += u.getX();
                    sumY += u.getY();
                }
                if (graph.getDegree(v) > 0) {
                    double x = sumX / graph.getDegree(v);
                    double y = sumY / graph.getDegree(v);
                    v.setPosition(x, y);
                } else {
                    v.setPosition(0, 0);
                }
            }
        }
    }

    @Override
    public void debug() {
        nodes.stream().forEach(e -> {
            System.out.println("Node " + e.getIndex() + " at (" + e.getX() + ", " + e.getY() + ")");
        });
    }
}
