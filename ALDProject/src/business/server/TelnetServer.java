package business.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import beans.SearchCriteria;
import beans.Street;
import business.client.WorkerThread;
import business.utilities.IOAccessLayer;

public class TelnetServer extends AbstractBasicServer {

	private File cityFile, streetsFile;
	private IOAccessLayer theIOAccessLayerInstance;
	private ArrayList<Street> streetList;
	private BufferedWriter serverCommand;
	private boolean isServerActiv = true;
	public static boolean isClientActiv;

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
		streetList = new ArrayList<>();
		theIOAccessLayerInstance = IOAccessLayer.getTheInstance();
		cityFile = super.getFileFromRessource("citys.txt");
		streetsFile = super.getFileFromRessource("streets.txt");
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
	
	public String responseToClient(Socket clientSocket,String message) throws Exception {
		BufferedReader clientResponse = new BufferedReader(
				new InputStreamReader(clientSocket.getInputStream()));
		String line;
		String[] clientRequest = message.split(BasicServer.fileSeparator);
		System.out.println(clientRequest[2]);
		//GraphBrowser graphBrowser = new GraphBrowser(null, null)
		if (SearchCriteria.BREITENSUCHE.toString().equals(clientRequest[2].toUpperCase())) {
			//TODO start breitensuche routine
			//graphBrowser.findByBreitenSuche(g, von, nach);
			System.out.println(SearchCriteria.BREITENSUCHE.toString());
		}
		else if (SearchCriteria.TIEFENSUCHE.toString().equals(clientRequest[2].toUpperCase())) 
		{
			//TODO start tiefensuche routine
			//graphBrowser.findByTiefenSucheRekursiv(g, von, nach);
			System.out.println(SearchCriteria.TIEFENSUCHE.toString());
		}
		else if (SearchCriteria.DIJKSTRA.toString().equals(clientRequest[2].toUpperCase())){
			//TODO start dijkstra routine
			//graphBrowser.dijkstraLichteGraphen(g, von, nach);(g, von, nach);
			//graphBrowser.dijkstraDichteGraphen(g, von, nach);
			System.out.println(SearchCriteria.DIJKSTRA.toString());
		}
		
		return "Mario is Fancy";			// serverlogic implementieren
			
		
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
	
}
