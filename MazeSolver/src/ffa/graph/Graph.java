/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ffa.graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    
    private class Pair{
        private final int node;
        private final int distance;

        public Pair(int node, int distance) {
            this.node = node;
            this.distance = distance;
        }                
    }
    
    private List<List<Pair>> data;

    public Graph() {
        this.data = new ArrayList<>(20);
    }
    
    public void addNewNode(){
        List<Pair> nodeNeighbours = new ArrayList<>(4);
        data.add(nodeNeighbours);
    }
    
    public void addNeighbours(int node1, int node2, int distance){
        data.get(node1).add(new Pair(node2, distance));
        data.get(node2).add(new Pair(node1, distance));
    }
    
    public int getDataForNode(int node, int[] neighbours, int[] distances){
        List<Pair> adjacentNodes = data.get(node);
        int numberOfNeigbours = adjacentNodes.size();
        for(int i = 0; i < numberOfNeigbours; i++){
            Pair p = adjacentNodes.get(i);
            neighbours[i] = p.node;
            distances[i] = p.distance;
        }
        return numberOfNeigbours;
    }
    
    public int getLastNodeIndex(){
        return data.size() - 1;
    }
}
