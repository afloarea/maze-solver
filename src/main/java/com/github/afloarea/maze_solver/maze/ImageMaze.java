package com.github.afloarea.maze_solver.maze;

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
    private static final int BLOCKED = Color.BLACK.getRGB();
    private static final int FREE = Color.WHITE.getRGB();

    private final BufferedImage image;
    private final int[] rgbArray;

    public static ImageMaze fromFile(Path path) throws IOException {

        final BufferedImage readImage;
        try(final InputStream in = Files.newInputStream(path, StandardOpenOption.READ)) {
            readImage = ImageIO.read(in);
        }
        final BufferedImage image = new BufferedImage(
                readImage.getWidth(), readImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        image.getGraphics().drawImage(readImage, 0, 0, null);

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
        final Graphics graphics = image.createGraphics();
        graphics.setColor(Color.GREEN);

        for (Positional first = points.remove(), second = points.remove();
             points.size() != 1;
             first = second, second = points.remove()) {

            graphics.drawLine(first.getX(), first.getY(), second.getX(), second.getY());
        }
         graphics.dispose();

        final Positional last = points.remove();
        image.setRGB(last.getX(), last.getY(), Color.GREEN.getRGB());
    }

    @Override
    public boolean isFreeAt(int row, int column) {
        return rgbArray[row * image.getWidth() + column] == FREE;
    }

    @Override
    public boolean isBlockedAt(int row, int column) {
        return rgbArray[row * image.getWidth() + column] == BLOCKED;
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
