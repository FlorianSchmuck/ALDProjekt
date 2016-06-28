package beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import business.utilities.CompareWeightedEdge;


public class ArrayGraph extends Graph {

	private int[][] graph;
	private int numVertices;
	private boolean directed;
	
	public ArrayGraph(int numVertices, boolean directed) {
		numVertices++;
		graph = new int[numVertices][numVertices];
		this.numVertices = numVertices;
		this.directed = directed;
	}
	
	public int numVertices() {
		return numVertices;
	}

	public boolean hasEdge(int source, int destination) {
		return (graph[source][destination] > 0);
	}
	
	public int getEdgeWeight(int source, int destination) {
		return graph[source][destination];
	}
	
	public void addEdge(int source, int destination) {
		addEdge(source, destination, 1);
	}
	
	public void addEdge(int source, int destination, int weight) {
		graph[source][destination] = weight;
		if (!directed)
			graph[destination][source] = weight;
	}
	
	public void debugPrint()
	{
		for(int i =0; i<numVertices;i++)
		{
			for(int j =0; j<numVertices;j++)
				System.out.println("From " + i + " to " + j+":"+graph[i][j]);
		}
	}
	
	public void removeEdge(int source, int destination) {
		graph[source][destination] = 0;
		if (!directed)
			graph[destination][source] = 0;
	}
	
	public List<WeightedEdge> getEdges(int vertexID) {
		ArrayList<WeightedEdge> edges = new ArrayList<WeightedEdge>();
		for (int i=0; i < numVertices; i++) {
			if (graph[vertexID][i] > 0) {
				edges.add(new WeightedEdge(i, graph[vertexID][i]));
			}
		Collections.sort(edges,new CompareWeightedEdge());
		}
		return edges;
	}
}