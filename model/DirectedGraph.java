package model;

import java.util.HashSet;

public class DirectedGraph extends Graph {

    @Override
    public void addEdge(Node node1, Node node2) {
        this.edges.add(new DirectedEdge(node1, node2));
        super.notifyObservers();
    }

    @Override
    public void removeEdge(Node node1, Node node2) {
        this.edges.remove(new DirectedEdge(node1, node2));
        super.notifyObservers();
    }

    @Override
    public boolean hasEdge(Node node1, Node node2) {
        return this.edges.contains(new DirectedEdge(node1, node2));
    }

    @Override
    public HashSet<Node> getNeighbors(Node node) {
        HashSet<Node> neighbors = new HashSet<>();
        neighbors.addAll(getInNeighbors(node));
        neighbors.addAll(getOutNeighbors(node));
        return neighbors;
    }

    public HashSet<Node> getInNeighbors(Node node) {
        HashSet<Node> inNeighbors = new HashSet<>();
        for (Edge edge : this.edges) {
            if (edge.getNode2().equals(node)) {
                inNeighbors.add(edge.getNode1());
            }
        }
        return inNeighbors;
    }

    public HashSet<Node> getOutNeighbors(Node node) {
        HashSet<Node> outNeighbors = new HashSet<>();
        for (Edge edge : this.edges) {
            if (edge.getNode1().equals(node)) {
                outNeighbors.add(edge.getNode2());
            }
        }
        return outNeighbors;
    }

    @Override
    public boolean isNeighbor(Node node1, Node node2) {
        return isInNeighbor(node1, node2) || isOutNeighbor(node1, node2);
    }

    public boolean isInNeighbor(Node node1, Node node2) {
        for (Edge edge : this.edges) {
            if (edge.getNode2().equals(node1) && edge.getNode1().equals(node2)) {
                return true;
            }
        }
        return false;
    }

    public boolean isOutNeighbor(Node node1, Node node2) {
        for (Edge edge : this.edges) {
            if (edge.getNode1().equals(node1) && edge.getNode2().equals(node2)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isDirected() {
        return true;
    }

    @Override
    public String toString() {
        String s = "Directed Graph\n";
        for (Node node : this.nodes) {
            s += "Node " + node.getIndex() + ": ";
            for (Node neighbor : getOutNeighbors(node)) {
                s += neighbor.getIndex() + " ";
            }
            s += "\n";
        }
        return s;
    }


    
}
