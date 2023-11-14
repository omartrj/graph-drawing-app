package model;

import java.util.Random;

public class UndirectedGraphFactory implements GraphFactory {

    @Override
    public UndirectedGraph generateCompleteGraph(int numberOfNodes) {
        UndirectedGraph graph = new UndirectedGraph();
        for (int i = 0; i < numberOfNodes; i++) {
            graph.addNode(new Node(i));
        }
        for (int i = 0; i < numberOfNodes; i++) {
            for (int j = i + 1; j < numberOfNodes; j++) {
                graph.addEdge(graph.getNode(i), graph.getNode(j));
            }
        }
        return graph;
    }

    @Override
    public UndirectedGraph generateRandomGraph(int numberOfNodes, double probability, int seed) {
        UndirectedGraph graph = new UndirectedGraph();
        Random random = new Random(seed);
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

    @Override
    public UndirectedGraph generateRandomGraph(int numberOfNodes, double probability) {
        UndirectedGraph graph = new UndirectedGraph();
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

    @Override
    public UndirectedGraph generateRegularGraph(int numberOfNodes, int degree) {
        /* If degree = 2m is even, put all the vertices around a circle, 
         * and join each to its m nearest neighbors on either side. 
         * If k=2m+1 is odd, and n is even, put the vertices on a circle, 
         * join each to its m nearest neighbors on each side, and also to the vertex directly opposite.
         * https://math.stackexchange.com/questions/142112/how-to-construct-a-k-regular-graph
         */

        UndirectedGraph graph = new UndirectedGraph();
        for (int i = 0; i < numberOfNodes; i++) {
            graph.addNode(new Node(i));
        }

        if (degree % 2 == 0) {
            for (int i = 0; i < numberOfNodes; i++) {
                for (int j = 1; j <= degree / 2; j++) {
                    graph.addEdge(graph.getNode(i), graph.getNode((i + j) % numberOfNodes));
                    graph.addEdge(graph.getNode(i), graph.getNode((i - j + numberOfNodes) % numberOfNodes));
                }
            }
        } else {
            if (numberOfNodes % 2 == 0) {
                for (int i = 0; i < numberOfNodes; i++) {
                    for (int j = 1; j <= degree / 2; j++) {
                        graph.addEdge(graph.getNode(i), graph.getNode((i + j) % numberOfNodes));
                        graph.addEdge(graph.getNode(i), graph.getNode((i - j + numberOfNodes) % numberOfNodes));
                    }
                    graph.addEdge(graph.getNode(i), graph.getNode((i + numberOfNodes / 2) % numberOfNodes));
                }
            } else {
                for (int i = 0; i < numberOfNodes; i++) {
                    for (int j = 1; j <= degree / 2; j++) {
                        graph.addEdge(graph.getNode(i), graph.getNode((i + j) % numberOfNodes));
                        graph.addEdge(graph.getNode(i), graph.getNode((i - j + numberOfNodes) % numberOfNodes));
                    }
                }
            }
        }
        return graph;
    }

    @Override
    public UndirectedGraph generateOctahedron() {
        UndirectedGraph graph = new UndirectedGraph();
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
    public UndirectedGraph generateHypercube(int n) {
        int numberOfNodes = (int) Math.pow(2, n);
        return generateRegularGraph(numberOfNodes, n);
    }
    
}
