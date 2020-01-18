package com.github.afloarea.maze_solver.convertors;

import com.github.afloarea.maze_solver.convertors.impl.PositionalGraphExtractor;
import com.github.afloarea.maze_solver.graph.PositionalGraph;
import com.github.afloarea.maze_solver.maze.Maze;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PositionalPositionalGraphExtractorTest {

    private static Stream<Arguments> buildTestSimpleGraphExtractionParams() {
        return Stream.of(
                Arguments.of(
                        new int[][] {
                        { 0, 0, 0, 1, 0 },
                        { 0, 0, 1, 1, 0 },
                        { 0, 0, 1, 0, 0 },
                        { 0, 1, 1, 0, 0 },
                        { 0, 1, 0, 0, 0 }

                        }, 0, 3, 4, 1),

                Arguments.of(
                        new int[][] {
                        { 1, 0, 0, 0, 0 },
                        { 1, 1, 1, 1, 1 },
                        { 1, 1, 1, 1, 1 },
                        { 1, 1, 1, 1, 1 },
                        { 0, 0, 0, 0, 1 }

                        }, 0, 0, 4, 4)
        );
    }

    @ParameterizedTest
    @MethodSource("buildTestSimpleGraphExtractionParams")
    void testSimpleGraphExtraction(int[][] sampleData, int startNodeY, int startNodeX, int endNodeY, int endNodeX) {
        // setup
        final Maze maze = new PositionalPositionalGraphExtractorTest.StubMaze(sampleData);

        final MazeToGraphConverter converter = new PositionalGraphExtractor();

        // execute
        final PositionalGraph graph = converter.convert(maze);

        // evaluate
        assertEquals(startNodeY, graph.getStartNode().getY());
        assertEquals(startNodeX, graph.getStartNode().getX());
        assertEquals(endNodeY, graph.getEndNode().getY());
        assertEquals(endNodeX, graph.getEndNode().getX());
    }

    private static final class StubMaze implements Maze {
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
