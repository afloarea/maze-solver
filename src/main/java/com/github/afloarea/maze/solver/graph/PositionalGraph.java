package com.github.afloarea.maze.solver.graph;

/**
 * A graph composed of {@code PositionalGraphNode}s.
 */
public record PositionalGraph(PositionalGraphNode startNode, PositionalGraphNode endNode) {}
