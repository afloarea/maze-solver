package com.github.afloarea.maze_solver.algorithms;

import com.github.afloarea.maze_solver.algorithms.model.Graph;
import com.github.afloarea.maze_solver.algorithms.model.GraphNode;

import java.util.List;

/**
 * General algorithm interface for finding the shortest path in a graph.
 */
public interface ShortestPathAlgorithm {

    /**
     * Find the shortest path in a graph.
     * @param graph the graph
     * @return the shortest path
     */
    List<GraphNode> calculateShortestPath(Graph graph);

}
