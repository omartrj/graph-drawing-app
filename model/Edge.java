package model;

public abstract class Edge {
    
    protected Node node1;
    protected Node node2;

    //Abstract methods
    public abstract boolean equals(Object obj);
    public abstract int hashCode();
    public abstract boolean isDirected();
    
    //Concrete methods
    public Edge(Node node1, Node node2) {
        this.node1 = node1;
        this.node2 = node2;
    }
    
    public Node getNode1() {
        return node1;
    }
    
    public Node getNode2() {
        return node2;
    }

    public Node getOtherNode(Node node) {
        if (node1.equals(node)) {
            return node2;
        } else if (node2.equals(node)) {
            return node1;
        } else {
            return null;
        }
    }
    
    public boolean contains(Node node) {
        return node1.equals(node) || node2.equals(node);
    }
    
    @Override
    public String toString() {
        return "(" + node1 + ", " + node2 + ")";
    }
}
