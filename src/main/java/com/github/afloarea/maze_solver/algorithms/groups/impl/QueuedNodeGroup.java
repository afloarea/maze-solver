package com.github.afloarea.maze_solver.algorithms.groups.impl;

import com.github.afloarea.maze_solver.algorithms.GraphNode;
import com.github.afloarea.maze_solver.algorithms.groups.AbstractNodeGroup;
import com.github.afloarea.maze_solver.algorithms.impl.Element;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Node group for the Breadth first search algorithm.
 * Uses a FIFO queue.
 *
 * @param <T> the type of the graph node.
 */
public final class QueuedNodeGroup<T extends GraphNode<T>> extends AbstractNodeGroup<T, Queue<Element<T>>> {

    public QueuedNodeGroup(T startNode) {
        super(new ArrayDeque<>(), startNode);
    }

}
