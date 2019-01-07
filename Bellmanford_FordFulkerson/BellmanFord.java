import java.util.ArrayList;

public class BellmanFord{

	
	/**
	 * Utility class. Don't use.
	 */
	public class BellmanFordException extends Exception{
		private static final long serialVersionUID = -4302041380938489291L;
		public BellmanFordException() {super();}
		public BellmanFordException(String message) {
			super(message);
		}
	}
	
	/**
	 * Custom exception class for BellmanFord algorithm
	 * 
	 * Use this to specify a negative cycle has been found 
	 */
	public class NegativeWeightException extends BellmanFordException{
		private static final long serialVersionUID = -7144618211100573822L;
		public NegativeWeightException() {super();}
		public NegativeWeightException(String message) {
			super(message);
		}
	}
	
	/**
	 * Custom exception class for BellmanFord algorithm
	 *
	 * Use this to specify that a path does not exist
	 */
	public class PathDoesNotExistException extends BellmanFordException{
		private static final long serialVersionUID = 547323414762935276L;
		public PathDoesNotExistException() { super();} 
		public PathDoesNotExistException(String message) {
			super(message);
		}
	}
	
    private int[] distances = null;
    private int[] predecessors = null;
    private int source;

    BellmanFord(WGraph g, int source) throws BellmanFordException{
        /* Constructor, input a graph and a source
         * Computes the Bellman Ford algorithm to populate the
         * attributes 
         *  distances - at position "n" the distance of node "n" to the source is kept
         *  predecessors - at position "n" the predecessor of node "n" on the path
         *                 to the source is kept
         *  source - the source node
         *
         *  If the node is not reachable from the source, the
         *  distance value must be Integer.MAX_VALUE
         *  
         *  When throwing an exception, choose an appropriate one from the ones given above
         */
        
        /* YOUR CODE GOES HERE */
    	
    	this.source = source;
    	int n = g.getNbNodes();
    	this.distances = new int[n];
    	this.predecessors = new int[n];
    	
    	for (int i =0; i<this.predecessors.length; i++) this.predecessors[i] = -1;
    	
		int node1=0;
		int node2=0;
    	for (Edge e: g.getEdges()) {
    		if (e.nodes[0] != source) {
    			this.distances[e.nodes[0]]=Integer.MAX_VALUE;
    			
    		}
    		if (e.nodes[1] != source) {
    			this.distances[e.nodes[1]]=Integer.MAX_VALUE;
    		}
    		this.predecessors[e.nodes[1]]= e.nodes[0];
    	}
    	this.distances[source]=0;
    	
    	//All distance computed 2 times
    	int counter = 0;
    	while ( counter <2) {
			for (int j=0; j<g.getNbNodes(); j++) {
				node1 = j;
		    	for (int i=0; i<g.getNbNodes(); i++) {
		    		node2 = i;
		    		if (g.getEdge(node1, node2) != null) {
		    			Edge anEdge = g.getEdge(node1, node2); 
			    		if ((this.distances[node1] != Integer.MAX_VALUE) && (this.distances[node1] + anEdge.weight < this.distances[node2])){
			    			this.distances[node2] = this.distances[node1] + anEdge.weight;
			    			this.predecessors[node2] = node1;
			    		}
		    		}
		    		
		    	}
	    	}
			counter++;
    	}
    	
    	for (Edge e: g.getEdges()) {
    		node1 = e.nodes[0];
    		node2 = e.nodes[1];
    		if ((this.distances[node1] != Integer.MAX_VALUE) && this.distances[node1]+e.weight < this.distances[node2]) {
    			throw new NegativeWeightException("There is a negative weight cycle.");
    			
    		}
    	}

    }

    public int[] shortestPath(int destination) throws BellmanFordException{
        /*Returns the list of nodes along the shortest path from 
         * the object source to the input destination
         * If not path exists an Exception is thrown
         * Choose appropriate Exception from the ones given 
         */

        /* YOUR CODE GOES HERE (update the return statement as well!) */
    	int[] path = null;
    	ArrayList<Integer> tempPath = new ArrayList<Integer>();
    	int p = 0 ;
    	tempPath.add(destination);
    	if (this.source == destination) {
    		path = new int[1];
    		path[0] = source;
    		return path;
    	}
    	int index = 0;
    	boolean pathFound = false;
    	
    	while (!pathFound) {
    		p = this.predecessors[tempPath.get(index)];
    		if (p == this.source) {
    			tempPath.add(p);
    			pathFound = true;
    			break;
    		}
    		if (p == -1) {
    			pathFound = false;
    			break;
    		} else {
    			tempPath.add(p);
    			index++;
    		}
    		
    		
    	}
    	
    	if(pathFound == false) throw new PathDoesNotExistException("A path does not extist from "+ this.source + " to "+ destination);
    	
    	path = new int[tempPath.size()];
    	int j =0;
    	for (int i=tempPath.size()-1; i>=0; i--) {
    		path[j] = tempPath.get(i);
    		j++;
			
    	}
    	

    	
        
        return path;
    }

    public void printPath(int destination){
        /*Print the path in the format s->n1->n2->destination
         *if the path exists, else catch the Error and 
         *prints it
         */
        try {
            int[] path = this.shortestPath(destination);
            for (int i = 0; i < path.length; i++){
                int next = path[i];
                if (next == destination){
                    System.out.println(destination);
                }
                else {
                    System.out.print(next + "-->");
                }
            }
        }
        catch (BellmanFordException e){
            System.out.println(e);
        }
    }

    public static void main(String[] args){
    	


	        String file = args[0];
	        WGraph g = new WGraph(file);
	        try{
	            BellmanFord bf = new BellmanFord(g, g.getSource());
	            bf.printPath(g.getDestination());
	        }
	        catch (BellmanFordException e){
	            System.out.println(e);
	        }
    	

   } 
}
