package model;

import java.awt.geom.Point2D;
import java.util.HashSet;

public class SpringAlgorithm implements VisualizationAlgorithm {

    private final int MAX_ITERATIONS = 1000;
    private final double STEP_SIZE = 0.1;
    private final double DIST_AT_REST = 300;

    private Graph graph;
    private HashSet<Node> nodes;
    private double attractionConst;
    private double repulsionConst;

    public SpringAlgorithm(double attractionConst, double repulsionConst) {
        this.attractionConst = attractionConst; // Default: 600
        this.repulsionConst = repulsionConst; // Default: 600
    }

    @Override
    public void initialize(Graph graph) {
        this.graph = graph;
        this.nodes = graph.getNodes();
    }

    @Override
    public void execute() {
        /* To embed a graph we replace the vertices by steel rings and replace each edge with a
        spring to form a mechanical system. The vertices are placed in some initial layout and
        let go so that the spring forces on the rings move the system to a minimal energy state.
        Two practical adjustments are made to this idea: firstly, logarithmic strength springs are
        used; that is, the force exerted by a spring is: c1 ∗ log(d/c2), where d is the length of the
        spring, and c1 and c2 are constants. Experience shows that Hookes Law (linear) springs
        are too strong when the vertices are far apart; the logarithmic force solves this problem.
        Note that the springs exert no force when d = c2. Secondly, we make nonadjacent
        vertices repel each other. An inverse square law force, c3/√d, where c3 is constant and
        d is the distance between the vertices, is suitable.

        Algorithm 1: SPRING
        Input: Graph G
        Output: Straight-line drawing of G
        Initialize Positions: place vertices of G in random locations;
        for i = 1 to M do
        calculate the force acting on each vertex;
        move the vertex c4 ∗ (force on vertex); */

        //Initialize Positions: place vertices of G in random locations;
        for (Node v : nodes) {
            double x = Math.random() * WIDTH;
            double y = Math.random() * HEIGHT;
            v.setPosition(x, y);
        }

        //for i = 1 to M do
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            //calculate the force acting on each vertex;
            for (Node v : nodes) {
                Point2D.Double force = new Point2D.Double(0, 0);
                force.x -= attractionForce(v).x;
                force.y -= attractionForce(v).y;
                force.x += repulsionForce(v).x;
                force.y += repulsionForce(v).y;
                //move the vertex c4 ∗ (force on vertex);
                v.setPosition(v.getX() + STEP_SIZE * force.x, v.getY() + STEP_SIZE * force.y);
            }
        }

        //At the end, translate the graph so that the center of the graph is at the center of the screen
        //At the end, translate the graph so that its center is at the center of the window
        double xCenter = 0;
        double yCenter = 0;
        for (Node v : nodes) {
            xCenter += v.getX();
            yCenter += v.getY();
        }
        xCenter /= nodes.size();
        yCenter /= nodes.size();
        for (Node v : nodes) {
            v.setPosition(v.getX() - xCenter + WIDTH / 2, v.getY() - yCenter + HEIGHT / 2);
        }



    }

    private Point2D.Double repulsionForce(Node v) {
        Point2D.Double force = new Point2D.Double(0, 0);
        for (Node u : nodes) {
            if (u != v) {
                double dx = v.getX() - u.getX();
                double dy = v.getY() - u.getY();
                double distance = Math.sqrt(dx * dx + dy * dy);
                double forceMagnitude = repulsionConst / Math.sqrt(distance);
                force.x += forceMagnitude * dx / distance;
                force.y += forceMagnitude * dy / distance;
            }
        }
        return force;
    }

    private Point2D.Double attractionForce(Node v) {
        Point2D.Double force = new Point2D.Double(0, 0);
        for (Node neighbor : graph.getNeighbors(v)) {
            double dx = v.getX() - neighbor.getX();
            double dy = v.getY() - neighbor.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);
            double forceMagnitude = attractionConst * Math.log(distance / DIST_AT_REST);
            force.x += forceMagnitude * dx / distance;
            force.y += forceMagnitude * dy / distance;
        }
        return force;
    }

    @Override
    public void debug() {
        nodes.stream().forEach(e -> {
            System.out.println("Node " + e.getIndex() + " at (" + e.getX() + ", " + e.getY() + ")");
        });
    }
    
}
