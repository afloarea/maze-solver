package ro.ffa.convertors;

import ro.ffa.data.Graph;
import ro.ffa.data.GraphNode;

import java.util.List;

public interface BidirectionalConverter {

    Graph extractGraphFromImage();

    void updateImage(List<GraphNode> route);

}
