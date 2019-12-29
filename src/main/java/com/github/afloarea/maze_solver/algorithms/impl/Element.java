package com.github.afloarea.maze_solver.algorithms.impl;

import com.github.afloarea.maze_solver.algorithms.GraphNode;

import java.util.Objects;

/**
 * Class used for wrapping a graph node.
 * Used by the path finding algorithms.
 *
 * @param <T> the type of the graph node
 */
public final class Element<T extends GraphNode<T>> implements Comparable<Element<T>> {

    private T node;
    private Element<T> previousElement;
    private int cost;
    private int heuristic;

    public Element(T node, Element<T> previousElement, int cost) {
        this.node = node;
        this.previousElement = previousElement;
        this.cost = cost;
        this.heuristic = 0;
    }

    public Element(T node, Element<T> previousElement, int cost, int heuristic) {
        this.node = node;
        this.previousElement = previousElement;
        this.cost = cost;
        this.heuristic = heuristic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Element)) return false;
        final Element<?> element = (Element<?>) o;
        return cost == element.cost && heuristic == element.heuristic;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cost, heuristic);
    }

    @Override
    public int compareTo(Element<T> that) {
        return (this.cost + this.heuristic) - (that.cost + that.heuristic);
    }

    public T getNode() {
        return node;
    }

    public Element<T> getPreviousElement() {
        return previousElement;
    }

    public int getCost() {
        return cost;
    }
}
