package com.github.afloarea.maze_solver.imaging;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Deque;

public class ImageContainer {

    private final BufferedImage image;

    public static ImageContainer fromFile(Path path) throws IOException {

        final BufferedImage readImage;
        try(final InputStream in = Files.newInputStream(path, StandardOpenOption.READ)) {
            readImage = ImageIO.read(in);
        }
        final BufferedImage image = new BufferedImage(
                readImage.getWidth(), readImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        image.getGraphics().drawImage(readImage, 0, 0, null);

        return new ImageContainer(image);
    }

    private ImageContainer(BufferedImage image) {
        this.image = image;
    }

    public float[][] getPixelMatrix() {
        final float[][] matrix = new float[image.getHeight()][image.getWidth()];

        for (int row = 0; row < image.getHeight(); row++) {
            for (int column = 0; column < image.getWidth(); column++) {
                final int pixel = image.getRGB(column, row) & 0xFF;
                matrix[row][column] = pixel == 0 ? 0 : 1;
            }
        }

        return matrix;
    }

    public void drawLines(Deque<IntPoint> points) {
        final IntPoint last = points.getLast();
        image.setRGB(last.getX(), last.getY(), Color.GREEN.getRGB());

        final Graphics graphics = image.getGraphics();
        graphics.setColor(Color.GREEN);

        for (IntPoint first = points.remove(), second = points.remove();
             !points.isEmpty();
             first = second, second = points.remove()) {

            graphics.drawLine(first.getX(), first.getY(), second.getX(), second.getY());
        }
    }

    public void writeToFile(Path path) throws IOException {
        try(OutputStream out = Files.newOutputStream(path, StandardOpenOption.CREATE)) {
            ImageIO.write(this.image, "png", out);
        }
    }

}
