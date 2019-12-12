package com.github.afloarea.maze_solver.maze;

/**
 * Interface for representing a maze.
 */
public interface Maze {

    boolean isFreeAt(int row, int column);

    boolean isBlockedAt(int row, int column);

    int width();

    int height();

}
