package ro.ffa.algorithms;

import ro.ffa.data.Graph;
import ro.ffa.data.GraphNode;

import java.util.List;

public interface ShortestPathAlgorithm {

    List<GraphNode> calculateShortestPath(Graph graph);

}
