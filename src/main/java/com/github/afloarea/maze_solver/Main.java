package com.github.afloarea.maze_solver;


import com.github.afloarea.maze_solver.algorithms.ShortestPathAlgorithm;
import com.github.afloarea.maze_solver.algorithms.impl.Dijkstra;
import com.github.afloarea.maze_solver.convertors.BidirectionalConverter;
import com.github.afloarea.maze_solver.convertors.util.DefaultBidirectionalConverter;
import com.github.afloarea.maze_solver.data.Graph;
import com.github.afloarea.maze_solver.data.GraphNode;
import com.github.afloarea.maze_solver.imaging.ImageContainer;

import javax.swing.*;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception{
        final JFileChooser chooser = new JFileChooser(Paths.get(".").toAbsolutePath().toFile());
        chooser.showOpenDialog(null);
        final File file = chooser.getSelectedFile();
        if (file == null) System.exit(0);
        final String fileName = file.getName();

        final ImageContainer container =
                logActionAndGetResult(() -> ImageContainer.fromFile(file.toPath()), "Read file in ");

        final BidirectionalConverter converter = new DefaultBidirectionalConverter(container);
        final ShortestPathAlgorithm algorithm = new Dijkstra();

        final Graph graph = logActionAndGetResult(converter::extractGraphFromImage, "Extracted graph in ");

        final List<GraphNode> route = logActionAndGetResult(
                () -> algorithm.calculateShortestPath(graph), " Calculated shortest path in ");

        converter.updateImage(route);
        container.writeToFile(Paths.get(file.getParent(), fileName.split("\\.")[0] + "_solved.png"));

        JOptionPane.showMessageDialog(null, "Processing file " + fileName + " done!");
    }

    private static <T> T logActionAndGetResult(Supplier<? extends T> supplier, String messagePrefix) {
        final long ref = System.currentTimeMillis();
        final T result = supplier.get();
        LOGGER.info(messagePrefix + (System.currentTimeMillis() - ref) / 1000 + " seconds");

        return result;
    }

}
