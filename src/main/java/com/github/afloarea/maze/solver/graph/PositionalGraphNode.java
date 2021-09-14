package com.github.afloarea.maze.solver.graph;

import com.github.afloarea.maze.solver.algorithms.GraphNode;
import com.github.afloarea.maze.solver.maze.Positional;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * A graph node aware of its spatial position.
 */
public final class PositionalGraphNode implements Positional, GraphNode<PositionalGraphNode> {

    private final int row;
    private final int column;

    private final Map<PositionalGraphNode, Integer> neighbourDistances = new HashMap<>();

    public PositionalGraphNode(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public int getX() {
        return column;
    }

    @Override
    public int getY() {
        return row;
    }

    @Override
    public int getHeuristicTo(PositionalGraphNode that) {
        // Manhattan distance is used because the maze cannot be traversed diagonally
        return Math.abs(this.getX() - that.getX()) + Math.abs(this.getY() - that.getY());
    }

    @Override
    public void addNeighbour(PositionalGraphNode neighbour, int distance) {
        neighbourDistances.put(neighbour, distance);
    }

    @Override
    public Map<PositionalGraphNode, Integer> getNeighbourDistances() {
        return neighbourDistances;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PositionalGraphNode)) return false;
        PositionalGraphNode graphNode = (PositionalGraphNode) o;
        return row == graphNode.row &&
                column == graphNode.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PositionalGraphNode.class.getSimpleName() + "[", "]")
                .add("row=" + row)
                .add("column=" + column)
                .add("neighbourDistances=" + neighbourDistances)
                .toString();
    }
}
