package com.github.afloarea.maze.solver.algorithms.impl;

import com.github.afloarea.maze.solver.algorithms.GraphNode;
import com.github.afloarea.maze.solver.algorithms.groups.NodeGroup;
import com.github.afloarea.maze.solver.algorithms.PathSearchStrategy;
import com.github.afloarea.maze.solver.algorithms.PathFinder;

import java.util.*;
import java.util.logging.Logger;

/**
 * Implementation for the Path Finder capable of using any of the available algorithms using specific node groups.
 */
public final class DefaultPathFinder implements PathFinder {
    private static final Logger LOGGER = Logger.getLogger(DefaultPathFinder.class.getName());

    private final PathSearchStrategy searchStrategy;

    public DefaultPathFinder(PathSearchStrategy searchStrategy) {
        this.searchStrategy = searchStrategy;
    }

    @Override
    public <T extends GraphNode<T>> Queue<T> findShortestPath(T startNode, T endNode) {
        final Set<T> visited = new HashSet<>();
        final NodeGroup<T> group = NodeGroup.create(searchStrategy, startNode, endNode);

        int expandedNodes = 0;
        Element<T> lastElement = group.getNext();
        while (!group.isEmpty() && !endNode.equals(lastElement.node())) {
            final var element = group.remove();
            if (visited.contains(element.node())) continue;

            expandedNodes++;

            visited.add(element.node());
            element.node().getNeighbourDistances().forEach((neighbour, distance) -> {
                final int cost = element.cost() + distance;
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
        result.add(lastElement.node());
        var element = lastElement;

        while (element.previousElement() != null) {
            result.addFirst(element.previousElement().node());
            element = element.previousElement();
        }
        return result;
    }
}
