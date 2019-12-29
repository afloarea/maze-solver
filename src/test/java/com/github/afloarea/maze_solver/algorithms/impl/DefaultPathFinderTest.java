package com.github.afloarea.maze_solver.algorithms.impl;

import com.github.afloarea.maze_solver.algorithms.GraphNode;
import com.github.afloarea.maze_solver.algorithms.PathFinder;
import com.github.afloarea.maze_solver.algorithms.PathSearchStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

class DefaultPathFinderTest {

    private static final PathFinder PATH_FINDER = new DefaultPathFinder();

    @Test
    void testSimpleGraph() {

        // setup
        final TestNode root = new TestNode(1);
        final TestNode second = new TestNode(2);
        final TestNode third = new TestNode(3);
        final TestNode fourth = new TestNode(4);
        final TestNode fifth = new TestNode(5);

        GraphNode.createNeighbours(root, second, 3);
        GraphNode.createNeighbours(root, third, 1);
        GraphNode.createNeighbours(third, fourth, 2);
        GraphNode.createNeighbours(second, fifth, 4);
        GraphNode.createNeighbours(fourth, fifth, 1);

        // execute
        final Queue<TestNode> result = PATH_FINDER.findShortestPath(root, fifth, PathSearchStrategy.DIJKSTRA);

        // evaluate
        Assertions.assertEquals(4, result.size()); // 1 - 3 - 4 - 5
        Assertions.assertEquals(1, (result.remove()).getId());
        Assertions.assertEquals(3, (result.remove()).getId());
        Assertions.assertEquals(4, (result.remove()).getId());
        Assertions.assertEquals(5, (result.remove()).getId());
    }

    private static final class TestNode implements GraphNode<TestNode> {
        private final int id;

        private final Map<TestNode, Integer> distances = new HashMap<>();

        public TestNode(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        @Override
        public Map<TestNode, Integer> getNeighbourDistances() {
            return distances;
        }

        @Override
        public void addNeighbour(TestNode neighbour, int distance) {
            distances.put(neighbour, distance);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TestNode)) return false;
            TestNode testNode = (TestNode) o;
            return id == testNode.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

}
