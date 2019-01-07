package A2;
import java.util.*;

public class Kruskal{

    public static WGraph kruskal(WGraph g){

        /* Fill this method (The statement return null is here only to compile) */
    	WGraph mst = new WGraph();
    	Edge currentEdge;
    	ArrayList<Edge> listOfEdgesSorted = g.listOfEdgesSorted();
    	int n = g.getNbNodes();
    	DisjointSets setOfNodes = new DisjointSets(n);
    	
    	
    	for (int i = 0; i < listOfEdgesSorted.size(); i++) {
    		currentEdge = listOfEdgesSorted.get(i);
    		if (IsSafe(setOfNodes, currentEdge)) {
    			mst.addEdge(currentEdge);
    			setOfNodes.union(currentEdge.nodes[0], currentEdge.nodes[1]);
    		}
    	}
    	
        
        return mst;
    }

    public static Boolean IsSafe(DisjointSets p, Edge e){

        /* Fill this method (The statement return 0 is here only to compile) */
    	
    	// checks if the edge e connects two disconnected tree in DisjointSets p
    	int repNode0 = p.find(e.nodes[0]); // representative in the disjoint set of the first node of the edge
    	int repNode1 = p.find(e.nodes[1]); // representative in the disjoint set of the second node of the edge
    	
    	if (repNode0 == repNode1) return false; // the two nodes are in the same disjoint sets, so if the edge connects them, it will form a circle or a loop
    	
        return true;
    
    }

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        WGraph t = kruskal(g);
        System.out.println(t);
   } 
}
