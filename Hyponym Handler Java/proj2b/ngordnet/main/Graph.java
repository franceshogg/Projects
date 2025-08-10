package ngordnet.main;
import java.util.*;
import java.util.ArrayList;

public class Graph {
    //adjList
    HashMap<Integer, LinkedList<Integer>> graphMap;
    int vertices;
    int edges;
    List<Integer> allVertices;

    //graph constructor. Creates hashmap. Hashmap will contain linked lists of hyponyms.
    public Graph() {
        graphMap = new HashMap<>();
        vertices = 0;
        allVertices = new ArrayList<Integer>();
        edges = 0;
    }

    //Creates bucket with "val" contained, adds bucket to graph
    public void addVal(Integer val) {
        LinkedList<Integer> bucket = new LinkedList<>();
        bucket.add(val);
        graphMap.put(val, bucket);
        vertices++;
        allVertices.add(val);
    }

    //adds child and child's hyponyms to parent
    public void addEdge(int parent, int child) {
        if (graphMap.get(child) == null) {
            LinkedList<Integer> parentBucket = graphMap.get(parent);
            parentBucket.add(child);
            return;
        }
        if (graphMap.get(parent) == null)  {
            return;
        } else {
            LinkedList<Integer> parentBucket = graphMap.get(parent); //gets parent bucket
            LinkedList<Integer> childVals = graphMap.get(child);
            for (int i : childVals) {
                parentBucket.add(i);
            }
            edges++;
        }
    }

    //returns number of vertices
    public int V() {
        return vertices;
    }

    //returns number of edges
    public int E() {
        return edges;
    }

    //method to see what graph looks like
    public void print() {
        for (Integer i : graphMap.keySet()) {
            for (Integer j : graphMap.get(i)) {
                System.out.print(j + " -> ");
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        Graph graphMap = new Graph();
        graphMap.addVal(1);
        graphMap.addVal(2);
        graphMap.addVal(3);
        graphMap.addVal(4);
        graphMap.addVal(5);
        graphMap.addVal(6);
        graphMap.addEdge(6, 1);
        graphMap.addEdge(6, 2);
        graphMap.addEdge(6, 3);
        graphMap.addEdge(4, 6);
        graphMap.print();
    }
}
