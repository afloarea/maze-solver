package com.github.afloarea.maze.solver.algorithms.impl;

import com.github.afloarea.maze.solver.algorithms.GraphNode;

/**
 * Data structure used for wrapping a graph node.
 * Used by the path finding algorithms.
 *
 * @param <T> the type of the graph node
 */
public record Element<T extends GraphNode<T>>(
        T node,
        Element<T> previousElement,
        int cost,
        int heuristic
) implements Comparable<Element<T>> {

    public Element(T node, Element<T> previousElement, int cost) {
        this(node, previousElement, cost, 0);
    }

    @Override
    public int compareTo(Element<T> that) {
        return (this.cost + this.heuristic) - (that.cost + that.heuristic);
    }
}
