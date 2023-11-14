package model;

import java.util.HashMap;
import java.util.HashSet;
import java.awt.geom.Point2D;

public class FruchtermanSpringAlgorithm implements VisualizationAlgorithm {

/*Algorithm 2: Fruchterman-Reingold
area = WIDTH ∗ LENGTH ; 
initialize G = (V, E) ; 
k = sqrt(area/|V|)  // compute optimal pairwise distance ;
function fr(x) = (k^2)/x  // compute repulsive force ; 
for i = 1 to iterations do
    foreach v ∈ V do
        v.disp := 0; // initialize displacement ;
        for u ∈ V do
            if (u != v) then
                delta = v.pos − u.pos ;
                v.disp ← v.disp + (delta/|delta|) ∗ fr(|delta|) ;
    function fa(x) = (x^2)/k  // compute attractive force ; ; 
    foreach e ∈ E do
        delta = e.v.pos − e.u.pos ; 
        e.v.pos = e.v.pos - (delta/|delta|) ∗ fa(|delta|) ;
        e.u.pos = e.u.pos + (delta/|delta|) ∗ fa(|delta|) ;
    foreach v ∈ V do
        v.pos = v.pos + (v.disp/|v.disp|) ∗ min(v.disp, t);
        v.pos.x = min(W/2, max(−W/2, v.pos.x));
        v.pos.y = min(L/2, max(−L/2, v.pos.y));
    t = cool(t) ;  */

    private static final int MAX_ITERATIONS = 1000;
    private static final double AREA = WIDTH * HEIGHT;

    private HashSet<Node> nodes;
    private HashSet<Edge> edges;
    private HashMap<Node, Point2D.Double> displacements;
    private double k; // optimal pairwise distance
    private double t; // temperature

    public FruchtermanSpringAlgorithm(double temperature) {
        this.t = temperature;
    }

    @Override
    public void initialize(Graph graph) {
        this.nodes = graph.getNodes();
        this.edges = graph.getEdges();

        //Associate each vertex with a point called displacement
        this.displacements = new HashMap<>();
    }

    @Override
    public void execute() {

        //Compute optimal pairwise distance
        k = Math.sqrt(AREA / nodes.size());

        //Initialize Positions: place vertices of G in random locations;
        for (Node v : nodes) {
            double x = Math.random() * WIDTH;
            double y = Math.random() * HEIGHT;
            v.setPosition(x, y);
        }

        //Initialize displacements
        for (Node v : nodes) {
            displacements.put(v, new Point2D.Double(0, 0));
        }

        //for i = 1 to M do
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            for (Node v : nodes) {
                Point2D.Double disp = displacements.get(v);
                for (Node u : nodes) {
                    if (v != u) {
                        Point2D.Double delta = new Point2D.Double(v.getX() - u.getX(), v.getY() - u.getY());
                        double distance = delta.distance(0, 0) + 1;
                        double repulsiveForce = fr(distance);
                        disp.x += (delta.x / distance) * repulsiveForce;
                        disp.y += (delta.y / distance) * repulsiveForce;
                    }
                }
            }

            for (Edge edge : edges) {
                Node v = edge.getNode1();
                Point2D.Double vDisp = displacements.get(v);
                Node u = edge.getNode2();
                Point2D.Double uDisp = displacements.get(u);
                Point2D.Double delta = new Point2D.Double(v.getX() - u.getX(), v.getY() - u.getY());
                double distance = delta.distance(0, 0) + 1;
                double attractiveForce = fa(distance);
                vDisp.x -= (delta.x / distance) * attractiveForce;
                vDisp.y -= (delta.y / distance) * attractiveForce;
                uDisp.x += (delta.x / distance) * attractiveForce;
                uDisp.y += (delta.y / distance) * attractiveForce;
            }

            for (Node v : nodes) {
                Point2D.Double disp = displacements.get(v);
                double distance = disp.distance(0, 0) + 1;
                double min = Math.min(distance, t);
                v.setPosition(v.getX() + (disp.x / distance) * min, v.getY() + (disp.y / distance) * min);
                v.setPosition(Math.min(WIDTH / 2, Math.max(-WIDTH / 2, v.getX())), Math.min(HEIGHT / 2, Math.max(-HEIGHT / 2, v.getY())));
            }

            t = cool(t);
        }   

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

    // Compute repulsive force
    private double fr(double x) {
        return (k * k) / x;
    }

    // Compute attractive force
    private double fa(double x) {
        return (x * x) / k;
    }

    private double cool(double t) {
        return t - 0.1;
    }

    @Override
    public void debug() {
        System.out.println("Fruchterman-Reingold Algorithm");
        System.out.println("k = " + k);
        System.out.println("t = " + t);
        System.out.println("Displacements:");
        for (Node v : nodes) {
            System.out.println(v + ": " + displacements.get(v));
        }
    }

    
}