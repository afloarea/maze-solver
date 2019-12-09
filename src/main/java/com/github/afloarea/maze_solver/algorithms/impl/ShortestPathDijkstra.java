package com.github.afloarea.maze_solver.algorithms.impl;

import com.github.afloarea.maze_solver.algorithms.ShortestPathAlgorithm;
import com.github.afloarea.maze_solver.algorithms.model.Graph;
import com.github.afloarea.maze_solver.algorithms.model.GraphNode;

import java.util.*;

public final class ShortestPathDijkstra implements ShortestPathAlgorithm {
    private final Set<GraphNode> visited = new HashSet<>();
    private final Queue<Element> queue = new PriorityQueue<>();

    @Override
    public List<GraphNode> calculateShortestPath(Graph graph) {
        final Element firstElement = new Element(graph.getStartNode(), null, 0);
        visited.add(firstElement.node);

        firstElement.node.getNeighbourDistances().forEach((neighbour, distance) ->
                queue.offer(new Element(neighbour, firstElement, distance)));

        Element lastElement = firstElement;
        while (!queue.isEmpty() && !graph.getEndNode().equals(lastElement.node)) {
            final Element element = queue.poll();
            if (visited.contains(element.node)) continue;

            visited.add(element.node);
            element.node.getNeighbourDistances().forEach((neighbour, distance) -> {
                final int cost = element.cost + distance;
                queue.offer(new Element(neighbour, element, cost));
            });

            lastElement = element;
        }

        queue.clear();
        visited.clear();
        return getResult(lastElement);
    }

    private static List<GraphNode> getResult(Element lastElement) {
        final LinkedList<GraphNode> result = new LinkedList<>();
        result.add(lastElement.node);
        Element element = lastElement;

        while (element.previousElement != null) {
            result.addFirst(element.previousElement.node);
            element = element.previousElement;
        }
        return result;
    }

    private static final class Element implements Comparable<Element> {
        private GraphNode node;
        private Element previousElement;
        private int cost;

        private Element(GraphNode node, Element previousElement, int cost) {
            this.node = node;
            this.previousElement = previousElement;
            this.cost = cost;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Element)) return false;
            final Element element = (Element) o;
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
