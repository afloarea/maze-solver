/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ffa.convert;

import ffa.graph.Graph;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author FFA
 */
public class Converter {
    
    private final File imageFile;
    private int[][] matrix;
    private final Graph graph;
    private boolean graphDone = false;
    private int node = 2;
    
    private class Coordinates{
        private final int column;
        private final int row;

        public Coordinates(int column, int row) {
            this.column = column;
            this.row = row;
        }
    }
    
    public Converter(File imageFile){
        this.imageFile = imageFile;
        graph = new Graph();
    }

    public Graph getGraph() {
        if(!graphDone) {
            buildGraph();
            graphDone = true;
        }
        return graph;
    }

    private void buildGraph() {
        
        matrix = getImageMatrix();
        //node = 2;
        
        int lastRow = matrix.length - 1;
        int lastColumn = matrix[0].length -1;
        
        for(int i = 1; i < lastColumn; i++){
            if(matrix[0][i] == 1) {
                matrix[0][i] = node++;
                graph.addNewNode();
                break;
            }
        }
        
        for(int i = 1; i < lastRow; i++){
            for(int j = 1; j < lastColumn; j++){                
                if(matrix[i][j] == 1){
                    if(!isJunction(i, j)) continue;
                    
                    matrix[i][j] = node++;
                    graph.addNewNode();
                    lookForNeighbourToTheLeft(i, j);
                    lookForNeighbourUp(i, j);
                }
            }
        }
        
        for(int i = 1; i < matrix[matrix.length - 1].length; i++){
            if(matrix[matrix.length - 1][i] == 1){
                matrix[matrix.length - 1][i] = node;
                graph.addNewNode();
                lookForNeighbourUp(matrix.length - 1, i);
                break;
            }
        }
    }
    
    private void lookForNeighbourToTheLeft(int row, int column){
        
        int k = row, l = column - 1, d = 0;
        while (matrix[k][l] != 0) {
            d++;
            if (matrix[k][l] != 1) {
                int node2 = matrix[k][l];
                int node1 = matrix[row][column];
                graph.addNeighbours(node1 - 2, node2 - 2, d);
                break;
            }
            l--;
        }
        
    }
    
    private void lookForNeighbourUp(int row, int column){
        int k = row - 1, l = column, d = 0;
        while (matrix[k][l] != 0) {
            d++;
            if (matrix[k][l] != 1) {
                int node2 = matrix[k][l];
                int node1 = matrix[row][column];
                graph.addNeighbours(node1 - 2, node2 - 2, d);
                break;
            }
            k--;
        }
    }
    
    private boolean isJunction(int row, int column){
        // check if it is tunnel or dead end
        boolean notUseful = matrix[row-1][column] + matrix[row+1][column] == 0 || matrix[row][column-1] + matrix[row][column+1] == 0 || matrix[row+1][column] + matrix[row][column+1] + matrix[row-1][column] + matrix[row][column-1] <= 1;
        if(notUseful) return false;
        
        boolean surrounded = true;
        for (int k = row - 1; k <= row + 1; k++) {
            for (int l = column - 1; l <= column + 1; l++) {
                if (matrix[k][l] == 0) {
                    surrounded = false;
                    break;
                }
            }
            if (!surrounded) {
                break;
            }
        }
        
        return !surrounded;
    }
    
    private int[][] getImageMatrix(){
        int[][] imageArray = null;
        
        try {
            
            BufferedImage image = ImageIO.read(imageFile);
            int height = image.getHeight();
            int width = image.getWidth();
            
            imageArray = new int[height][width];
            Raster raster = image.getData();
            
            for(int i = 0; i< height; i++){
                for(int j = 0; j< width; j++){
                    imageArray[i][j] = raster.getSample(j, i, 0);
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
        }

        return imageArray;
    }
    
    public BufferedImage getWithHighlightedPath(List<Integer> path){
        Set<Integer> pathNodes = new HashSet<>(path);
        Map<Integer, Coordinates> locations = new HashMap<>();
        int width = matrix[0].length;
        int height = matrix.length;
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        for(int i = 0; i < height; i++){
            for(int j = 0 ; j < width; j++){
                if(matrix[i][j] == 0){
                    result.setRGB(j, i, Color.BLACK.getRGB());
                } else if(matrix[i][j] == 1){
                    result.setRGB(j, i, Color.WHITE.getRGB());
                } else if(pathNodes.remove(matrix[i][j] - 2)){
                    locations.put(matrix[i][j] - 2, new Coordinates(j, i));
                } else {
                    result.setRGB(j, i, Color.WHITE.getRGB());
                }
            }
        } 
        
        
        Graphics g = result.getGraphics();
        g.setColor(Color.GREEN);
        for(int i = 1; i < path.size(); i++){
            Coordinates c1  = locations.get(path.get(i - 1));
            Coordinates c2 = locations.get(path.get(i));
            g.drawLine(c1.column, c1.row, c2.column, c2.row);
        }
        
        return result;
    }
    
    //optional
    public BufferedImage getWithHighlightedNodes(){
        int width = matrix[0].length;
        int height = matrix.length;
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if(matrix[i][j] > 1){
                    result.setRGB(j, i, Color.BLUE.getRGB());
                } else if(matrix[i][j] == 0){
                    result.setRGB(j, i, Color.BLACK.getRGB());
                } else if(matrix[i][j] == 1){
                    result.setRGB(j, i, Color.WHITE.getRGB());
                }
            }
        }
        
        return result;
    }
    
    //optional
    public BufferedImage getWithGraph(){
        BufferedImage result = getWithHighlightedNodes();
        int height = result.getHeight();
        int width = result.getWidth();     
        
        List<Coordinates> coords = new ArrayList<>(node - 2);
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                int x = matrix[i][j];
                if(x > 1) coords.add(new Coordinates(j, i));
            }
        }
        
        Graphics g = result.getGraphics();
        g.setColor(Color.BLUE);
        int[] neighbours = new int[4];
        int[] distances = new int[4];
        int numberOfNeighbours = 0;
        for(int i = 0; i < node - 2; i++){
            numberOfNeighbours = graph.getDataForNode(i, neighbours, distances);
            for(int j = 0; j < numberOfNeighbours; j++){
                if(i > neighbours[j]){
                    Coordinates coordsI = coords.get(i);
                    Coordinates coordsJ = coords.get(neighbours[j]);
                    g.drawLine(coordsJ.row, coordsJ.column, coordsI.row, coordsI.column);
                }
            }
        }
        g.dispose();
        
        return result;
    }
    
}
