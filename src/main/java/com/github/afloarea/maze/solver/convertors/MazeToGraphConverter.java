package com.github.afloarea.maze.solver.convertors;

import com.github.afloarea.maze.solver.graph.PositionalGraph;
import com.github.afloarea.maze.solver.maze.Maze;

/**
 * General converter that can convert a maze to a positional graph.
 */
public interface MazeToGraphConverter {

    PositionalGraph convert(Maze maze);

}
