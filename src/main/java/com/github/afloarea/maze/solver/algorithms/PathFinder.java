package com.github.afloarea.maze.solver.algorithms;

import com.github.afloarea.maze.solver.algorithms.impl.DefaultPathFinder;

import java.util.Queue;

/**
 * General algorithm interface for finding the shortest path in a graph.
 */
public interface PathFinder {

    /**
     * Find the shortest path in a graph.
     *
     * @param startNode      the starting node of the resulting path
     * @param endNode        the final node in the resulting path
     * @param <T>            the type of the graph node
     * @return the shortest path
     */
    <T extends GraphNode<T>> Queue<T> findShortestPath(T startNode, T endNode);

    static PathFinder ofStrategy(PathSearchStrategy strategy) {
        return new DefaultPathFinder(strategy);
    }

}
