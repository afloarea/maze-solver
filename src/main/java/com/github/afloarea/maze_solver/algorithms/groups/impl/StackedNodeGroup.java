package com.github.afloarea.maze_solver.algorithms.groups.impl;

import com.github.afloarea.maze_solver.algorithms.GraphNode;
import com.github.afloarea.maze_solver.algorithms.groups.AbstractNodeGroup;
import com.github.afloarea.maze_solver.algorithms.impl.Element;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Node group for the depth first search algorithm.
 * Uses a stack. Does not take into account the cost.
 *
 * @param <T> the type of the graph node.
 */
public final class StackedNodeGroup<T extends GraphNode<T>> extends AbstractNodeGroup<T, Deque<Element<T>>> {

    public StackedNodeGroup(T startNode) {
        super(new ArrayDeque<>(), startNode);
    }

    @Override
    public Element<T> getNext() {
        return structure.getFirst();
    }

    @Override
    public Element<T> remove() {
        return structure.pop();
    }

    @Override
    public void add(Element<T> currentElement, T neighbour, int cost) {
        structure.push(new Element<>(neighbour, currentElement, cost));
    }
}
