package beans;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import business.server.BasicServer;
import business.utilities.CompareWeightedEdge;
import business.utilities.IOAccessLayer;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		

		// 0: Graz
		// 1: Wien
		// 2: Klagenfurt
		// 3: Salzburg
		// 4: Inschbruck
		// 5: St. Pölten
		// 6: Linz
		IOAccessLayer acl;
		Graph g = null;
		try {
			acl = IOAccessLayer.getTheInstance();
			List<Street> streets = acl.readStreetsFile(new File(BasicServer.ressourcePath+"streets.txt"));			
			List<City> cities = acl.readCityFile(new File(BasicServer.ressourcePath+"citys.txt"));
			g = new ListGraph(streets.size(), true);
			for(Street s : streets)
			{
			g.addEdge(s.getSource_id(),s.getDestination_id(),s.getDistance());	
			}
			//g.debugPrint();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		findByTiefenSucheRekursiv(g, 1, 8);

		// findByTiefenSuche(g, 0, 4);
		// findByBreitenSuche(g, 0, 4);
		// dijkstra(g, 0, 4);

	}

	private static void findByTiefenSucheRekursiv(Graph g, int von, int nach) {
		boolean[] visited = new boolean[g.numVertices()];
		int[] pred = new int[g.numVertices()];
		ArrayList<Integer> flow = new ArrayList<Integer>();

		// pred[5] = 0
		// Wir besuchen 5 über 0

		_findByTiefenSucheRekursiv(g, von, nach, visited, pred,flow);
		/*for (int i = 0; i < pred.length; i++) {
			if(visited[i])
			System.out.println("from "+ pred[i]+" to " + i );
		}
		*/
		System.out.println(flow);
	}

	private static boolean _findByTiefenSucheRekursiv(Graph g, int current, int nach, boolean[] visited, int[] pred, ArrayList<Integer> flow) {
		visited[current]=true;
		flow.add(current);
		if (current == nach)
		{
			return true;
		}

		List<WeightedEdge> nachbarn = g.getEdges(current);
		for (WeightedEdge n : nachbarn) {
			if (!visited[n.vertexID]) {
				pred[n.vertexID] = current;

				boolean found = _findByTiefenSucheRekursiv(g, n.vertexID, nach, visited, pred,flow);
				if (found)
					return true;

			}
		}
		return false;
	}

	@SuppressWarnings("unused")
	private static void findByTiefenSuche(Graph g, int von, int nach) {

		Stack<Integer> nodes = new Stack<Integer>();

		boolean[] visited = new boolean[g.numVertices()];
		int[] pred = new int[g.numVertices()];
		boolean found = false;

		for (int i = 0; i < pred.length; i++) {
			pred[i] = -1;
		}

		nodes.push(von);

		while (!nodes.isEmpty()) {

			int current = nodes.pop();
			visited[current] = true;

			if (current == nach) {
				found = true;
				break;
			}

			List<WeightedEdge> nachbarn = g.getEdges(current);
			for (WeightedEdge nachbar : nachbarn) {
				if (!visited[nachbar.vertexID]) {
					pred[nachbar.vertexID] = current;
					nodes.push(nachbar.vertexID);
				}
			}
		}

		if (found) {
			// Route ausgeben
			for (int i = 0; i < pred.length; i++) {
				if(visited[i])
				System.out.println(i + " über " + pred[i]);
			}
		} else {
			System.out.println("Keine Verbindung gefunden");
		}

	}

	private static int nextVertex(int[] dist, boolean[] visited) {

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

	// Variante ohne Heap für dichte Graphen
	private static void dijkstra2(Graph g, int von, int nach) {

		int[] pred = new int[g.numVertices()];
		int[] dist = new int[g.numVertices()];
		boolean[] visited = new boolean[g.numVertices()];

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

		// Pred ausgeben
		for (int i = 0; i < pred.length; i++) {
			System.out.println(i + " über " + pred[i]);
		}

		// Way ausgeben
		System.out.println();
		ArrayList<Integer> way = predToWay(pred, von, nach);
		for (int vertexNumber : way) {
			System.out.print(vertexNumber + " ");
		}
		System.out.println();
	}

	private static ArrayList<Integer> predToWay(int[] pred, int from, int to) {

		ArrayList<Integer> way = new ArrayList<Integer>();

		int i = to;
		while (i != from) {
			way.add(0, i);
			i = pred[i];
		}
		way.add(0, from);

		return way;
	}

	// Variante mit Heap für lichte Graphen
	private static void dijkstra(Graph g, int von, int nach) {

		int[] pred = new int[g.numVertices()];
		int[] dist = new int[g.numVertices()];
		boolean[] visited = new boolean[g.numVertices()];

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

		// pred ausgeben
		for (int i = 0; i < pred.length; i++) {
			System.out.println(i + " über " + pred[i]);
		}

		// Way ausgeben
		System.out.println();
		ArrayList<Integer> way = predToWay(pred, von, nach);
		for (int vertexNumber : way) {
			System.out.print(vertexNumber + " ");
		}
		System.out.println();

	}

	private static void findByBreitenSuche(Graph g, int von, int nach) {

		ArrayDeque<Integer> nodes = new ArrayDeque<Integer>();

		boolean[] visited = new boolean[g.numVertices()];
		int[] pred = new int[g.numVertices()];
		boolean found = false;

		for (int i = 0; i < pred.length; i++) {
			pred[i] = -1;
		}

		nodes.add(von);

		outer: while (!nodes.isEmpty()) {

			int current = nodes.poll();
			visited[current] = true;

			List<WeightedEdge> nachbarn = g.getEdges(current);
			for (WeightedEdge nachbar : nachbarn) {
				if (!visited[nachbar.vertexID]) {
					nodes.add(nachbar.vertexID);
					pred[nachbar.vertexID] = current;

					if (nachbar.vertexID == nach) {
						found = true;
						break outer;
					}
				}
			}
		}

		if (found) {
			// Route ausgeben
			for (int i = 0; i < pred.length; i++) {
				System.out.println(i + " über " + pred[i]);
			}
		} else {
			System.out.println("Keine Verbindung gefunden");
		}

	}

}
