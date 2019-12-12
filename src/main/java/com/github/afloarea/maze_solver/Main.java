package com.github.afloarea.maze_solver;


import com.github.afloarea.maze_solver.algorithms.ShortestPathAlgorithm;
import com.github.afloarea.maze_solver.algorithms.impl.ShortestPathDijkstra;
import com.github.afloarea.maze_solver.convertors.MazeToGraphConverter;
import com.github.afloarea.maze_solver.convertors.impl.PositionalGraphExtractor;
import com.github.afloarea.maze_solver.graph.PositionalGraph;
import com.github.afloarea.maze_solver.graph.PositionalGraphNode;
import com.github.afloarea.maze_solver.maze.ImageMaze;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Queue;
import java.util.function.Supplier;
import java.util.logging.Logger;

/**
 * Main class.
 */
public class Main {
    static {
        System.setProperty("java.util.logging.config.file",
                Main.class.getResource("/logging.properties").getFile());
    }

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private static final MazeToGraphConverter CONVERTER = new PositionalGraphExtractor();
    private static final ShortestPathAlgorithm PATH_ALGORITHM = new ShortestPathDijkstra();

    public static void main(String[] args) {
        final Path filePath = getFilePath();

        if (filePath == null) {
            LOGGER.info("No file selected. Exiting...");
            return;
        }

        final ImageMaze maze = readMaze(filePath);
        if (maze == null) {
            return;
        }

        LOGGER.info("Converting image to graph...");
        final PositionalGraph graph = timeActionAndGetResult(() -> CONVERTER.convert(maze),
                "Conversion done in %d seconds");


        LOGGER.info("Calculating shortest path...");
        final Queue<PositionalGraphNode> route = timeActionAndGetResult(
                () -> PATH_ALGORITHM.calculateShortestPath(graph.getStartNode(), graph.getEndNode()),
                "Search finished in %d seconds");


        LOGGER.info("Drawing route on maze...");
        updateMaze(maze, route);


        LOGGER.info("Writing to file...");
        try {
            maze.writeToFile(filePath.resolveSibling("solved_" + filePath.getFileName().toString()));
        } catch (IOException e) {
            LOGGER.severe(() -> "Failed to write maze to file because: " + e.getMessage());
        }


        JOptionPane.showMessageDialog(null, "Processing file " + filePath.getFileName() + " done!");
    }

    private static <T> T timeActionAndGetResult(Supplier<? extends T> supplier, String format){
        final long ref = System.currentTimeMillis();

        final T result = supplier.get();

        LOGGER.info(() -> String.format(format, (System.currentTimeMillis() - ref) / 1000));
        return result;
    }

    private static Path getFilePath() {
        final JFileChooser chooser = new JFileChooser(Paths.get(".").toAbsolutePath().toFile());
        chooser.showOpenDialog(null);
        final File file = chooser.getSelectedFile();

        if (file == null) {
            return null;
        }

        return file.toPath();
    }

    private static ImageMaze readMaze(Path path) {
        final ImageMaze maze;
        try {
            maze = ImageMaze.fromFile(path);
        } catch (IOException ex) {
            LOGGER.severe(() -> String.format("Unable to read file %s because of %s", path.getFileName(), ex.getMessage()));
            return null;
        }
        return maze;
    }

    private static void updateMaze(ImageMaze maze, Queue<PositionalGraphNode> route) {
        final long ref = System.currentTimeMillis();
        maze.drawRoute(route);

        LOGGER.info(() -> String.format("Drawing finished in %d seconds", (System.currentTimeMillis() - ref) / 1000));
    }

}
