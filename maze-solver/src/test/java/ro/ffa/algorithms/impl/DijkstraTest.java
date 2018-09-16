package ro.ffa.algorithms.impl;

import org.junit.Assert;
import ro.ffa.data.Graph;
import org.junit.Test;
import ro.ffa.data.GraphNode;

import java.util.List;

public class DijkstraTest {

    @Test
    public void testSimpleGraph() {
        final List<GraphNode> result = new Dijkstra().calculateShortestPath(createSimpleGraph());
        Assert.assertEquals(4, result.size()); // 1 - 3 - 4 - 5
    }

    private static Graph createSimpleGraph() {
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

        return new Graph(root, fifth);
    }

}
