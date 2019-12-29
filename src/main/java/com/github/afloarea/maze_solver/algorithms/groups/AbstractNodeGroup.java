package com.github.afloarea.maze_solver.algorithms.groups;

import com.github.afloarea.maze_solver.algorithms.GraphNode;
import com.github.afloarea.maze_solver.algorithms.impl.Element;

import java.util.Queue;

/**
 * Base class for all node groups.
 *
 * @param <T> the type of the node.
 * @param <S> the type of the data structure.
 */
public abstract class AbstractNodeGroup<T extends GraphNode<T>, S extends Queue<Element<T>>> implements NodeGroup<T> {

    protected final S structure;

    /**
     * Initialize the node group with the underlying collection.
     *
     * @param structure the data structure wrap.
     */
    public AbstractNodeGroup(S structure) {
        this.structure = structure;
    }

    /**
     * Initialize the node group and add the starting element.
     *
     * @param structure the data structure to wrap.
     * @param startNode the starting element.
     */
    public AbstractNodeGroup(S structure, T startNode) {
        this(structure);
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
