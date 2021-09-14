package com.github.afloarea.maze.solver;


import com.github.afloarea.maze.solver.algorithms.PathFinder;
import com.github.afloarea.maze.solver.algorithms.PathSearchStrategy;
import com.github.afloarea.maze.solver.convertors.MazeToGraphConverter;
import com.github.afloarea.maze.solver.convertors.impl.PositionalGraphExtractor;
import com.github.afloarea.maze.solver.graph.PositionalGraph;
import com.github.afloarea.maze.solver.graph.PositionalGraphNode;
import com.github.afloarea.maze.solver.maze.ImageMaze;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.function.Supplier;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Application entrypoint.
 */
public final class Main {
    static {
        configureLogging();
    }

    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    private static final MazeToGraphConverter CONVERTER = new PositionalGraphExtractor();
    private static final Map<String, PathSearchStrategy> STRATEGY_MAP = Map.of(
            "dijkstra", PathSearchStrategy.DIJKSTRA,
            "a-star",   PathSearchStrategy.A_STAR,
            "bfs",      PathSearchStrategy.BFS,
            "dfs",      PathSearchStrategy.DFS);

    public static void main(String[] args) {
        final var arguments = new Arguments(args);
        if (arguments.isHelp()) {
            arguments.displayHelp();
            return;
        }

        final var strategy = STRATEGY_MAP.getOrDefault(arguments.getStrategy(), PathSearchStrategy.DIJKSTRA);
        LOG.info(() -> String.format("Using %s strategy", strategy));

        final boolean mazeProvided = arguments.getMazePath().isPresent();
        final Optional<Path> optionalPath = mazeProvided ? getFilePath(arguments.getMazePath().get()) : getFileChooserPath();
        final Optional<ImageMaze> optionalMaze = optionalPath.flatMap(mazePath -> process(mazePath, strategy));

        if (optionalMaze.isEmpty()) {
            return;
        }

        writeToFileAndNotify(optionalPath.get(), optionalMaze.get(), !mazeProvided);
    }

    private static Optional<ImageMaze> process(Path mazePath, PathSearchStrategy strategy) {
        final ImageMaze maze = readMaze(mazePath);
        if (maze == null) {
            return Optional.empty();
        }

        LOG.info("Converting image to graph...");
        final PositionalGraph graph = timeActionAndGetResult(() -> CONVERTER.convert(maze),
                "Conversion done in %d seconds");

        final PathFinder pathFinder = PathFinder.ofStrategy(strategy);
        LOG.info("Calculating shortest path...");
        final Queue<PositionalGraphNode> route = timeActionAndGetResult(
                () -> pathFinder.findShortestPath(graph.startNode(), graph.endNode()),
                "Search finished in %d seconds");


        LOG.info("Drawing route on maze...");
        maze.drawRoute(route);

        return Optional.of(maze);
    }

    private static void writeToFileAndNotify(Path original, ImageMaze maze, boolean displayDialog) {
        final Path targetPath = original.resolveSibling("solved_" + original.getFileName());
        LOG.info(() -> "Writing to file " + targetPath);
        try {
            maze.writeToFile(targetPath);
        } catch (IOException e) {
            LOG.severe(() -> "Failed to write maze to file because: " + e.getMessage());
            return;
        }

        notifyDone(original, displayDialog);
    }

    private static void notifyDone(Path originalPath, boolean displayDialog) {
        final String endMessage = "Processing file " + originalPath.getFileName() + " done!";
        if (displayDialog) {
            JOptionPane.showMessageDialog(null, endMessage);
        }
        LOG.info(endMessage);
    }

    private static <T> T timeActionAndGetResult(Supplier<? extends T> supplier, String format) {
        final long ref = System.currentTimeMillis();

        final T result = supplier.get();

        LOG.info(() -> String.format(format, (System.currentTimeMillis() - ref) / 1_000));
        return result;
    }

    private static Optional<Path> getFileChooserPath() {
        final var chooser = new JFileChooser(Paths.get(".").toAbsolutePath().toFile());
        chooser.setFileFilter(new FileNameExtensionFilter("PNG files", "png"));
        final int result = chooser.showOpenDialog(null);

        if (JFileChooser.APPROVE_OPTION != result) {
            LOG.info("No file selected");
            return Optional.empty();
        }

        return Optional.of(chooser.getSelectedFile().toPath());
    }

    private static Optional<Path> getFilePath(String path) {
        final Path mazePath;
        try {
            mazePath = Paths.get(path);
            if (!mazePath.getFileName().toString().endsWith(".png") || !Files.exists(mazePath)) {
                throw new InvalidPathException(path, "Not an existing png file");
            }
        } catch (InvalidPathException e) {
            LOG.severe(() -> String.format("Invalid path provided: %s", path));
            return Optional.empty();
        }

        return Optional.of(mazePath);
    }

    private static ImageMaze readMaze(Path path) {
        LOG.info(() -> "Reading maze from file " + path);
        final ImageMaze maze;
        try {
            maze = ImageMaze.fromFile(path);
        } catch (IOException ex) {
            LOG.severe(() -> String.format("Unable to read file %s", ex.getMessage()));
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
