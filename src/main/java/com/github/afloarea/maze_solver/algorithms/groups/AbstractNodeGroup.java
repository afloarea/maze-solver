package com.github.afloarea.maze_solver.algorithms.groups;

import com.github.afloarea.maze_solver.algorithms.GraphNode;
import com.github.afloarea.maze_solver.algorithms.impl.Element;

import java.util.Queue;

public abstract class AbstractNodeGroup<T extends GraphNode<T>, S extends Queue<Element<T>>> implements NodeGroup<T> {

    protected final S structure;

    public AbstractNodeGroup(S structure) {
        this.structure = structure;
    }

    public AbstractNodeGroup(S structure, T startNode) {
        this.structure = structure;
        add(null, startNode, 0);
    }

    @Override
    public boolean isEmpty() {
        return structure.isEmpty();
    }

    @Override
    public Element<T> getNext() {
        return structure.element();
    }

    @Override
    public Element<T> remove() {
        return structure.remove();
    }

    @Override
    public void add(Element<T> currentElement, T neighbour, int cost) {
        structure.add(new Element<>(neighbour, currentElement, cost));
    }

    @Override
    public void clear() {
        structure.clear();
    }
}
