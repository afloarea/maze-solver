package com.github.afloarea.maze_solver.algorithms.groups.impl;

import com.github.afloarea.maze_solver.algorithms.GraphNode;
import com.github.afloarea.maze_solver.algorithms.groups.NodeGroup;
import com.github.afloarea.maze_solver.algorithms.impl.Element;

import java.util.ArrayDeque;
import java.util.Queue;

public final class QueuedNodeGroup<T extends GraphNode<T>> implements NodeGroup<T> {
    private final Queue<Element<T>> queue;

    public QueuedNodeGroup(T startNode) {
        queue = new ArrayDeque<>();
        queue.add(new Element<>(startNode, null, 0));
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public Element<T> getNext() {
        return queue.element();
    }

    @Override
    public Element<T> remove() {
        return queue.remove();
    }

    @Override
    public void add(Element<T> currentElement, T neighbour, int cost) {
        queue.add(new Element<>(neighbour, currentElement, cost));
    }

    @Override
    public void clear() {
        queue.clear();
    }
}
