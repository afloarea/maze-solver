package com.github.afloarea.maze_solver.convertors;

import com.github.afloarea.maze_solver.data.Graph;
import com.github.afloarea.maze_solver.data.GraphNode;

import java.util.List;

/**
 * Converter between image and graph.
 */
public interface BidirectionalConverter {

    Graph extractGraphFromImage();

    void updateImage(List<GraphNode> route);

}
