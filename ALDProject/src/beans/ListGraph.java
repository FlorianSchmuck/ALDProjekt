package beans;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import business.utilities.CompareWeightedEdge;


public class ListGraph extends Graph {

	private ArrayList<WeightedEdge>[] graph;
	private int numVertices;
	private boolean directed;
	
	@SuppressWarnings("unchecked")
	public ListGraph(int numVertices, boolean directed) {
		numVertices++;
		graph = new ArrayList[numVertices];
		for (int i=0; i < numVertices; i++)
			graph[i] = new ArrayList<WeightedEdge>();
		this.numVertices = numVertices;
		this.directed = directed;
	}
	
	public int numVertices() {
		return numVertices;
	}

	private WeightedEdge findEdge(int source, int destination) {
		for (int i=0; i < graph[source].size(); i++) {
			if (graph[source].get(i).vertexID == destination)
				return graph[source].get(i);
		}
		return null;
	}
	
	public boolean hasEdge(int source, int destination) {
		WeightedEdge pe = findEdge(source, destination);
		return pe != null;
	}
	
	public int getEdgeWeight(int source, int destination) {
		WeightedEdge pv = findEdge(source, destination);
		return pv.weight;
	}

	public void addEdge(int source, int destination) {
		addEdge(source, destination, 1);
	}
	
	public void addEdge(int source, int destination, int weight) {
		WeightedEdge pv = new WeightedEdge(destination, weight);
		graph[source].add(pv);
		if (directed) {
			pv = new WeightedEdge(source, weight);
			graph[destination].add(pv);
		}
	}
	public void debugPrint()
	{
		for(int i =0; i<graph.length;i++)
		{
			for(WeightedEdge e: graph[i])
				System.out.println("From " + i + " to " + e.vertexID+":"+e.weight);
		}
	}
	


	public void removeEdge(int source, int destination) {
		WeightedEdge pv = findEdge(source, destination);
		graph[source].remove(pv);
		if (!directed) {
			pv = findEdge(destination, source);
			graph[source].remove(pv);
		}
	}

	public List<WeightedEdge> getEdges(int vertexID) {
		List<WeightedEdge> li = graph[vertexID];
		Collections.sort(li, new CompareWeightedEdge());
		 return li;
	}
}