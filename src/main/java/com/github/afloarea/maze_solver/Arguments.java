package com.github.afloarea.maze_solver;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Class used for mapping command line arguments.
 */
public final class Arguments {

    private final List<String> args;

    public Arguments(String[] args) {
        this.args = Arrays.asList(args);
    }

    public boolean isHelp() {
        return args.stream().anyMatch(option -> "-h".equals(option) || "--help".equals(option));
    }

    public void displayHelp() {
        final PrintWriter out;
        if (System.console() != null) {
            out = System.console().writer();
        } else {
            out = new PrintWriter(System.out, true);
        }

        out.format(
                "%nSolve a maze using a strategy." +
                        "%nOptions:" +
                        "%n\t-s,  --strategy    \tThe strategy to use. Possible values: dijkstra, a-star, bfs, dfs (default dijkstra)" +
                        "%n\t-mp, --maze-path   \tThe path to the maze. If not specified a file chooser will be used" +
                        "%n\t-h,  --help        \tDisplay the help message" +
                        "%n"
        );
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
