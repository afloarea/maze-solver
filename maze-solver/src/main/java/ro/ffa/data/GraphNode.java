package ro.ffa.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GraphNode {

    private final int id;

    private final List<GraphNode> neighbours = new ArrayList<>();
    private final List<Integer> distances = new ArrayList<>();

    public static void createNeighbours(GraphNode firstNode, GraphNode secondNode, int distance) {
        firstNode.neighbours.add(secondNode);
        firstNode.distances.add(distance);
        secondNode.neighbours.add(firstNode);
        secondNode.distances.add(distance);
    }

    public GraphNode(int id) {
        this.id = id;
    }

    public int getNeighbourDistance(int neighbourIndex) {
        return distances.get(neighbourIndex);
    }

    public GraphNode getNeighbour(int neighbourIndex) {
        return neighbours.get(neighbourIndex);
    }

    public int neighboursAmount() {return neighbours.size();}

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphNode graphNode = (GraphNode) o;
        return id == graphNode.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
