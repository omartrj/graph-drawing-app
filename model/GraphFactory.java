package model;

public interface GraphFactory {

    public Graph generateCompleteGraph(int numberOfNodes);

    public Graph generateRandomGraph(int numberOfNodes, double probability);

    public Graph generateRandomGraph(int numberOfNodes, double probability, int seed);

    public Graph generateRegularGraph(int numberOfNodes, int degree);

    public Graph generateOctahedron();

    public Graph generateHypercube(int n);
}
