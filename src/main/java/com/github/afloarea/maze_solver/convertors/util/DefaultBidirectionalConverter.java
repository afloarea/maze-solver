package com.github.afloarea.maze_solver.convertors.util;

import com.github.afloarea.maze_solver.algorithms.model.Graph;
import com.github.afloarea.maze_solver.algorithms.model.GraphNode;
import com.github.afloarea.maze_solver.convertors.BidirectionalConverter;
import com.github.afloarea.maze_solver.convertors.util.unidirectional.MatrixToGraphConverter;
import com.github.afloarea.maze_solver.imaging.ImageContainer;
import com.github.afloarea.maze_solver.imaging.IntPoint;

import java.util.*;
import java.util.stream.Collectors;

public final class DefaultBidirectionalConverter implements BidirectionalConverter {

    private final ImageContainer imageContainer;
    private Map<IntPoint, GraphNode> coordinates;

    public DefaultBidirectionalConverter(ImageContainer imageContainer) {
        this.imageContainer = imageContainer;
    }

    @Override
    public Graph extractGraphFromImage() {
        final MatrixToGraphConverter converter = new MatrixToGraphConverter(imageContainer.getPixelMatrix(), 0);
        coordinates = converter.getCoordinates();
        return new Graph(converter.getFirstNode(), converter.getLastNode());
    }

    @Override
    public void updateImage(List<GraphNode> route) {
        final Map<GraphNode, IntPoint> positions = new HashMap<>();
        coordinates.forEach((pixel, graphNode) -> positions.put(graphNode, pixel));

        imageContainer.drawLines(route.stream().map(positions::get).collect(Collectors.toCollection(ArrayDeque::new)));
    }
}
