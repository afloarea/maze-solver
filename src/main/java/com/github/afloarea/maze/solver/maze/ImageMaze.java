package com.github.afloarea.maze.solver.maze;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Queue;

/**
 * A maze represented by a black and white image where black signifies a blocked tile
 * and white signifies a free tile.
 */
public final class ImageMaze implements Maze {
    private static final int FREE = Color.WHITE.getRGB();

    private final BufferedImage image;
    private final int[] rgbArray;

    public static ImageMaze fromFile(Path path) throws IOException {

        final BufferedImage readImage;
        try(final InputStream in = Files.newInputStream(path, StandardOpenOption.READ)) {
            readImage = ImageIO.read(in);
        }
        final var image = new BufferedImage(
                readImage.getWidth(), readImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

        final var graphics = image.createGraphics();
        graphics.drawImage(readImage, 0, 0, null);
        graphics.dispose();

        return new ImageMaze(image);
    }

    public void writeToFile(Path path) throws IOException {
        try(OutputStream out = Files.newOutputStream(path, StandardOpenOption.CREATE)) {
            ImageIO.write(this.image, "png", out);
        }
    }

    private ImageMaze(BufferedImage image) {
        this.image = image;
        rgbArray = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
    }

    public void drawRoute(Queue<? extends Positional> points) {
        final var graphics = image.createGraphics();
        graphics.setColor(Color.GREEN);

        Positional first;
        Positional second;
        for (first = points.remove(), second = points.remove();
             !points.isEmpty();
             first = second, second = points.remove()) {

            graphics.drawLine(first.getX(), first.getY(), second.getX(), second.getY());
        }
         graphics.dispose();

        image.setRGB(second.getX(), second.getY(), Color.GREEN.getRGB());
    }

    @Override
    public boolean isFreeAt(int row, int column) {
        if (row < 0 || row >= image.getHeight() || column < 0 || column >= image.getWidth()) {
            return false;
        }
        return rgbArray[row * image.getWidth() + column] == FREE;
    }

    @Override
    public boolean isBlockedAt(int row, int column) {
        return !isFreeAt(row, column);
    }

    @Override
    public int width() {
        return image.getWidth();
    }

    @Override
    public int height() {
        return image.getHeight();
    }
}
