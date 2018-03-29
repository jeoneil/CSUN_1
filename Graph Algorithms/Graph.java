/*
 * Jack O'Neil
 * 7 December, 2017
 * Project #3
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Graph {

	private ArrayList<EdgeNode>[] adjList;
	private int nVertices;
	private int nEdges;
	private String fileName;
 
	/******************  Constructor**********************/
	public Graph (String inputFileName) {
		Scanner scanner = null;
		int v = 0;
		try {
			scanner = new Scanner(new File(inputFileName));
		} 
		catch (FileNotFoundException e) {
			System.out.println("Error: Bad text file name.");
			e.printStackTrace();
		}
		try {
			nVertices = scanner.nextInt();
			adjList = new ArrayList[nVertices];
			for (int i = 0; i < nVertices; i++)
				adjList[i] = new ArrayList<EdgeNode>();
			while (scanner.hasNext()) {
				if (scanner.hasNextInt()) {
					v = scanner.nextInt();
					adjList[v].add(new EdgeNode(v, 
						scanner.nextInt(),
						scanner.nextInt()));
					nEdges++;
				}
				else
					scanner.next();
			}
			sortEdges();
		}
		catch (InputMismatchException e) {
			System.out.println("Error: Bad text file data.");
			e.printStackTrace();
		}
	}
  
	/******************Print graph method***************/
  
	public void printGraph() {  
		System.out.printf("Graph: nVertices = %d  nEdges = %d\n"
			+ "Adjancency Lists\n", nVertices, nEdges);
		for (int i = 0; i < nVertices; i++) {
			System.out.println("v= " + i + "  " + adjList[i]);
		}
	}  

	/******************* BFS Shortest paths  ******************/
	public SPPacket bfsShortestPaths (int start) {
		ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
		int[][] edgeLabels = new int[nVertices][nVertices];
		int[] vertexLabels = new int[nVertices];
		int[] distance = new int[nVertices];	// distance array
		int[] parent = new int[nVertices];		// parent array
		int i = 0, vertex1, vertex2;
		
		// Initialization
		Arrays.fill(vertexLabels, 0);
		for (int j = 0; j < nVertices; j++) {
			Arrays.fill(edgeLabels[j], 0);
		}
		Arrays.fill(distance, Integer.MAX_VALUE);
		Arrays.fill(parent, -1);
		
		list.add(new ArrayList<Integer>());		// create empty list L0
		vertexLabels[start]++;					// source vertex is explored
		distance[start] = 0;
		list.get(0).add(start);					// source vertex is added to L0
		
		// traverse graph
		while (!list.get(i).isEmpty()) {
			list.add(new ArrayList<Integer>());		//create empty list L(i+1)
			for (int j = 0; j < list.get(i).size(); j++) {	// for each v in Li
				vertex1 = list.get(i).get(j);
				distance[vertex1] = i;
				for (int k = 0; k < adjList[vertex1].size(); k++) {	// for each edge
					vertex2 = adjList[vertex1].get(k).vertex2;
					if (edgeLabels[vertex1][vertex2] == 0) {	// has edge(v, w) been explored?
						edgeLabels[vertex1][vertex2] = 1;			// if not, now it has
						edgeLabels[vertex2][vertex1] = 1;
						vertexLabels[vertex2]++;				// vertex w is explored too
						if (!list.get(i + 1).contains(vertex2)) {
							list.get(i + 1).add(vertex2);		// add vertex w to L(i + 1)
							parent[vertex2] = vertex1;
						}
					}
				}
			}
			i++;
		}
		return new SPPacket(start, distance, parent);
	} 
  
	/********************Dijkstra's Shortest Path Algorithm*** */

	public SPPacket  dijkstraShortestPaths (int start ) {
		ArrayList<Integer> adjVertices = new ArrayList<Integer>();
		boolean[] visited = new boolean[nVertices];
		int[] distance = new int[nVertices];
		int[] parent = new int[nVertices];
		int vertex1, vertex2, edgeWeight;
		
		// Initialization
		Arrays.fill(distance, Integer.MAX_VALUE);
		Arrays.fill(parent, -1);
		
		Arrays.fill(visited, 0, nVertices, false);
		distance[start] = 0;
		visited[start] = true;
		vertex1 = start;
		
		// traverse graph
		for (int i = 0; i < nVertices; i++) {// find adj vertex with smallest distance
			if (!adjList[i].isEmpty()) {	
				if (i > 0) {
					adjVertices = findAdjVertices(vertex1, visited);
					// move to the vertex with smallest distance
					vertex1 = closetVertex(adjVertices, distance);
				}
				// relax edges
				for (int j = 0; j < adjList[vertex1].size(); j++) {
					vertex2 = adjList[vertex1].get(j).vertex2;
					edgeWeight = adjList[vertex1].get(j).weight;
					if (distance[vertex1] + edgeWeight < distance[vertex2]) {
						distance[vertex2] = distance[vertex1] + edgeWeight;
						parent[vertex2] = vertex1;
					}
				}
			}
		}
		return new SPPacket(start, distance, parent);
	}
	
