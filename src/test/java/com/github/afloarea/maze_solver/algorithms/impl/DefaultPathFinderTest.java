package com.github.afloarea.maze_solver.algorithms.impl;

import com.github.afloarea.maze_solver.algorithms.GraphNode;
import com.github.afloarea.maze_solver.algorithms.PathFinder;
import com.github.afloarea.maze_solver.algorithms.PathSearchStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.*;

import static com.github.afloarea.maze_solver.algorithms.GraphNode.createNeighbours;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultPathFinderTest {

    private static final PathFinder PATH_FINDER = new DefaultPathFinder();

    @ParameterizedTest
    @EnumSource(PathSearchStrategy.class)
    void testFindSimplePath(PathSearchStrategy strategy) {
        // setup
        final TestNode root = new TestNode(1);
        final TestNode second = new TestNode(2);
        final TestNode third = new TestNode(3);
        final TestNode fourth = new TestNode(4);

        createNeighbours(root, second, 6);
        createNeighbours(root, third, 6);
        createNeighbours(second, fourth, 4);
        createNeighbours(third, fourth, 4);

        // execute
        final Deque<TestNode> result = new ArrayDeque<>(PATH_FINDER.findShortestPath(root, fourth, strategy));
        assertEquals(1, result.getFirst().getId());
        assertEquals(4, result.getLast().getId());
    }

    @Test
    void testAStar() {
        // setup
        final TestNode root = new TestNode(1, 10);
        final TestNode second = new TestNode(2, 6);
        final TestNode third = new TestNode(3, 4);
        final TestNode fourth = new TestNode(4, 0);

        createNeighbours(root, second, 6);
        createNeighbours(root, third, 6);
        createNeighbours(second, fourth, 4);
        createNeighbours(third, fourth, 4);

        // execute
        final Queue<TestNode> result = PATH_FINDER.findShortestPath(root, fourth, PathSearchStrategy.A_STAR);

        // evaluate
        assertEquals(3, result.size()); // 1 - 3 - 4
        assertEquals(1, (result.remove()).getId());
        assertEquals(3, (result.remove()).getId());
        assertEquals(4, (result.remove()).getId());
    }

    @Test
    void testDijkstra() {

        // setup
        final TestNode root = new TestNode(1);
        final TestNode second = new TestNode(2);
        final TestNode third = new TestNode(3);
        final TestNode fourth = new TestNode(4);
        final TestNode fifth = new TestNode(5);

        createNeighbours(root, second, 3);
        createNeighbours(root, third, 1);
        createNeighbours(third, fourth, 2);
        createNeighbours(second, fifth, 4);
        createNeighbours(fourth, fifth, 1);

        // execute
        final Queue<TestNode> result = PATH_FINDER.findShortestPath(root, fifth, PathSearchStrategy.DIJKSTRA);

        // evaluate
        assertEquals(4, result.size()); // 1 - 3 - 4 - 5
        assertEquals(1, (result.remove()).getId());
        assertEquals(3, (result.remove()).getId());
        assertEquals(4, (result.remove()).getId());
        assertEquals(5, (result.remove()).getId());
    }

    private static final class TestNode implements GraphNode<TestNode> {
        private final int id;
        private final int heuristic;

        private final Map<TestNode, Integer> distances = new HashMap<>();

        public TestNode(int id, int heuristic) {
            this.id = id;
            this.heuristic = heuristic;
        }

        public TestNode(int id) {
            this(id, 0);
        }

        public int getId() {
            return id;
        }

        @Override
        public int getHeuristicTo(TestNode node) {
            return heuristic;
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
