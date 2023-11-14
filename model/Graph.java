package model;

import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JPanel;

public abstract class Graph {

    protected HashSet<Node> nodes;
    protected HashSet<Edge> edges;
    protected int nodeCount;
    protected ArrayList<JPanel> observers;

    //Abstract methods
    public abstract void addEdge(Node node1, Node node2);
    public abstract void removeEdge(Node node1, Node node2);
    public abstract HashSet<Node> getNeighbors(Node node);
    public abstract boolean isNeighbor(Node node1, Node node2);
    public abstract boolean hasEdge(Node node1, Node node2);
    public abstract boolean isDirected();

    //Concrete methods
    public Graph() {
        this.nodes = new HashSet<>();
        this.edges = new HashSet<>();
        this.nodeCount = 0;
    }

    public void addObserver(JPanel observer) {
        if (observers == null) {
            observers = new ArrayList<>();
        }
        observers.add(observer);
    }

    public void removeObserver(JPanel observer) {
        if (observers != null) {
            observers.remove(observer);
        }
    }

    public void notifyObservers() {
        if (observers != null) {
            observers.stream().forEach(e -> e.repaint());
        }
    }

    public void addNode(Node node) {
        nodes.add(node);
        nodeCount++;
        notifyObservers();
    }

    public void addNode() {
        Node node = new Node(nodeCount);
        addNode(node);
    }

    public void addNode(double x, double y) {
        Node node = new Node(nodeCount, x, y);
        addNode(node);
    }

    public void removeNode(Node node) {
        nodes.remove(node);
        edges.removeIf(e -> e.contains(node));
        notifyObservers();
    }

    public HashSet<Node> getNodes() {
        return nodes;
    }

    public HashSet<Edge> getEdges() {
        return edges;
    }

    public int getNodeCount() {
        return nodeCount;
    }

    public Node getNode(int index) {
        for (Node node : nodes) {
            if (node.getIndex() == index) {
                return node;
            }
        }
        return null;
    }

    public int getDegree(Node node) {
        return getNeighbors(node).size();
    }

    public int size() {
        return nodes.size();
    }

    public String nodeToString(Node node) {
        String s = "";
        s += node.toString();
        s += "Degree: " + getDegree(node) + "\n";
        return s;
    }

    public String nodeToHTML(Node node) {
        String s = this.nodeToString(node);
        s = "<html>" + s.replaceAll("\n", "<br>") + "</html>";
        return s;
    }

    @Override
    public String toString() {
        String s = "";
        for (Node node : nodes) {
            s += "Node " + node.getIndex() + ": ";
            for (Node neighbor : getNeighbors(node)) {
                s += neighbor.getIndex() + " - ";
            }
            s += "\n";
        }
        return s;
    }
}
