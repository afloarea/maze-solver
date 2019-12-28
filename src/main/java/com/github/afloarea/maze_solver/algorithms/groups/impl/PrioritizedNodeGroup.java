package com.github.afloarea.maze_solver.algorithms.groups.impl;

import com.github.afloarea.maze_solver.algorithms.GraphNode;
import com.github.afloarea.maze_solver.algorithms.groups.NodeGroup;
import com.github.afloarea.maze_solver.algorithms.impl.Element;

import java.util.PriorityQueue;
import java.util.Queue;

public final class PrioritizedNodeGroup<T extends GraphNode<T>> implements NodeGroup<T> {
    private final Queue<Element<T>> queue;

    public PrioritizedNodeGroup(T startNode) {
        queue = new PriorityQueue<>();
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
