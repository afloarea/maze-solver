/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ffa.algorithms;

import ffa.graph.Graph;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 *
 * @author FFA
 */
public class Dijkstra implements GraphAlgorithm{
    
    private final Set<Integer> visited;
    private final Map<Integer, Integer> previousNode;
    private final PriorityQueue<NodeElement> queue;
    private int lastNode;
    
    private class NodeElement implements Comparable<NodeElement>{
        private final int node;
        private final int previousNode;
        private final int cost;
        
        public NodeElement(int node, int previousNode, int cost){
            this.node = node;
            this.cost = cost;
            this.previousNode = previousNode;
        }

        @Override
        public int compareTo(NodeElement o) {
            return cost - o.cost;
        }
    }

    public Dijkstra(){
        this.visited = new HashSet<>();
        this.previousNode = new HashMap<>();
        this.queue = new PriorityQueue<>();
    }
    
    
    @Override
    public void process(Graph graph) {      
        lastNode = graph.getLastNodeIndex();
        
        visited.add(0);
        previousNode.put(0, 0);
        
        int[] neighbours = new int[4]; // ---
        int[] distances = new int[4]; // ---
        
        int numberOfNeighbours = graph.getDataForNode(0, neighbours, distances);
        for(int i =0; i < numberOfNeighbours; i++){
            queue.add(new NodeElement(neighbours[i], 0, distances[i]));
        }
        
        while(!queue.isEmpty()){
            
            NodeElement n = queue.poll();
            if(visited.contains(n.node)) continue;
            
            visited.add(n.node);
            previousNode.put(n.node, n.previousNode);
            
            numberOfNeighbours = graph.getDataForNode(n.node, neighbours, distances);
            for(int i = 0; i < numberOfNeighbours; i++){
                int cost = distances[i] + n.cost;
                queue.add(new NodeElement(neighbours[i], n.node, cost));
            }
            
            if(n.node == lastNode){
                queue.clear();
                visited.clear();
            }
            
        }
        
    }

    @Override
    public List<Integer> getResult() {
        
        LinkedList<Integer> result  = new LinkedList<>();
        while(lastNode != 0){
            result.addFirst(lastNode);
            lastNode = previousNode.get(lastNode);
        }
        result.addFirst(0);
        return result;
        
    }
    
    
    
}
