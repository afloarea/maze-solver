package com.github.afloarea.maze_solver.algorithms.impl;

import com.github.afloarea.maze_solver.algorithms.ShortestPathAlgorithm;
import com.github.afloarea.maze_solver.algorithms.GraphNode;

import java.util.*;

/**
 * Dijkstra implementation of the shortest path algorithm.
 */
public final class ShortestPathDijkstra implements ShortestPathAlgorithm {

    @Override
    public <T extends GraphNode<T>> Queue<T> calculateShortestPath(T startNode, T endNode) {
        final Set<T> visited = new HashSet<>();
        final Queue<Element<T>> queue = new PriorityQueue<>();

        final Element<T> firstElement = new Element<>(startNode, null, 0);
        visited.add(firstElement.node);

        firstElement.node.getNeighbourDistances().forEach((neighbour, distance) ->
                queue.offer(new Element<>(neighbour, firstElement, distance)));

        Element<T> lastElement = firstElement;
        while (!queue.isEmpty() && !endNode.equals(lastElement.node)) {
            final Element<T> element = queue.poll();
            if (visited.contains(element.node)) continue;

            visited.add(element.node);
            element.node.getNeighbourDistances().forEach((neighbour, distance) -> {
                final int cost = element.cost + distance;
                queue.offer(new Element<>(neighbour, element, cost));
            });

            lastElement = element;
        }

        queue.clear();
        visited.clear();
        return getResult(lastElement);
    }

    private static <T extends GraphNode<T>> Queue<T> getResult(Element<T> lastElement) {
        final ArrayDeque<T> result = new ArrayDeque<>();
        result.add(lastElement.node);
        Element<T> element = lastElement;

        while (element.previousElement != null) {
            result.addFirst(element.previousElement.node);
            element = element.previousElement;
        }
        return result;
    }

    private static final class Element<T extends GraphNode<T>> implements Comparable<Element<T>> {
        private T node;
        private Element<T> previousElement;
        private int cost;

        private Element(T node, Element<T> previousElement, int cost) {
            this.node = node;
            this.previousElement = previousElement;
            this.cost = cost;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Element)) return false;
            final Element<?> element = (Element<?>) o;
            return cost == element.cost;
        }

        @Override
        public int hashCode() {
            return Objects.hash(cost);
        }

        @Override
        public int compareTo(Element that) {
            return this.cost - that.cost;
        }
    }
}
