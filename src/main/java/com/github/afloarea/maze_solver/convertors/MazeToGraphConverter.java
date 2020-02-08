package com.github.afloarea.maze_solver.convertors;

import com.github.afloarea.maze_solver.graph.PositionalGraph;
import com.github.afloarea.maze_solver.maze.Maze;

/**
 * General converter that can convert a maze to a positional graph.
 */
public interface MazeToGraphConverter {

    PositionalGraph convert(Maze maze);

}
