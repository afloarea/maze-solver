package com.github.afloarea.maze_solver.convertors.util.unidirectional;

import com.github.afloarea.maze_solver.algorithms.model.GraphNode;
import com.github.afloarea.maze_solver.imaging.IntPoint;

import java.util.HashMap;
import java.util.Map;

public final class MatrixToGraphConverter {
    private static final int FREE_TILE = 1;
    private static final int BLOCKED_TILE = 0;
    private static final int MARKED_TILE = -1;

    private final Map<IntPoint, GraphNode> coordinates = new HashMap<>();

    private final float[][] data;
    private final int startingId;

    private GraphNode firstNode;
    private GraphNode lastNode;

    public MatrixToGraphConverter(float[][] data, int startingId) {
        this.data = data;
        this.startingId = startingId;
    }

    public Map<IntPoint, GraphNode> getCoordinates() {

        int id = startingId;

        final int lastRow = data.length - 1;
        final int lastColumn = data[0].length - 1;

        // starting position
        for (int j = 1; j <= lastColumn; j++) {
            if (data[0][j] == FREE_TILE) {
                markTile(0, j);
                firstNode = new GraphNode(startingId);
                coordinates.put(new IntPoint(j, 0), firstNode);
                break;
            }
        }

        // main section
        for(int i = 1; i < lastRow; i++){
            for(int j = 1; j < lastColumn; j++){
                if(data[i][j] == FREE_TILE){
                    if(isTunnelOrSurrounded(i, j)) continue;

                    markTile(i, j);
                    final GraphNode node = new GraphNode(++id);
                    coordinates.put(new IntPoint(j, i), node);

                    lookForNeighbourToTheLeft(i, j, node);
                    lookForNeighbourUp(i, j, node);
                }
            }
        }

        // final position
        for (int j = 1; j <= lastColumn; j++) {
            if (data[lastRow][j] == FREE_TILE) {
                lastNode = new GraphNode(++id);
                coordinates.put(new IntPoint(j, lastColumn), lastNode);
                lookForNeighbourUp(lastRow, j, lastNode);
                break;
            }
        }

        coordinates.keySet().forEach(pixel -> clearMark(pixel.getY(), pixel.getX()));

        return coordinates;

    }

    private void lookForNeighbourUp(int row, int column, GraphNode node) {
        int i = row, distance = 0;
        while (data[--i][column] != BLOCKED_TILE) {
            distance++;
            if (data[i][column] == MARKED_TILE) {
                final GraphNode neighbour = coordinates.get(new IntPoint(column, i));
                GraphNode.createNeighbours(node, neighbour, distance);
                break;
            }
        }
    }

    private void lookForNeighbourToTheLeft(int row, int column, GraphNode node) {
        int j = column, distance = 0;
        while (data[row][--j] != BLOCKED_TILE) {
            distance++;
            if (data[row][j] == MARKED_TILE) {
                final GraphNode neighbour = coordinates.get(new IntPoint(j, row));
                GraphNode.createNeighbours(node, neighbour, distance);
                break;
            }
        }
    }


    private void markTile(int i, int j) {
        data[i][j] = MARKED_TILE;
    }

    private void clearMark(int i, int j) {
        data[i][j] = FREE_TILE;
    }

    private boolean isTunnelOrSurrounded(int row, int column) {
        boolean isTunnel = checkTunnel(data, row, column);
        if(isTunnel) return true;

        boolean surrounded = true;
        for (int k = row - 1; k <= row + 1; k++) {
            for (int l = column - 1; l <= column + 1; l++) {
                if (data[k][l] == 0) {
                    surrounded = false;
                    break;
                }
            }
            if (!surrounded) {
                break;
            }
        }

        return surrounded;
    }

    public GraphNode getFirstNode() {
        return firstNode;
    }

    public GraphNode getLastNode() {
        return lastNode;
    }

    private static boolean checkTunnel(float[][] data, int row, int column) {
        return data[row - 1][column] == BLOCKED_TILE && data[row + 1][column] == BLOCKED_TILE
                && data[row][column - 1] != BLOCKED_TILE && data[row][column + 1] != BLOCKED_TILE

                || data[row - 1][column] != BLOCKED_TILE && data[row + 1][column] != BLOCKED_TILE
                && data[row][column - 1] == BLOCKED_TILE && data[row][column + 1] == BLOCKED_TILE;
    }
}
