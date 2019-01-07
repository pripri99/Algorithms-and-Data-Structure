import java.io.*;
import java.util.*;




public class FordFulkerson {
	
	// Helper method to check if all nodes in the graph have been visited
		public static boolean allVisited(boolean[] visited, int nbNodes) {
			int i = 0;
			while(i<nbNodes){
				if(visited[i]==false){
					return false;
				}
				i++;
			}
			
			return true;
		}

	
	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> Stack = new ArrayList<Integer>();
		/* YOUR CODE GOES HERE*/
		if(source == destination)return Stack;
		
		int nbNodes = graph.getNbNodes();
		Integer node1;
		Integer node2;
		boolean pathFound = false;
		boolean visited[] = new boolean[nbNodes]; 
		for(int i=0; i<nbNodes; ++i){
			visited[i]=false; 
		}
		Stack.add(source);
		visited[source]=true;
		
		Edge anEdge;
		node1 = source;
		while (!pathFound) {
			
			if (node1 == destination) {
				pathFound = true;
				break;
			}
			boolean nodeFound = false;
			for (int aNode=0; aNode<nbNodes; aNode++) {
				
				node2 = aNode;
				anEdge = graph.getEdge(node1, node2);
				if (anEdge != null) {
					if ((visited[node2]== false) && (anEdge.weight != 0)) {
						Stack.add(node2);
						visited[node2]=true;
						nodeFound = true;
						node1 = node2;
						break;
						
					}
				}
			}
			

			
			if(allVisited(visited, nbNodes) && Stack.get(Stack.size()-1) != destination) {
				ArrayList<Integer> emptyStack = new ArrayList<Integer>();
				return emptyStack;
			}
			
			if (!nodeFound) {
				if (Stack.get(Stack.size()-1) == source){
					ArrayList<Integer> emptyStack = new ArrayList<Integer>();
					return emptyStack;
				} else{
					Stack.remove(Stack.size()-1);
					node1 = Stack.get(Stack.size()-1);
				}
			}
			
		
		}
		
		
		return Stack;
	}
	

	public static void fordfulkerson(Integer source, Integer destination, WGraph graph, String filePath){
		String answer="";
		String myMcGillID = "260722807"; //Please initialize this variable with your McGill ID
		int maxFlow = 0;
		
				/* YOUR CODE GOES HERE */
		
		ArrayList<Edge> allEdge = graph.getEdges();
		
		// get capacity of all edges
		int[][] capacity = new int[graph.getNbNodes()][graph.getNbNodes()];
		for (Edge e: allEdge) capacity[e.nodes[0]][e.nodes[1]] = e.weight;
		
		WGraph rG = new WGraph(graph); //residual graph
		ArrayList<Integer> path;
		int bottleneck = 0; //bottleneck capacity
		int node2 = 0;
		int node1 =0;
		int newCapacity = 0;
		int reverseCapacity = 0;
		boolean noPath = false;
		int counter = 0;
		try {
			while (!noPath) {
				path = pathDFS(source,destination,rG);
				if (path.isEmpty()) {
					noPath = true;
					break;
				}
				counter++;
				
				bottleneck = rG.getEdge(path.get(0), path.get(1)).weight;
				
				//search for bottleneck capacity
				for (int i=0; i<path.size()-1; i++) {
					node1 = path.get(i);
					node2 = path.get(i+1);
					int tempB = rG.getEdge(node1, node2).weight;
					if (tempB < bottleneck) bottleneck = tempB;
				}
	
				
				//update maxflow
				maxFlow = maxFlow+bottleneck;
				
				for (int i=0; i<path.size()-1; i++) {
					node1 = path.get(i);
					node2 = path.get(i+1);
					int capacityEdge = capacity[node1][node2];
					Edge edge1 = rG.getEdge(node1, node2);
					
					newCapacity = edge1.weight - bottleneck;
					rG.setEdge(node1, node2, newCapacity);
					reverseCapacity = capacityEdge - newCapacity;
					
					Edge reverseEdge1 = rG.getEdge(node2, node1);
					if (reverseEdge1 == null) {
						reverseEdge1 = new Edge(node2, node1, reverseCapacity);
						rG.addEdge(reverseEdge1);
					}
				
				}
				int finalWeight =0;
				for (Edge e1:graph.getEdges()) {
					for (Edge e2:rG.getEdges()) {
						if ((e1.nodes[0]==e2.nodes[0]) && (e1.nodes[1]==e2.nodes[1])) {
							Edge reverseEdge = rG.getEdge(e1.nodes[1], e1.nodes[0]);
							finalWeight = capacity[e2.nodes[0]][e2.nodes[1]] - e2.weight;
							graph.setEdge(e2.nodes[0], e2.nodes[1], finalWeight);
						}
					}
				}
				
				
			}
			if (noPath && counter == 0) {
				for (Edge e1:graph.getEdges()) {
					for (Edge e2:rG.getEdges()) {
						if ((e1.nodes[0]==e2.nodes[0]) && (e1.nodes[1]==e2.nodes[1])) {
							int finalWeight = 0;
							graph.setEdge(e2.nodes[0], e2.nodes[1], finalWeight);
						}
					}
				}
			}
		} catch (Exception e) {
			maxFlow = -1;
		}
		
		answer += maxFlow + "\n" + graph.toString();	
		writeAnswer(filePath+myMcGillID+".txt",answer);
		System.out.println(answer);
	}
	
	
	public static void writeAnswer(String path, String line){
		BufferedReader br = null;
		File file = new File(path);
		// if file doesnt exists, then create it
		
		try {
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(line+"\n");	
		bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	 public static void main(String[] args){
		 String file = args[0];
		 File f = new File(file);
		 WGraph g = new WGraph(file);
		 fordfulkerson(g.getSource(),g.getDestination(),g,f.getAbsolutePath().replace(".txt",""));
	 }
}
