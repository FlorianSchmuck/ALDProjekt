package beans;

import java.util.List;

public class PrimMinimumSpanningTree {
	
	public int[] findMST(Graph g) {
		int numv = g.numVertices();
		int[] pred = new int[numv];
		int[] weight = new int[numv];
		for (int i=0; i < numv; i++) {
			weight[i] = 9999;		// Gewichte im Graph dürfen nie größer sein
			pred[i] = -1;
		}
		weight[0] = 0;		// Start bei beliebigem Knoten
		
		EdgeHeap eheap = new EdgeHeap(g.numVertices());
		for (int i=0; i < g.numVertices(); i++)
			eheap.insert(new WeightedEdge(i, weight[i]));

		while (!eheap.isEmpty()) {
			int u = eheap.remove().vertexID;
			List<WeightedEdge> le = g.getEdges(u);
			for (int i=0; i < le.size(); i++) {
				WeightedEdge we = le.get(i);
				int v = we.vertexID;
				if (eheap.contains(we)) {
					if (we.weight < weight[v]) {
						weight[v] = we.weight;
						pred[v] = u;
						eheap.setPriority(v, we.weight);
					}
				}
			}
		}
		return pred;
	}
}