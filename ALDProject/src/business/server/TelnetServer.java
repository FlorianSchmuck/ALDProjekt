package business.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import beans.Street;
import business.client.WorkerThread;
import business.utilities.IOAccessLayer;

public class TelnetServer extends AbstractBasicServer {

	private File cityFile, streetsFile;
	private IOAccessLayer theIOAccessLayerInstance;
	// Maybe LinkedList??
	private ArrayList<Street> streetGraphList;
	private BufferedWriter serverCommand;
	private boolean isServerActiv;
	private boolean isClientActiv;

	public static void main(String[] args) {
			try {
				new TelnetServer();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public TelnetServer() throws Exception {
		super.initializeServer();
		switchToServerActiv();
		initializeTelnetServer();
		if (isChooseFromFileSystemEnabled) {
			theIOAccessLayerInstance.chooseFileFromFileSystem();
		}
		readStreetsAndAddToGraph();
		startServerRoutine();
		
	}

	private void initializeTelnetServer() throws IOException {
		streetGraphList = new ArrayList<>();
		theIOAccessLayerInstance = IOAccessLayer.getTheInstance();
		cityFile = super.getFileFromRessource("citys.txt");
		streetsFile = super.getFileFromRessource("streets.txt");
	}

	private void readStreetsAndAddToGraph() throws Exception {
		List<String> streetsFromFile = theIOAccessLayerInstance.readFile(streetsFile);
		for (int i = 0; i < streetsFromFile.size(); i++) {
			System.out.println(streetsFromFile.get(i));
			// TODO street input -> FH Meeting
			// streetGraphList.add(new Street())
		}
	}

	private void startServerShell(Socket clientSocket) throws Exception {
		serverCommand = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
		serverCommand.write("Willkommen am Server ... bitte um Ihre Eingabe");
		serverCommand.newLine();
		serverCommand.write("Gestalten Sie die Eingabe wie folgt:");
		serverCommand.newLine();
		serverCommand.write("Startort ; Zielort ; Suchkriterium");
		serverCommand.newLine();
		serverCommand.flush();
	}
	
	private void responseToClient(Socket clientSocket) throws Exception {
		BufferedReader ClientResponse = new BufferedReader(
				new InputStreamReader(clientSocket.getInputStream()));
		String[] ClientRequest = ClientResponse.readLine().split(";");
		for (Street street : streetGraphList) {
			if (street.getSource_id() == Integer.parseInt(ClientRequest[0])) {
				serverCommand.write(street.toString());
				serverCommand.flush();
			}
		}
	}
	
	@Override
	public void startServerRoutine() throws Exception {
		while (isServerActiv()) {
			Socket clientSocket = super.serverSocket.accept();
			//TODO new logic -> now: every iteration creates a new Thread
			startServerShell(clientSocket);
			WorkerThread workerThread = new WorkerThread(clientSocket,serverCommand);
			Thread clientThread = new Thread(workerThread);
			clientThread.start();

			while (isClientActiv()) {
				responseToClient(clientSocket);
			}
		}
	}
	
	public void switchToClientActiv() {
		isClientActiv = true;
		isServerActiv = false;
	}
	
	public void switchToServerActiv() {
		isClientActiv = false;
		isServerActiv = true;
	}
	
	public boolean isClientActiv() {
		return isClientActiv;
	}
	
	public boolean isServerActiv() {
		return isServerActiv;
	}
}
