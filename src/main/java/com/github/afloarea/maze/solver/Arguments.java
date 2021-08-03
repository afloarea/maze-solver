package com.github.afloarea.maze.solver;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Class used for mapping command line arguments.
 */
public final class Arguments {
    private static final String HELP_MESSAGE = """
            Solve a maze using a strategy.
            Options:
                -s,  --strategy     The strategy to use. Possible values: dijkstra, a-star, bfs, dfs (default dijkstra)
                -mp, --maze-path    The path to the maze. If not specified a file chooser will be used
                -h,  --help         Display the help message
            """;

    private static final PrintWriter OUT = System.console() != null
            ? System.console().writer() : new PrintWriter(System.out, true);

    private final List<String> args;

    public Arguments(String[] args) {
        this.args = Arrays.asList(args);
    }

    public boolean isHelp() {
        return args.stream().anyMatch(option -> "-h".equals(option) || "--help".equals(option));
    }

    public void displayHelp() {
        OUT.println(HELP_MESSAGE);
    }

    public String getStrategy() {
        int index = args.indexOf("-s");
        index = index == -1 ? args.indexOf("--strategy") : index;

        if (index == -1 || args.size() - 1 == index) {
            return "dijkstra";
        }

        return args.get(index + 1).toLowerCase();
    }

    public Optional<String> getMazePath() {
        int index = args.indexOf("-mp");
        index = index == -1 ? args.indexOf("--maze-path") : index;

        if (index == -1 || args.size() - 1 == index) {
            return Optional.empty();
        }

        return Optional.of(args.get(index + 1));
    }
}
