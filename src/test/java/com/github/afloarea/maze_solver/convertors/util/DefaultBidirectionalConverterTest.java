package com.github.afloarea.maze_solver.convertors.util;

import com.github.afloarea.maze_solver.data.Graph;
import com.github.afloarea.maze_solver.imaging.ImageContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DefaultBidirectionalConverterTest {

    private static final float[][] sampleData =
            {
                    {0f, 0f, 0f, 1f, 0f},
                    {0f, 0f, 1f, 1f, 0f},
                    {0f, 0f, 1f, 0f, 0f},
                    {0f, 1f, 1f, 0f, 0f},
                    {0f, 1f, 0f, 0f, 0f}
            };

    @Test
    void testSimpleGraphExtraction() {
        // setup
        final ImageContainer container = Mockito.mock(ImageContainer.class);
        Mockito.when(container.getPixelMatrix()).thenReturn(sampleData);
        final DefaultBidirectionalConverter converter = new DefaultBidirectionalConverter(container);

        // execute
        final Graph graph = converter.extractGraphFromImage();

        // evaluate
        Assertions.assertEquals(0, graph.getStartNode().getId());
        Assertions.assertEquals(5, graph.getEndNode().getId());
    }

}
