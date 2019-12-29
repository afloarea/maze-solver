package com.github.afloarea.maze_solver.algorithms.groups.impl;

import com.github.afloarea.maze_solver.algorithms.GraphNode;
import com.github.afloarea.maze_solver.algorithms.groups.AbstractNodeGroup;
import com.github.afloarea.maze_solver.algorithms.impl.Element;

import java.util.PriorityQueue;
import java.util.Queue;

public final class HeuristicNodeGroup<T extends GraphNode<T>> extends AbstractNodeGroup<T, Queue<Element<T>>> {
    private final T endNode;

    public HeuristicNodeGroup(T startNode, T endNode) {
        super(new PriorityQueue<>());

        this.endNode = endNode;                         // setting the end node must be done before calling add
        add(null, startNode, 0);
    }

    @Override
    public void add(Element<T> currentElement, T neighbour, int cost) {
        structure.add(new Element<>(neighbour, currentElement, cost, neighbour.getHeuristicTo(endNode)));
    }
}
