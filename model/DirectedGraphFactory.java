package model;

import java.util.Random;

public class DirectedGraphFactory implements GraphFactory {

    @Override
    public DirectedGraph generateCompleteGraph(int numberOfNodes) {
        DirectedGraph graph = new DirectedGraph();
        for (int i = 0; i < numberOfNodes; i++) {
            graph.addNode(new Node(i));
        }
        for (int i = 0; i < numberOfNodes; i++) {
            for (int j = 0; j < numberOfNodes; j++) {
                if (i != j) {
                    graph.addEdge(graph.getNode(i), graph.getNode(j));
                }
            }
        }
        return graph;
    }

    @Override
    public DirectedGraph generateRandomGraph(int numberOfNodes, double probability) {
        DirectedGraph graph = new DirectedGraph();
        Random random = new Random();
        for (int i = 0; i < numberOfNodes; i++) {
            graph.addNode(new Node(i));
        }
        for (int i = 0; i < numberOfNodes; i++) {
            for (int j = 0; j < numberOfNodes; j++) {
                if (random.nextDouble() < probability && i != j) {
                    graph.addEdge(graph.getNode(i), graph.getNode(j));
                }
            }
        }
        return graph;
    }

    @Override
    public DirectedGraph generateRandomGraph(int numberOfNodes, double probability, int seed) {
        DirectedGraph graph = new DirectedGraph();
        Random random = new Random(seed);
        for (int i = 0; i < numberOfNodes; i++) {
            graph.addNode(new Node(i));
        }
        for (int i = 0; i < numberOfNodes; i++) {
            for (int j = 0; j < numberOfNodes; j++) {
                if (random.nextDouble() < probability && i != j) {
                    graph.addEdge(graph.getNode(i), graph.getNode(j));
                }
            }
        }
        return graph;
    }

    @Override
    public DirectedGraph generateOctahedron() {
        DirectedGraph graph = new DirectedGraph();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);

        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        graph.addNode(node5);
        graph.addNode(node6);

        graph.addEdge(node1, node2);
        graph.addEdge(node1, node3);
        graph.addEdge(node1, node4);
        graph.addEdge(node1, node6);

        graph.addEdge(node2, node3);
        graph.addEdge(node2, node4);
        graph.addEdge(node2, node5);

        graph.addEdge(node3, node5);
        graph.addEdge(node3, node6);

        graph.addEdge(node4, node5);
        graph.addEdge(node4, node6);

        graph.addEdge(node5, node6);

        return graph;
    }

    @Override
    public DirectedGraph generateRegularGraph(int numberOfNodes, int degree) {
        DirectedGraph graph = new DirectedGraph();

        for (int i = 0; i < numberOfNodes; i++) {
            graph.addNode(new Node(i));
        }

        if (degree % 2 == 0) {
            for (int i = 0; i < numberOfNodes; i++) {
                for (int j = 1; j <= degree / 2; j++) {
                    if (i != (i + j) % numberOfNodes) {
                        graph.addEdge(graph.getNode(i), graph.getNode((i + j) % numberOfNodes));
                    }
                    if (i != (i - j + numberOfNodes) % numberOfNodes) {
                        graph.addEdge(graph.getNode(i), graph.getNode((i - j + numberOfNodes) % numberOfNodes));
                    }
                }
            }
        } else {
            for (int i = 0; i < numberOfNodes; i++) {
                for (int j = 1; j <= degree / 2; j++) {
                    if (i != (i + j) % numberOfNodes) {
                        graph.addEdge(graph.getNode(i), graph.getNode((i + j) % numberOfNodes));
                    }
                    if (i != (i - j + numberOfNodes) % numberOfNodes) {
                        graph.addEdge(graph.getNode(i), graph.getNode((i - j + numberOfNodes) % numberOfNodes));
                    }
                }
                if (i != (i + degree / 2 + 1) % numberOfNodes) {
                    graph.addEdge(graph.getNode(i), graph.getNode((i + degree / 2 + 1) % numberOfNodes));
                }
            }
        }
        return graph;
    }

    @Override
    public DirectedGraph generateHypercube(int n) {
        int numberOfNodes = (int) Math.pow(2, n);
        return generateRegularGraph(numberOfNodes, n);
    }

    public DirectedGraph generateDAG(int numberOfNodes, double probability) {
        DirectedGraph graph = new DirectedGraph();
        Random random = new Random();
        for (int i = 0; i < numberOfNodes; i++) {
            graph.addNode(new Node(i));
        }
        for (int i = 0; i < numberOfNodes; i++) {
            for (int j = i + 1; j < numberOfNodes; j++) {
                if (random.nextDouble() < probability) {
                    graph.addEdge(graph.getNode(i), graph.getNode(j));
                }
            }
        }
        return graph;
    }
    
}
