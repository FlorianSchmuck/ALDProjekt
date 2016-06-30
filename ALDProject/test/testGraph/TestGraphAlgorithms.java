package testGraph;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import beans.City;
import beans.Street;
import beans.graph.ArrayGraph;
import beans.graph.Graph;
import beans.graph.ListGraph;
import beans.tree.Node;
import beans.tree.VertexTree;
import business.server.BasicServer;
import business.server.GraphBrowser;
import business.utilities.IOAccessLayer;
import junit.framework.TestCase;

public class TestGraphAlgorithms extends TestCase {

	private HashMap<Integer, City> cityMap = new HashMap();
	private HashMap<Integer, City> testMap = new HashMap();
	private IOAccessLayer theInstance;
	private List<Street> streetList,streetsDirectedList;
	private VertexTree vertexTree;
	private Graph listGraph, arrayGraph,directedListGraph,directedArrayGraph;
	private GraphBrowser listGraphBrowser, arrayGraphBrowser;


	// assigning the values
	protected void setUp() throws IOException {
		vertexTree = new VertexTree<City>();
		theInstance = IOAccessLayer.getTheInstance();
		cityMap = theInstance.readCityFile(new File(BasicServer.ressourcePath + "citys.txt"));
		testMap = theInstance.readCityFile(new File(BasicServer.ressourcePath + "citys.txt"));
		streetList = theInstance.readStreetsFile(new File(BasicServer.ressourcePath + "streets.txt"));
		streetsDirectedList = theInstance.readStreetsFile(new File(BasicServer.ressourcePath + "streets_directed.txt"));
		addToTree();
		listGraph = new ListGraph(cityMap.size(), false); // licht
		arrayGraph = new ArrayGraph(cityMap.size(), false); // dicht
		directedListGraph = new ListGraph(cityMap.size(), true);
		directedArrayGraph = new ArrayGraph(cityMap.size(), true);
		addToGraph(listGraph,false);
		addToGraph(arrayGraph,false);
		addToGraph(directedListGraph,true);
		addToGraph(directedArrayGraph,true);
		listGraphBrowser = new GraphBrowser(listGraph, null, cityMap);
		arrayGraphBrowser = new GraphBrowser(arrayGraph, null, cityMap);
	}

	private void addToTree() {
		for (City city : cityMap.values()) {
			vertexTree.add(city.getName(), city.getId());
		}
	}

	private void addToGraph(Graph graph,boolean directed) {
		if (directed){
			for (Street s : streetsDirectedList) {
				graph.addEdge(s.getSource_id(), s.getDestination_id(), s.getDistance());
			}
		}
		else {
			for (Street s : streetList) {
				graph.addEdge(s.getSource_id(), s.getDestination_id(), s.getDistance());
			}
		}
	}


	private int searchNode(String searchstring) {
		try {
			Node<City> temp = vertexTree.find(searchstring);
			if (temp != null) {
				return temp.getId();
			} else {
				System.out.println("ERROR");
				return 0;
			}
		} catch (Exception e) {
			return 0;
		}
	}

	// test Method
	@Test
	public void testFindInGraph() throws IOException {
		setUp();
		int fromId, toId;

		for (City city : cityMap.values()) {
			fromId = searchNode(city.getName());
			for (City testCity : testMap.values()) {
				toId = searchNode(testCity.getName());
				System.out.println(city.getName() +" - "+ testCity.getName());
				System.out.println("UNDIRECTED");
				listGraphBrowser.dijkstra(listGraph, fromId, toId);
				arrayGraphBrowser.dijkstra(listGraph, fromId, toId);
				listGraphBrowser.findByBreitenSuche(listGraph, fromId, toId);
				arrayGraphBrowser.findByBreitenSuche(arrayGraph, fromId, toId);
				listGraphBrowser.findByTiefenSucheRekursiv(listGraph, fromId, toId);
				arrayGraphBrowser.findByTiefenSucheRekursiv(arrayGraph,fromId, toId);
				//directed
				System.out.println("DIRECTED");
				listGraphBrowser.dijkstra(directedListGraph, fromId, toId);
				arrayGraphBrowser.dijkstra(directedArrayGraph, fromId, toId);
				listGraphBrowser.findByBreitenSuche(directedListGraph, fromId,toId);
				arrayGraphBrowser.findByBreitenSuche(directedArrayGraph, fromId, toId);
				listGraphBrowser.findByTiefenSucheRekursiv(directedListGraph, fromId, toId);
				arrayGraphBrowser.findByTiefenSucheRekursiv(directedArrayGraph, fromId, toId);
			}
		}
	}
}
