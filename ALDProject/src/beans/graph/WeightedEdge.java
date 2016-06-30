package beans.graph;

public class WeightedEdge {
	public int vertexID;
	public int weight;

	public WeightedEdge(int vertexID, int weight) {
		//vertexID = destinationID
		this.vertexID = vertexID;
		this.weight = weight;
	}
}
