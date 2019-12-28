package com.github.afloarea.maze_solver.algorithms.groups;

import com.github.afloarea.maze_solver.algorithms.GraphNode;
import com.github.afloarea.maze_solver.algorithms.PathSearch;
import com.github.afloarea.maze_solver.algorithms.impl.Element;
import com.github.afloarea.maze_solver.algorithms.groups.impl.HeuristicNodeGroup;
import com.github.afloarea.maze_solver.algorithms.groups.impl.QueuedNodeGroup;
import com.github.afloarea.maze_solver.algorithms.groups.impl.StackedNodeGroup;
import com.github.afloarea.maze_solver.algorithms.groups.impl.PrioritizedNodeGroup;

public interface NodeGroup<T extends GraphNode<T>> {

    boolean isEmpty();

    Element<T> getNext();

    Element<T> remove();

    void add(Element<T> currentElement, T neighbour, int cost);

    void clear();

    static <N extends GraphNode<N>> NodeGroup<N> create(PathSearch algorithm, N startNode, N endNode) {
        switch (algorithm) {
            case BFS: return new QueuedNodeGroup<>(startNode);
            case DFS: return new StackedNodeGroup<>(startNode);
            case A_STAR: return new PrioritizedNodeGroup<>(startNode);
            case DIJKSTRA: return new HeuristicNodeGroup<>(startNode, endNode);
        }

        throw new IllegalStateException("No suitable algorithm found");
    }

}
