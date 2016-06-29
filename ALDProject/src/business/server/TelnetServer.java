package business.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import beans.ArrayGraph;
import beans.City;
import beans.Graph;
import beans.ListGraph;
import beans.Node;
import beans.SearchCriteria;
import beans.Street;
import beans.VertexTree;
import business.client.WorkerThread;
import business.utilities.IOAccessLayer;

public class TelnetServer extends AbstractBasicServer {

	private File cityFile, streetsFile;
	private IOAccessLayer theIOAccessLayerInstance;
	private List<Street> streetList;
	private HashMap<Integer,City> cityList = new HashMap();
	private BufferedWriter serverCommand;
	private boolean isServerActiv = true;
	public static boolean isClientActiv;
	private VertexTree vertexTree;
	private Graph listGraph,arrayGraph;
	private GraphBrowser listGraphBrowser, arrayGraphBrowser;
	private String path;
	private List<Integer> flow = new ArrayList<Integer>();

	public static void main(String[] args) {
			try {
				new TelnetServer();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public TelnetServer() throws Exception {
		super.initializeServer();
		initializeTelnetServer();
		if (isChooseFromFileSystemEnabled) {
			theIOAccessLayerInstance.chooseFileFromFileSystem();
		}
		startServerRoutine();
		
	}

	private void initializeTelnetServer() throws IOException {
		vertexTree = new VertexTree<City>();
		theIOAccessLayerInstance = IOAccessLayer.getTheInstance();
		cityFile = super.getFileFromRessource("citys.txt");
		streetsFile = super.getFileFromRessource("streets.txt");
		streetList = theIOAccessLayerInstance.readStreetsFile(streetsFile);
		cityList = theIOAccessLayerInstance.readCityFile(cityFile);
		addToTree();
		listGraph = new ListGraph(cityList.size(),false);	//licht
		arrayGraph = new ArrayGraph(cityList.size(),false); //dicht
		addToGraph(listGraph);
		addToGraph(arrayGraph);
	}
	
	private void addToGraph(Graph graph){
		for (Street s : streetList) {
			graph.addEdge(s.getSource_id(), s.getDestination_id(), s.getDistance());
		}
	}
	
	
	private void addToTree(){
		for(City city: cityList.values()) {
			vertexTree.add(city.getName(), city.getId());
		}
	}

	private void startServerShell(Socket clientSocket) throws Exception {
		serverCommand = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
		serverCommand.write("Willkommen am Server ... bitte um Ihre Eingabe");
		serverCommand.newLine();
		serverCommand.write("Gestalten Sie die Eingabe wie folgt:");
		serverCommand.newLine();
		serverCommand.write("Startort ; Zielort ; Suchkriterium(Breitensuche,Tiefensuche,Dijkstra)");
		serverCommand.newLine();
		serverCommand.write("Beenden Sie die Session mit exit");
		serverCommand.newLine();
		serverCommand.flush();
	}
	
	public void responseToClient(Socket clientSocket,String message) throws Exception {
		BufferedReader clientResponse = new BufferedReader(
				new InputStreamReader(clientSocket.getInputStream()));
		String from,to,searchcriteria;
		String[] clientRequest = message.split(BasicServer.fileSeparator);
		from = clientRequest[0];
		to = clientRequest[1];
		searchcriteria = clientRequest[2];
		listGraphBrowser = new GraphBrowser(listGraph, serverCommand,cityList);
		arrayGraphBrowser = new GraphBrowser(arrayGraph, serverCommand,cityList);
		
		Node<City> temp = vertexTree.find(from);
		int fromId = searchNode(from);
		int toId = searchNode(to);
		if(fromId <=0 || toId <= 0)
			return;
		if (SearchCriteria.BREITENSUCHE.toString().equals(searchcriteria.toUpperCase())) {

			listGraphBrowser.findByBreitenSuche(listGraph, fromId, toId);
			//arrayGraphBrowser.findByBreitenSuche(arrayGraph, fromId, toId);
			path = SearchCriteria.BREITENSUCHE.toString();
			System.out.println(path);
		}
		else if (SearchCriteria.TIEFENSUCHE.toString().equals(searchcriteria.toUpperCase())) 
		{
			listGraphBrowser.findByTiefenSucheRekursiv(listGraph, fromId, toId);
			//arrayGraphBrowser.findByTiefenSucheRekursiv(arrayGraph, fromId, toId);
			path = SearchCriteria.TIEFENSUCHE.toString();
			System.out.println(path);
		}
		else if (SearchCriteria.DIJKSTRA.toString().equals(searchcriteria.toUpperCase())){
			//listGraphBrowser.dijkstra(listGraph, fromId, toId); - Dichte Graphen
			System.out.println(fromId + " to " + toId);
			arrayGraphBrowser.dijkstra(arrayGraph, fromId, toId);
			path = SearchCriteria.DIJKSTRA.toString();
			System.out.println(path);
		}		
		serverCommand.newLine();
		serverCommand.flush();
	}
	
	@Override
	public void startServerRoutine() throws Exception {
		while (isServerActiv == true) {
			Socket clientSocket = super.serverSocket.accept();
			startServerShell(clientSocket);
			WorkerThread workerThread = new WorkerThread(clientSocket, this);
			Thread clientThread = new Thread(workerThread);
			clientThread.start();
			
			while (isClientActiv == true) {
				responseToClient(clientSocket,"");
			}
		}
	}
	private int searchNode(String searchstring) throws IOException{
		Node<City> temp = vertexTree.find(searchstring);
		if(temp != null){
			return temp.getId();
		}
		else{
			serverCommand.write("Not found:" + searchstring );
			serverCommand.flush();
			return 0;
		}
	}
	
}
