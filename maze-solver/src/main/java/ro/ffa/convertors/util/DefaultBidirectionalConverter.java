package ro.ffa.convertors.util;

import org.openimaj.image.pixel.Pixel;
import ro.ffa.convertors.BidirectionalConverter;
import ro.ffa.convertors.util.unidirectional.MatrixToGraphConverter;
import ro.ffa.data.Graph;
import ro.ffa.data.GraphNode;
import ro.ffa.imaging.ImageContainer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DefaultBidirectionalConverter implements BidirectionalConverter {

    private final ImageContainer imageContainer;
    private Map<Pixel, GraphNode> coordinates;

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
        final Map<GraphNode, Pixel> positions = new HashMap<>();
        coordinates.forEach((pixel, graphNode) -> positions.put(graphNode, pixel));

        imageContainer.drawLines(route.stream().map(positions::get).collect(Collectors.toList()));
    }
}
