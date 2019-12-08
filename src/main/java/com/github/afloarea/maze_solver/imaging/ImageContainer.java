package com.github.afloarea.maze_solver.imaging;

import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.math.geometry.point.Point2d;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.IntStream;

public final class ImageContainer {
    private static final Float[] color = { 0f, 1f, 0f };

    private final FImage image;
    private MBFImage outputImage = null;

    public static ImageContainer fromFile(Path path) {
        final FImage image;
        try {
            image = ImageUtilities.readF(path.toFile());
            return new ImageContainer(image);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private ImageContainer(FImage image) {
        this.image = image;
    }

    public float[][] getPixelMatrix() {return image.pixels;}

    public void drawLines(List<Point2d> points) {
        outputImage = image.toRGB();
        IntStream.range(1, points.size())
                .forEach(index -> {
                    final Point2d firstPoint = points.get(index - 1);
                    final Point2d secondPoint = points.get(index);
                    outputImage.drawLine(firstPoint, secondPoint, color);
                });
        final Point2d lastPoint = points.get(points.size() - 1);
        outputImage.setPixel((int)lastPoint.getX(), (int)lastPoint.getY(), color);
    }

    public void writeToFile(Path path) throws IOException{
        if (outputImage != null) ImageUtilities.write(outputImage, path.toFile());
        else ImageUtilities.write(image, path.toFile());
    }
}
