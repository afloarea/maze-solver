package com.github.afloarea.maze_solver.algorithms.impl;

import com.github.afloarea.maze_solver.algorithms.GraphNode;
import com.github.afloarea.maze_solver.algorithms.groups.NodeGroup;
import com.github.afloarea.maze_solver.algorithms.PathSearchStrategy;
import com.github.afloarea.maze_solver.algorithms.PathFinder;

import java.util.*;
import java.util.logging.Logger;

/**
 * Implementation for the Path Finder capable of using any of the available algorithms using specific node groups.
 */
public final class DefaultPathFinder implements PathFinder {
    private static final Logger LOGGER = Logger.getLogger(DefaultPathFinder.class.getName());

    @Override
    public <T extends GraphNode<T>> Queue<T> findShortestPath(T startNode, T endNode, PathSearchStrategy searchStrategy) {
        final Set<T> visited = new HashSet<>();
        final NodeGroup<T> group = NodeGroup.create(searchStrategy, startNode, endNode);

        int expandedNodes = 0;
        Element<T> lastElement = group.getNext();
        while (!group.isEmpty() && !endNode.equals(lastElement.getNode())) {
            final var element = group.remove();
            if (visited.contains(element.getNode())) continue;

            expandedNodes++;

            visited.add(element.getNode());
            element.getNode().getNeighbourDistances().forEach((neighbour, distance) -> {
                final int cost = element.getCost() + distance;
                group.add(element, neighbour, cost);
            });

            lastElement = element;
        }

        final int totalExpandedNodes = expandedNodes;
        LOGGER.info(() -> String.format("%s: number of expanded nodes: %d", searchStrategy, totalExpandedNodes));

        group.clear();
        visited.clear();
        return getResult(lastElement);
    }

    private static <T extends GraphNode<T>> Queue<T> getResult(Element<T> lastElement) {
        final var result = new ArrayDeque<T>();
        result.add(lastElement.getNode());
        var element = lastElement;

        while (element.getPreviousElement() != null) {
            result.addFirst(element.getPreviousElement().getNode());
            element = element.getPreviousElement();
        }
        return result;
    }
}