/********************Bellman Ford Shortest Paths ***************/
	public SPPacket bellmanFordShortestPaths(int start) {
		int[] distance = new int[nVertices];
		int[] parent = new int[nVertices];
		int vertex2, edgeWeight;
		
		// Initialization
		Arrays.fill(distance, Integer.MAX_VALUE);
		Arrays.fill(parent, -1);
		
		distance[start] = 0;
		
		// traverse graph
		for (int i = 0; i < nVertices; i++) {
			// relax edges
			for (int vertex1 = 0; vertex1 < nVertices; vertex1++) {
				for (int j = 0; j < adjList[vertex1].size(); j++) {
					vertex2 = adjList[vertex1].get(j).vertex2;
					edgeWeight = adjList[vertex1].get(j).weight;
					if (distance[vertex1] != Integer.MAX_VALUE &&
							distance[vertex1] + edgeWeight < distance[vertex2]) {
						distance[vertex2] = distance[vertex1] + edgeWeight;
						parent[vertex2] = vertex1;
					}
				}
			}
		}
		// check for negative cycles
		for (int vertex1 = 0; vertex1 < nVertices; vertex1++) {
			for (int j = 0; j < adjList[vertex1].size(); j++) {
				vertex2 = adjList[vertex1].get(j).vertex2;
				edgeWeight = adjList[vertex1].get(j).weight;
				if (distance[vertex1] != Integer.MAX_VALUE &&
						distance[vertex1] + edgeWeight < distance[vertex2]) {
					// there is a negative cycle
					return null;
				}
			}
		}
		return new SPPacket(start, distance, parent);
	} 

	/***********************Prints shortest paths*************************/
	public void printShortestPaths(SPPacket spp) {
		ArrayList<Integer> sp;
		int vertex2, vertex2Parent, spWeight = 0;
		System.out.printf("Shortest Paths from vertex %d to vertex\n", spp.getSource());
		for (int vertex1 = 0; vertex1 < nVertices; vertex1++) {
			if (spp.getDistance()[vertex1] != Integer.MAX_VALUE) {
				sp = new ArrayList<Integer>();				
				// add destination vertex to list
				sp.add(vertex1);							
				// start path with this vertex
				vertex2 = vertex1;							
				// loop until the source vertex is reached
				while (vertex2 != spp.getSource()) {
					// keep track of this vertex's parent
					vertex2Parent = spp.parent[vertex2];
					// add that parent to path (add its weight too)
					sp.add(vertex2Parent);
					spWeight += adjList[vertex2Parent].get(findEdgeIndex(vertex2Parent, vertex2)).weight;
					// move to the parent vertex and repeat
					vertex2 = spp.parent[vertex2];
				}
				Collections.reverse(sp);
				System.out.printf("%d:  %s  Path weight = %d\n", vertex1, sp.toString(), spWeight);
			}
			else	// the vertex was unreachable
				System.out.printf("%d:  []  Path weight = infinite\n", vertex1);
		}
	}
 
	/*****************isStronglyConnected***************************/
	public boolean isStronglyConnected() {
		// strong connectedness is determined here using
		// Floyd-Warshall algorithm
		int[][][] D = new int[nVertices + 1][nVertices + 1][nVertices + 1];
		int index, secondChoice;
		
		// initialize
		for (int i = 1; i < nVertices + 1; i++)
			for (int j = 1; j < nVertices + 1; j++) {
				if (i == j) {
					D[0][i][j] = 0;
				}
				else if ((index = findEdgeIndex(i - 1, j - 1)) != -1) {
					D[0][i][j] = adjList[i - 1].get(index).weight;
				}
				else {
					D[0][i][j] = Integer.MAX_VALUE;
				}
			}
		
		// fill in k matrices
		for (int k = 1; k < nVertices + 1; k++)
			for (int i = 1; i < nVertices + 1; i++)
				for (int j = 1; j < nVertices + 1; j++) {
					if (D[k - 1][i][k] == Integer.MAX_VALUE || 
							D[k - 1][k][j] == Integer.MAX_VALUE) {
						secondChoice = Integer.MAX_VALUE;
					}
					else secondChoice = D[k - 1][i][k] + D[k - 1][k][j];
					
					//System.out.printf("D[%d, %d, %d] = min {%d, %d}\n", k, i, j, 
					//	D[k - 1][i][j], secondChoice);
					D[k][i][j] = Math.min(
						D[k - 1][i][j], secondChoice);
				}
		
		// is it strongly connected?
		// if the algorithm shows that a vertex that can't be reached by
		// some other vertex, a path would be "infinite"
		for (int i = 1; i < nVertices + 1; i++) {
			for (int j = 1; j < nVertices + 1; j++) {
				if (D[nVertices][i][j] == Integer.MAX_VALUE) {
					return false;
				}
			}
		}
		// otherwise, it is strongly connected
		return true;
	}
	
	// returns the adjList[i] index for edge(i, j)
	private int findEdgeIndex(int i, int j) {
		if (adjList[i].isEmpty()) return -1;
		for (int w = 0; w < adjList[i].size(); w++) {
			if (adjList[i].get(w).vertex2 == j)
				return w;
		}
		return -1;
	}
	
	// makes list of vertices that are adjacent to v AND 
	// have not been visited
	private ArrayList<Integer> findAdjVertices (int v, boolean[] visited) {
		int u = -1;
		ArrayList<Integer> adjVertices = new ArrayList<Integer>();
		for (int i = 0; i < adjList[v].size(); i++) {
			u = adjList[v].get(i).vertex2;
			if (!visited[u]) {
				adjVertices.add(u);
			}
		}
		return adjVertices;
	}
	
	// returns the vertex with the smallest distance from a list of vertices
	private int closetVertex (ArrayList<Integer> adjVertices, int[] distance) {
		int closest = adjVertices.get(0);
		
		for (int i = 1; i < adjVertices.size(); i++) {
			if (adjVertices.get(i) < distance[closest]) {
				closest = adjVertices.get(i);
			}
		}
		return closest;
	}
	
	// insertion sorts each ArrayList in adjList by 
	// second vertex value
	private void sortEdges() {
		EdgeNode k = null;
		for (int v = 0; v < nVertices; v++)
			for (int i = 1; i < adjList[v].size(); i++)
				for (int j = i; j > 0; j--)
					if (adjList[v].get(j).vertex2 < adjList[v].get(j - 1).vertex2) {
						k = adjList[v].get(j);
						adjList[v].set(j, adjList[v].get(j - 1));
						adjList[v].set(j - 1, k);
					}
	}
}	// End Graph class

// Place the EdgeNode class and the SPPacket class inside the Graph.java file
/*******************************************/
class EdgeNode 
{
   int vertex1;
   int vertex2;
   int weight;
   
   public EdgeNode ( int v1, int v2, int w)
   { vertex1 = v1; vertex2 = v2; weight = w; }
   
   public String toString() {
		return "(" + vertex1 + "," + vertex2 + "," + weight + ")";  
	}
}

/***********************************************/
class SPPacket
{
	int[] d;    	//distance array
	int[] parent;   //parent path array
	int source; 	//source vertex
     
	public SPPacket(int start, int[] dist, int[] pp) {
		d = Arrays.copyOf(dist, dist.length);
		parent = Arrays.copyOf(pp, pp.length);
		source = start;
	}
     
	public int[] getDistance() {
		return d;	
	}
     
	public int[] getParent() {
		return parent;	
	}
   
	public int getSource() {
		return source;	
	}
      
	public String toString() {
		return source + "\n" + Arrays.toString(d) + "\n" + Arrays.toString(parent) + "\n";
	}
}