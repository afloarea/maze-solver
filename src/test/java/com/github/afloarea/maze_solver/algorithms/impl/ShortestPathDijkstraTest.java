package com.github.afloarea.maze_solver.algorithms.impl;

import com.github.afloarea.maze_solver.data.Graph;
import com.github.afloarea.maze_solver.data.GraphNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ShortestPathDijkstraTest {

    @Test
    void testSimpleGraph() {
        final GraphNode root = new GraphNode(1);
        final GraphNode second = new GraphNode(2);
        final GraphNode third = new GraphNode(3);
        final GraphNode fourth = new GraphNode(4);
        final GraphNode fifth = new GraphNode(5);

        GraphNode.createNeighbours(root, second, 3);
        GraphNode.createNeighbours(root, third, 1);
        GraphNode.createNeighbours(third, fourth, 2);
        GraphNode.createNeighbours(second, fifth, 4);
        GraphNode.createNeighbours(fourth, fifth, 1);

        final Graph graph =  new Graph(root, fifth);

        final List<GraphNode> result = new ShortestPathDijkstra().calculateShortestPath(graph);
        Assertions.assertEquals(4, result.size()); // 1 - 3 - 4 - 5
    }

}
