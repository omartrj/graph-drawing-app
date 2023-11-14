package model;

import java.util.Objects;

public class DirectedEdge extends Edge {

    public DirectedEdge(Node node1, Node node2) {
        super(node1, node2);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof DirectedEdge)) return false;
        DirectedEdge edge = (DirectedEdge) obj;
        return (this.node1.equals(edge.getNode1()) && this.node2.equals(edge.getNode2()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(node1, node2);
    }

    @Override
    public boolean isDirected() {
        return true;
    }
    
    
}
