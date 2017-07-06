/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ffa.main;

import ffa.algorithms.Dijkstra;
import ffa.algorithms.GraphAlgorithm;
import ffa.convert.Converter;
import ffa.graph.Graph;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author FFA
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        JFileChooser chooser = new JFileChooser(new File("").getAbsoluteFile());
        chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();
        String fileName = null;
        if(file != null) fileName = file.getName().split("\\.")[0];
        else System.exit(0);

        Converter converter = new Converter(file);
        long ref = System.currentTimeMillis();
        Graph g = converter.getGraph();
        double delta = (System.currentTimeMillis() - ref)/1000.0;
        System.out.println("Finished converting to graph. Duration: " + delta + " seconds");
        System.out.println("Graph has " + (g.getLastNodeIndex() + 1) + " nodes");
        
        GraphAlgorithm algorithm = new Dijkstra();
        ref = System.currentTimeMillis();
        algorithm.process(g);
        delta = (System.currentTimeMillis() - ref)/1000.0;
        System.out.println("Finished processing graph. Duration : " + delta + " seconds");
        List<Integer> path = algorithm.getResult();
        
        System.out.println("Building image...");
        BufferedImage res = converter.getWithHighlightedPath(path);
        File out = new File(fileName + "_solved.png");
        ImageIO.write(res, "png", out);
        System.out.println("Done image with path");
        
        JOptionPane.showMessageDialog(null, "Processing done. Created file " + fileName + "_solved.png");
        
//        System.out.println("Building image with nodes...");
//        res = converter.getWithHighlightedNodes();
//        out = new File(fileName + "_nodes.png");
//        ImageIO.write(res, "png", out);
//        System.out.println("Done image with nodes");
//        
//        System.out.println("Building image with graph...");
//        res = converter.getWithGraph();
//        out = new File(fileName + "_graph.png");
//        ImageIO.write(res, "png", out);
//        System.out.println("Done image with graph");
    }
    
}
