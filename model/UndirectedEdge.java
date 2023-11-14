package model;

import java.util.Objects;

public class UndirectedEdge extends Edge {

    public UndirectedEdge(Node node1, Node node2) {
        super(node1, node2);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof UndirectedEdge)) return false;
        UndirectedEdge edge = (UndirectedEdge) obj;
        boolean case1 = this.node1.equals(edge.getNode1()) && this.node2.equals(edge.getNode2());
        boolean case2 = this.node1.equals(edge.getNode2()) && this.node2.equals(edge.getNode1());
        return case1 || case2;
    }

    @Override
    public int hashCode() {
        int node1Index = node1.getIndex();
        int node2Index = node2.getIndex();
        return Objects.hash(Math.min(node1Index, node2Index), Math.max(node1Index, node2Index));
    }

    @Override
    public boolean isDirected() {
        return false;
    }
    
}
