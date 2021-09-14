package com.github.afloarea.maze.solver.maze;

/**
 * Interface for representing a maze.
 */
public interface Maze {

    /**
     * Check if the maze is free at the specified position.
     * If the position is not within bound then it is considered blocked.
     * @param row the row
     * @param column the column
     * @return whether the position is free or not.
     */
    boolean isFreeAt(int row, int column);

    /**
     * Check if the maze is blocked / impassable at the specified position.
     * If the position is not within bound then it is considered blocked.
     * @param row the row
     * @param column the column
     * @return whether the position is blocked or not.
     */
    default boolean isBlockedAt(int row, int column) {
        return !isFreeAt(row, column);
    }

    int width();

    int height();

}
