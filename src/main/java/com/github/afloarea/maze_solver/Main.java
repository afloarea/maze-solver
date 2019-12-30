package com.github.afloarea.maze_solver;


import com.github.afloarea.maze_solver.algorithms.PathFinder;
import com.github.afloarea.maze_solver.algorithms.PathSearchStrategy;
import com.github.afloarea.maze_solver.algorithms.impl.DefaultPathFinder;
import com.github.afloarea.maze_solver.convertors.MazeToGraphConverter;
import com.github.afloarea.maze_solver.convertors.impl.PositionalGraphExtractor;
import com.github.afloarea.maze_solver.graph.PositionalGraph;
import com.github.afloarea.maze_solver.graph.PositionalGraphNode;
import com.github.afloarea.maze_solver.maze.ImageMaze;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Supplier;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Main class.
 */
public final class Main {
    static {
        configureLogging();
    }

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private static final MazeToGraphConverter CONVERTER = new PositionalGraphExtractor();
    private static final PathFinder PATH_FINDER = new DefaultPathFinder();

    private static final Map<String, PathSearchStrategy> STRATEGY_MAP;
    static {
        final Map<String, PathSearchStrategy> pathStrategyByName = new HashMap<>();
        pathStrategyByName.put("dijkstra", PathSearchStrategy.DIJKSTRA);
        pathStrategyByName.put("a-star", PathSearchStrategy.A_STAR);
        pathStrategyByName.put("bfs", PathSearchStrategy.BFS);
        pathStrategyByName.put("dfs", PathSearchStrategy.DFS);

        STRATEGY_MAP = Collections.unmodifiableMap(pathStrategyByName);
    }

    public static void main(String[] args) {
        final Arguments arguments = new Arguments(args);
        if (arguments.isHelp()) {
            arguments.displayHelp();
            return;
        }

        final PathSearchStrategy strategy = STRATEGY_MAP.getOrDefault(arguments.getStrategy(), PathSearchStrategy.DIJKSTRA);
        LOGGER.info(() -> String.format("Using %s strategy", strategy));

        final Optional<Path> providedPath = arguments.getMazePath().flatMap(Main::getFilePath);
        final boolean mazeProvided = providedPath.isPresent();
        final Path filePath = providedPath
                .orElseGet(Main::getFileChooserPath);

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
        final Queue<PositionalGraphNode> route = timeActionAndGetResult(() -> PATH_FINDER.findShortestPath(
                graph.getStartNode(), graph.getEndNode(), strategy),
                "Search finished in %d seconds");


        LOGGER.info("Drawing route on maze...");
        maze.drawRoute(route);


        LOGGER.info("Writing to file...");
        try {
            maze.writeToFile(filePath.resolveSibling("solved_" + filePath.getFileName().toString()));
        } catch (IOException e) {
            LOGGER.severe(() -> "Failed to write maze to file because: " + e.getMessage());
        }

        final String endMessage = "Processing file " + filePath.getFileName() + " done!";
        if (mazeProvided) {
            LOGGER.info(endMessage);
        } else {
            JOptionPane.showMessageDialog(null, endMessage);
        }
    }

    private static <T> T timeActionAndGetResult(Supplier<? extends T> supplier, String format) {
        final long ref = System.currentTimeMillis();

        final T result = supplier.get();

        LOGGER.info(() -> String.format(format, (System.currentTimeMillis() - ref) / 1000));
        return result;
    }

    private static Path getFileChooserPath() {
        final JFileChooser chooser = new JFileChooser(Paths.get(".").toAbsolutePath().toFile());
        chooser.setFileFilter(new FileNameExtensionFilter("PNG files", "png"));
        final int result = chooser.showOpenDialog(null);

        if (JFileChooser.APPROVE_OPTION != result) {
            return null;
        }

        return chooser.getSelectedFile().toPath();
    }

    private static Optional<Path> getFilePath(String path) {
        final Path mazePath;
        try {
            mazePath = Paths.get(path);
            if (!mazePath.getFileName().toString().endsWith(".png") && Files.exists(mazePath)) {
                throw new InvalidPathException(path, "Not an existing png file");
            }
        } catch (InvalidPathException e) {
            LOGGER.severe(() -> String.format("Invalid path provided: %s", path));
            return Optional.empty();
        }

        return Optional.of(mazePath);
    }

    private static ImageMaze readMaze(Path path) {
        final ImageMaze maze;
        try {
            maze = ImageMaze.fromFile(path);
        } catch (IOException ex) {
            LOGGER.severe(() -> String.format("Unable to read file %s", ex.getMessage()));
            return null;
        }
        return maze;
    }

    private static void configureLogging() {
        try {
            LogManager.getLogManager().readConfiguration(Main.class.getResourceAsStream("/logging.properties"));
        } catch (IOException e) {
            throw new IllegalStateException("Could not read logging configuration", e);
        }
    }

}
