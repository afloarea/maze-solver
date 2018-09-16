package ro.ffa.convertors.util;

import org.junit.Assert;
import org.mockito.Mockito;
import ro.ffa.data.Graph;
import ro.ffa.imaging.ImageContainer;
import org.junit.Test;

public class DefaultBidirectionalConverterTest {

    private static final float[][] sampleData =
            {
                    {0f, 0f, 0f, 1f, 0f},
                    {0f, 0f, 1f, 1f, 0f},
                    {0f, 0f, 1f, 0f, 0f},
                    {0f, 1f, 1f, 0f, 0f},
                    {0f, 1f, 0f, 0f, 0f}
            };

    @Test
    public void testSimpleGraphExtraction() {
        // setup
        final ImageContainer container = Mockito.mock(ImageContainer.class);
        Mockito.when(container.getPixelMatrix()).thenReturn(sampleData);
        final DefaultBidirectionalConverter converter = new DefaultBidirectionalConverter(container);

        // execute
        final Graph graph = converter.extractGraphFromImage();

        // evaluate
        Assert.assertEquals(0, graph.getStartNode().getId());
        Assert.assertEquals(5, graph.getEndNode().getId());
    }

}
