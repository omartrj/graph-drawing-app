package model;

import java.util.HashSet;

public class UndirectedGraph extends Graph {

    @Override
    public void addEdge(Node node1, Node node2) {
        this.edges.add(new UndirectedEdge(node1, node2));
        super.notifyObservers();
    }

    @Override
    public void removeEdge(Node node1, Node node2) {
        this.edges.remove(new UndirectedEdge(node1, node2));
        super.notifyObservers();
    }

    @Override
    public boolean hasEdge(Node node1, Node node2) {
        return this.edges.contains(new UndirectedEdge(node1, node2));
    }

    @Override
    public HashSet<Node> getNeighbors(Node node) {
        HashSet<Node> neighbors = new HashSet<>();
        for (Edge edge : this.edges) {
            if (edge.contains(node)) {
                neighbors.add(edge.getOtherNode(node));
            }
        }
        return neighbors;
    }

    @Override
    public boolean isNeighbor(Node node1, Node node2) {
        return this.edges.contains(new UndirectedEdge(node1, node2));
    }

    @Override
    public boolean isDirected() {
        return false;
    }

    @Override
    public String toString() {
        String s = "Undirected Graph\n";
        for (Node node : this.nodes) {
            s += "Node " + node.getIndex() + ": ";
            for (Node neighbor : getNeighbors(node)) {
                s += neighbor.getIndex() + " ";
            }
            s += "\n";
        }
        return s;
    }
}
