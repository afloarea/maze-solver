package com.github.afloarea.maze_solver.graph;

public final class PositionalGraph {

    private PositionalGraphNode startNode;
    private PositionalGraphNode endNode;

    public PositionalGraph(PositionalGraphNode startNode, PositionalGraphNode endNode) {
        this.startNode = startNode;
        this.endNode = endNode;
    }

    public PositionalGraphNode getStartNode() {
        return startNode;
    }

    public PositionalGraphNode getEndNode() {
        return endNode;
    }
}
