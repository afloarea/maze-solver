package com.github.afloarea.maze_solver.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class GraphNode {

    private final int id;

    private final Map<GraphNode, Integer> neighbourDistances = new HashMap<>();

    public static void createNeighbours(GraphNode firstNode, GraphNode secondNode, int distance) {
        firstNode.neighbourDistances.put(secondNode, distance);
        secondNode.neighbourDistances.put(firstNode, distance);
    }

    public GraphNode(int id) {
        this.id = id;
    }

    public Map<GraphNode, Integer> getNeighbourDistances() {
        return neighbourDistances;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GraphNode)) return false;
        final GraphNode graphNode = (GraphNode) o;
        return id == graphNode.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
