package business.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import basic.Street;
import business.client.WorkerThread;
import business.utilities.IOAccessLayer;

public class TelnetServer extends BasicServerImpl {

	private File cityFile, streetsFile;
	private IOAccessLayer theIOAccessLayerInstance;
	// Maybe LinkedList??
	private ArrayList<Street> streetGraphList;
	private BufferedWriter serverCommand;

	public static void main(String[] args) throws Exception {
		try {
			new TelnetServer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("This is error!");
		}
	}
	
	public TelnetServer() throws Exception {
		super.initializeServer();
		initializeTelnetServer();
		readStreetsAndAddToGraph();
	}

	private void initializeTelnetServer() throws IOException {
		streetGraphList = new ArrayList<>();
		theIOAccessLayerInstance = IOAccessLayer.getTheInstance();
		cityFile = super.getFileFromRessource("citys.txt");
		streetsFile = super.getFileFromRessource("streets.txt");
	}

	private void readStreetsAndAddToGraph() throws Exception {
		List<String> streetsFromFile = theIOAccessLayerInstance.readFile(new FileReader(streetsFile));
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
		serverCommand.write("Startort ; Zielort ; Suchkritärium");
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
		while (super.isServerActiv()) {
			Socket clientSocket = super.serverSocket.accept();
			switchToClientActiv();
			WorkerThread workerThread = new WorkerThread();
			Thread clientThread = new Thread(workerThread);
			clientThread.start();
			startServerShell(clientSocket);

			while (isClientActiv()) {
				responseToClient(clientSocket);
			}
		}
	}
}
