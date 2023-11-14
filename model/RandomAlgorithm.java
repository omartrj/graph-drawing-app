package model;

import java.util.HashSet;

public class RandomAlgorithm implements VisualizationAlgorithm {

    private HashSet<Node> nodes;

    @Override
    public void initialize(Graph graph) {
        this.nodes = graph.getNodes();
    }

    @Override
    public void execute() {
        nodes.stream().forEach(e -> {
            double x = Math.random() * WIDTH;
            double y = Math.random() * HEIGHT;
            e.setPosition(x, y);
        });
    }

    @Override
    public void debug() {
        nodes.stream().forEach(e -> {
            System.out.println("Node " + e.getIndex() + " at (" + e.getX() + ", " + e.getY() + ")");
        });
    }
    
}
