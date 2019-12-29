package com.github.afloarea.maze_solver.algorithms;

import java.util.Map;

/**
 * Interface for representing a graph node.
 *
 * @param <N> used to ensure all nodes in a graph have the same type.
 */
public interface GraphNode<N extends GraphNode<N>> {

    Map<N, Integer> getNeighbourDistances();

    void addNeighbour(N neighbour, int distance);

    /**
     * Get an estimate of the distance of this node to the provided node.
     * This is an optional method only used by heuristic algorithms (such as A*).
     *
     * @param node the node to which to calculate the heuristic
     * @return an approximation of the distance
     */
    default int getHeuristicTo(N node) {
        return 0;
    }

    static <T extends GraphNode<T>> void createNeighbours(T firstNode, T secondNode, int distance) {
        firstNode.addNeighbour(secondNode, distance);
        secondNode.addNeighbour(firstNode, distance);
    }
}
