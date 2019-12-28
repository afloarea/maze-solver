package com.github.afloarea.maze_solver.algorithms.groups.impl;

import com.github.afloarea.maze_solver.algorithms.GraphNode;
import com.github.afloarea.maze_solver.algorithms.groups.NodeGroup;
import com.github.afloarea.maze_solver.algorithms.impl.Element;

import java.util.ArrayDeque;
import java.util.Deque;

public final class StackedNodeGroup<T extends GraphNode<T>> implements NodeGroup<T> {
    private final Deque<Element<T>> stack;

    public StackedNodeGroup(T startNode) {
        stack = new ArrayDeque<>();
        stack.push(new Element<>(startNode, null, 0));
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public Element<T> getNext() {
        return stack.getFirst();
    }

    @Override
    public Element<T> remove() {
        return stack.pop();
    }

    @Override
    public void add(Element<T> currentElement, T neighbour, int cost) {
        stack.push(new Element<>(neighbour, currentElement, cost));
    }

    @Override
    public void clear() {
        stack.clear();
    }
}
