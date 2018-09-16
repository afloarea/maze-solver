package ro.ffa.data;

public class Graph {

    private GraphNode startNode;
    private GraphNode endNode;

    public Graph(GraphNode startNode, GraphNode endNode) {
        this.startNode = startNode;
        this.endNode = endNode;
    }

    public GraphNode getStartNode() {
        return startNode;
    }

    public GraphNode getEndNode() {
        return endNode;
    }
}
