package model;

import java.awt.geom.Point2D;

public class Node {

    private int index;
    private Point2D.Double position;

    public Node(int index, Point2D.Double position) {
        this.index = index;
        this.position = position;
    }

    public Node(int index, double x, double y) {
        this.index = index;
        this.position = new Point2D.Double(x, y);
    }

    public Node(int index) {
        this.index = index;
        this.position = new Point2D.Double(0, 0);
    }

    public int getIndex() {
        return index;
    }

    public Point2D.Double getPosition() {
        return position;
    }

    public double getX() {
        return position.getX();
    }

    public double getY() {
        return position.getY();
    }

    public void setPosition(double x, double y) {
        this.position = new Point2D.Double(x, y);
    }

    public void setPosition(Point2D.Double position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Node) {
            Node other = (Node) obj;
            return this.index == other.index;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return index;
    }

    @Override
    public String toString() {
        String s = "";
        s += "Node " + this.index + "\n";
        s += "Position: (" + String.format("%.2f", this.position.getX()) + ", " + String.format("%.2f", this.position.getY()) + ")\n";
        return s;
    }
}
