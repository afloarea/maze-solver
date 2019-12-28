package com.github.afloarea.maze_solver.algorithms;

import java.util.Map;

/**
 * Interface for representing a graph node.
 * @param <N> is used to ensure all nodes in a graph have the same type.
 */
public interface GraphNode<N extends GraphNode<N>> {

    Map<N, Integer> getNeighbourDistances();

    void addNeighbour(N neighbour, int distance);

    default int getHeuristicTo(N node) {
        return 0;
    }

    static <T extends GraphNode<T>> void createNeighbours(T firstNode, T secondNode, int distance) {
        firstNode.addNeighbour(secondNode, distance);
        secondNode.addNeighbour(firstNode, distance);
    }
}
