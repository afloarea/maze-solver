package ro.ffa.algorithms.impl;

import ro.ffa.algorithms.ShortestPathAlgorithm;
import ro.ffa.data.Graph;
import ro.ffa.data.GraphNode;

import java.util.*;

public class Dijkstra implements ShortestPathAlgorithm {
    private final Set<GraphNode> visited = new HashSet<>();
    private final Queue<Element> queue = new PriorityQueue<>();

    private static class Element implements Comparable<Element>{
        private GraphNode node;
        private Element previousElement;
        private int cost;

        private Element(GraphNode node, Element previousElement, int cost) {
            this.node = node;
            this.previousElement = previousElement;
            this.cost = cost;
        }

        @Override
        public int compareTo(Element that) {
            return this.cost - that.cost;
        }
    }

    @Override
    public List<GraphNode> calculateShortestPath(Graph graph) {
        final Element firstElement = new Element(graph.getStartNode(), null, 0);
        visited.add(firstElement.node);

        for (int i = 0; i < firstElement.node.neighboursAmount(); i++) {
            queue.offer(new Element(firstElement.node.getNeighbour(i),
                                    firstElement,
                                    firstElement.node.getNeighbourDistance(i)));
        }

        Element lastElement = null;
        while (!queue.isEmpty()) {
            final Element element = queue.poll();
            if (visited.contains(element.node)) continue;

            visited.add(element.node);
            for (int i = 0; i < element.node.neighboursAmount(); i++) {
                final int cost = element.cost + element.node.getNeighbourDistance(i);
                queue.offer(new Element(element.node.getNeighbour(i), element, cost));
            }

            if (element.node.getId() == graph.getEndNode().getId()) {
                lastElement = element;
                queue.clear();
                visited.clear();
            }
        }

        return getResult(lastElement);
    }

    private static List<GraphNode> getResult(Element lastElement) {
        final LinkedList<GraphNode> result = new LinkedList<>();
        if (lastElement != null) {
            result.add(lastElement.node);
            Element element = lastElement;
            while (element.previousElement != null) {
                result.addFirst(element.previousElement.node);
                element = element.previousElement;
            }
        }
        return result;
    }
}
