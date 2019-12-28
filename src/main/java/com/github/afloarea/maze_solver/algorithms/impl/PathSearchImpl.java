package com.github.afloarea.maze_solver.algorithms.impl;

import com.github.afloarea.maze_solver.algorithms.GraphNode;
import com.github.afloarea.maze_solver.algorithms.groups.NodeGroup;
import com.github.afloarea.maze_solver.algorithms.PathSearch;
import com.github.afloarea.maze_solver.algorithms.PathSearchAlgorithm;

import java.util.*;
import java.util.logging.Logger;

public final class PathSearchImpl implements PathSearchAlgorithm {
    private static final Logger LOGGER = Logger.getLogger(PathSearchImpl.class.getName());

    private PathSearch algorithm;

    public PathSearchImpl(PathSearch algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public <T extends GraphNode<T>> Queue<T> calculateShortestPath(T startNode, T endNode) {
        final Set<T> visited = new HashSet<>();
        final NodeGroup<T> group = NodeGroup.create(algorithm, startNode, endNode);

        int expandedNodes = 0;
        Element<T> lastElement = group.getNext();
        while (!group.isEmpty() && !endNode.equals(lastElement.getNode())) {
            final Element<T> element = group.remove();
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
        LOGGER.info(() -> String.format("number of expanded nodes: %d", totalExpandedNodes));

        group.clear();
        visited.clear();
        return getResult(lastElement);
    }

    private static <T extends GraphNode<T>> Queue<T> getResult(Element<T> lastElement) {
        final ArrayDeque<T> result = new ArrayDeque<>();
        result.add(lastElement.getNode());
        Element<T> element = lastElement;

        while (element.getPreviousElement() != null) {
            result.addFirst(element.getPreviousElement().getNode());
            element = element.getPreviousElement();
        }
        return result;
    }

    public PathSearch getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(PathSearch algorithm) {
        this.algorithm = algorithm;
    }
}
