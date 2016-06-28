package business.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import beans.City;
import beans.EdgeHeap;
import beans.Graph;
import beans.WeightedEdge;

public class GraphBrowser {

	private Graph graph;
	private BufferedWriter serverCommand;
	private HashMap<Integer,City> cityList;

	public GraphBrowser(Graph graph, BufferedWriter serverCommand,HashMap<Integer,City> cityList) {
		this.graph = graph;
		this.serverCommand = serverCommand;
		this.cityList = cityList;
		// TODO print to client
	}

	public void findByTiefenSucheRekursiv(Graph g, int von, int nach) {

		boolean[] visited = new boolean[g.numVertices()];
		int[] pred = new int[g.numVertices()];
		List<Integer> flow = new ArrayList<Integer>();

		_findByTiefenSucheRekursiv(g, von, nach, visited, pred, flow);

		System.out.println(flow);
		writeFlow(flow,"Tiefensuche :");
	}

	private void writeFlow(List<Integer> flow,String methode) {
		try {
			serverCommand.write(methode);
			for (Integer integer : flow) {
				serverCommand.write(integer.toString());
			}
			serverCommand.flush();
			serverCommand.newLine();
		} catch (Exception e) {

		}
		writeCity(flow);
	}
	private void writeCity(List<Integer> flow) {
		try {
			serverCommand.write("");
			for (Integer integer : flow) {
			serverCommand.write(cityList.get(integer).getName()+"-");
			}
			serverCommand.flush();
			serverCommand.newLine();
		} catch (Exception e) {

		}
	}


	private boolean _findByTiefenSucheRekursiv(Graph g, int current, int nach, boolean[] visited, int[] pred,
			List<Integer> flow) {
		visited[current] = true;
		flow.add(current);
		if (current == nach) {
			return true;
		}

		List<WeightedEdge> nachbarn = g.getEdges(current);
		for (WeightedEdge n : nachbarn) {
			if (!visited[n.vertexID]) {
				pred[n.vertexID] = current;

				boolean found = _findByTiefenSucheRekursiv(g, n.vertexID, nach, visited, pred, flow);
				if (found)
					return true;

			}
		}
		return false;
	}

	public void dijkstraDichteGraphen(Graph g, int von, int nach) {
		// Oliver: funktioniert
		System.out.println(g.numVertices());
		int[] pred = new int[g.numVertices()];
		int[] dist = new int[g.numVertices()];
		boolean[] visited = new boolean[g.numVertices()];
		List<Integer> flow = new ArrayList<Integer>();

		for (int i = 0; i < dist.length; i++) {
			dist[i] = 99999;
			pred[i] = -1;
		}
		dist[von] = 0;

		while (true) {
			int curIndex = nextVertex(dist, visited);
			if (curIndex == -1)
				break;

			visited[curIndex] = true;

			List<WeightedEdge> nachbarn = g.getEdges(curIndex);
			for (WeightedEdge nachbar : nachbarn) {
				int distBisHier = dist[curIndex];
				int distZumNachbar = nachbar.weight;

				int distInsg = distBisHier + distZumNachbar;

				if (dist[nachbar.vertexID] > distInsg) {
					dist[nachbar.vertexID] = distInsg;
					pred[nachbar.vertexID] = curIndex;

				}
			}
		}

		List<Integer> way = predToWay(pred, von, nach);
		for (int vertexNumber : way) {
			flow.add(vertexNumber);
			// Way ausgeben
			// System.out.print(vertexNumber + " ");
		}
		System.out.println(flow);
		writeFlow(flow,"Dijkstra :");
		// return flow;
	}

	private int nextVertex(int[] dist, boolean[] visited) {

		int minValue = 99999;
		int minIndex = -1;

		for (int i = 0; i < dist.length; i++) {
			if (!visited[i] && dist[i] < minValue) {
				minValue = dist[i];
				minIndex = i;
			}
		}
		return minIndex;
	}

	private List<Integer> predToWay(int[] pred, int from, int too) {

		List<Integer> way = new ArrayList<Integer>();

		int i = too;
		while (i != from) {
			way.add(0, i);
			i = pred[i];
		}
		way.add(0, from);

		return way;
	}

	public void dijkstraLichteGraphen(Graph g, int von, int nach) {

		int[] pred = new int[g.numVertices()];
		int[] dist = new int[g.numVertices()];
		boolean[] visited = new boolean[g.numVertices()];
		List<Integer> flow = new ArrayList<Integer>();

		EdgeHeap heap = new EdgeHeap(g.numVertices());
		for (int i = 0; i < dist.length; i++) {
			dist[i] = 99999;
			heap.insert(new WeightedEdge(i, 99999));
			pred[i] = -1;
		}

		dist[von] = 0;
		heap.setPriority(von, 0);

		while (!heap.isEmpty()) {

			WeightedEdge cur = heap.remove();

			if (cur.vertexID == nach)
				break;

			List<WeightedEdge> nachbarn = g.getEdges(cur.vertexID);
			for (WeightedEdge nachbar : nachbarn) {

				int distBisHier = dist[cur.vertexID]; // Alt: cur.weight
				int distZumNachbar = nachbar.weight;

				int distInsg = distBisHier + distZumNachbar;

				if (distInsg < dist[nachbar.vertexID]) {

					dist[nachbar.vertexID] = distInsg;
					heap.setPriority(nachbar.vertexID, distInsg);

					pred[nachbar.vertexID] = cur.vertexID;
				}
			}
		}

		List<Integer> way = predToWay(pred, von, nach);
		for (int vertexNumber : way) {
			flow.add(vertexNumber);

		}
		System.out.println(flow);
		writeFlow(flow,"Dijkstra :");
		// return flow;
	}

	public void findByBreitenSuche(Graph g, int von, int nach) {
		// Oliver: funktioniert
		List<Integer> flow = new ArrayList<Integer>();
		boolean[] visited = new boolean[g.numVertices()];
		boolean found = false;
		found = _findByBreitenSuche(g, von, nach, flow, visited, found);
		if (found) {
			System.out.println(flow);
			writeFlow(flow,"Breitensuche :");
		} else {// oder eben als Return!
			System.out.println(flow + ": Keine Verbindung gefunden");
			writeFlow(flow,"Breitensuche :");
		}
		// return flow;
	}

	private boolean _findByBreitenSuche(Graph g, int current, int nach, List<Integer> flow, boolean[] visited,
			boolean found) {
		// Oliver: funktioniert
		ArrayDeque<Integer> nodes = new ArrayDeque<Integer>();
		List<WeightedEdge> nachbarn;
		int[] pred = new int[g.numVertices()];

		nodes.add(current);
		// flow.add(current);
		visited[current] = true;
		// outer:
		while ((!nodes.isEmpty()) && ((current = nodes.poll()) > 0)) {
			flow.add(current);
			if (current == nach) {
				found = true;
				break;
			}
			nachbarn = g.getEdges(current);
			for (WeightedEdge nachbar : nachbarn) {
				if (!visited[nachbar.vertexID]) {
					nodes.add(nachbar.vertexID);
					pred[nachbar.vertexID] = current;
					visited[nachbar.vertexID] = true;
				}
			}
		}
		return found;
	}

}
