package com.github.afloarea.maze_solver.convertors;

import com.github.afloarea.maze_solver.graph.PositionalGraph;
import com.github.afloarea.maze_solver.convertors.impl.PositionalGraphExtractor;
import com.github.afloarea.maze_solver.maze.Maze;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PositionalPositionalGraphExtractorTest {

    @Test
    void testSimpleGraphExtraction() {
        // setup
        final int[][] sampleData = {
                { 0, 0, 0, 1, 0 },
                { 0, 0, 1, 1, 0 },
                { 0, 0, 1, 0, 0 },
                { 0, 1, 1, 0, 0 },
                { 0, 1, 0, 0, 0 }
        };
        final Maze maze = new PositionalPositionalGraphExtractorTest.StubMaze(sampleData);

        final MazeToGraphConverter converter = new PositionalGraphExtractor();

        // execute
        final PositionalGraph graph = converter.convert(maze);

        // evaluate
        Assertions.assertEquals(0, graph.getStartNode().getY());
        Assertions.assertEquals(3, graph.getStartNode().getX());
        Assertions.assertEquals(4, graph.getEndNode().getY());
        Assertions.assertEquals(1, graph.getEndNode().getX());
    }

    private static final class StubMaze implements Maze {
        private static final int FREE = 1;
        private static final int BLOCKED = 0;

        private final int[][] data;

        public StubMaze(int[][] data) {
            this.data = data;
        }

        @Override
        public boolean isFreeAt(int row, int column) {
            return !isBlockedAt(row, column);
        }

        @Override
        public boolean isBlockedAt(int row, int column) {
            if (row < 0 || row >= data.length || column < 0 || column >= data[0].length) {
                return true;
            }
            return data[row][column] == BLOCKED;
        }

        @Override
        public int width() {
            return data[0].length;
        }

        @Override
        public int height() {
            return data.length;
        }
    }

}
