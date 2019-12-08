package com.github.afloarea.maze_solver.algorithms;

import com.github.afloarea.maze_solver.data.Graph;
import com.github.afloarea.maze_solver.data.GraphNode;

import java.util.List;

public interface ShortestPathAlgorithm {

    List<GraphNode> calculateShortestPath(Graph graph);

}
