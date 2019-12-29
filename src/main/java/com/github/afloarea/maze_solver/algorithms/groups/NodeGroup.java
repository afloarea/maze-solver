package com.github.afloarea.maze_solver.algorithms.groups;

import com.github.afloarea.maze_solver.algorithms.GraphNode;
import com.github.afloarea.maze_solver.algorithms.PathSearchStrategy;
import com.github.afloarea.maze_solver.algorithms.impl.Element;
import com.github.afloarea.maze_solver.algorithms.groups.impl.HeuristicNodeGroup;
import com.github.afloarea.maze_solver.algorithms.groups.impl.QueuedNodeGroup;
import com.github.afloarea.maze_solver.algorithms.groups.impl.StackedNodeGroup;
import com.github.afloarea.maze_solver.algorithms.groups.impl.PrioritizedNodeGroup;

/**
 * Interface used to abstract the data structure that holds graph node from the various path finding algorithms.
 *
 * @param <T> the type of the graph node.
 */
public interface NodeGroup<T extends GraphNode<T>> {

    /**
     * Returns {@code true} if this group contains no elements.
     *
     * @return {@code true} if this group contains no elements
     */
    boolean isEmpty();

    /**
     * Retrieves, but does not remove the next element of this group.
     *
     * @return the next element
     * @throws java.util.NoSuchElementException if the group is empty
     */
    Element<T> getNext();

    /**
     * Retrieves and removes the next element of this group.
     *
     * @return the next element
     * @throws java.util.NoSuchElementException if the group is empty
     */
    Element<T> remove();

    /**
     * Create a new element using the given parameters and add it to the group.
     *
     * @param currentElement the current element (will become the previous element of the newly created element)
     * @param neighbour      the graph node with which to create the new element
     * @param cost           the cost of the newly created element
     */
    void add(Element<T> currentElement, T neighbour, int cost);

    /**
     * Empty the group.
     */
    void clear();

    /**
     * Factory method to create the appropriate node group for the given algorithm.
     *
     * @param algorithm the algorithm
     * @param startNode the starting node
     * @param endNode   the destination node
     * @param <N>       the type of the graph node
     * @return the node group
     */
    static <N extends GraphNode<N>> NodeGroup<N> create(PathSearchStrategy algorithm, N startNode, N endNode) {
        switch (algorithm) {
            case BFS:
                return new QueuedNodeGroup<>(startNode);
            case DFS:
                return new StackedNodeGroup<>(startNode);
            case A_STAR:
                return new HeuristicNodeGroup<>(startNode, endNode);
            case DIJKSTRA:
                return new PrioritizedNodeGroup<>(startNode);
        }

        throw new IllegalStateException("No suitable algorithm found");
    }

}
