package com.github.afloarea.maze_solver.algorithms.groups.impl;

import com.github.afloarea.maze_solver.algorithms.GraphNode;
import com.github.afloarea.maze_solver.algorithms.groups.AbstractNodeGroup;
import com.github.afloarea.maze_solver.algorithms.impl.Element;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Node group for Dijkstra shortest path.
 * Uses a priority queue to sort by cost.
 *
 * @param <T> the type of the graph node.
 */
public final class PrioritizedNodeGroup<T extends GraphNode<T>> extends AbstractNodeGroup<T, Queue<Element<T>>> {

    public PrioritizedNodeGroup(T startNode) {
        super(new PriorityQueue<>(), startNode);
    }
}
