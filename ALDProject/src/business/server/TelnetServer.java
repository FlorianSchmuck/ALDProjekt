package business.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import beans.Street;
import business.client.WorkerThread;
import business.utilities.IOAccessLayer;

public class TelnetServer extends AbstractBasicServer {

	private File cityFile, streetsFile;
	private IOAccessLayer theIOAccessLayerInstance;
	// Maybe LinkedList??
	private ArrayList<Street> streetList;
	private BufferedWriter serverCommand;
	private boolean isServerActiv = true;
	public static boolean isClientActiv;

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
		serverCommand.write("Startort ; Zielort ; Suchkriterium");
		serverCommand.newLine();
		serverCommand.write("Beenden Sie die Session mit exit");
		serverCommand.newLine();
		serverCommand.flush();
	}
	
	private void responseToClient(Socket clientSocket) throws Exception {
		BufferedReader clientResponse = new BufferedReader(
				new InputStreamReader(clientSocket.getInputStream()));
		String line;
		String[] ClientRequest = clientResponse.readLine().split(BasicServer.fileSeparator);
		while ((line = clientResponse.readLine()) != null){
			if (line.equals("error")){
				serverCommand.write("Bitte korrigieren Sie Ihre Eingabe");
				serverCommand.flush();
			}
			else {
				for (Street street : streetList) {
					if (street.getSource_id() == Integer.parseInt(ClientRequest[0])) {
						serverCommand.write(street.toString());
						serverCommand.flush();
					}
				}
			}
			
		}
	}
	
	@Override
	public void startServerRoutine() throws Exception {
		while (isServerActiv == true) {
			Socket clientSocket = super.serverSocket.accept();
			startServerShell(clientSocket);
			WorkerThread workerThread = new WorkerThread(clientSocket);
			Thread clientThread = new Thread(workerThread);
			clientThread.start();
			
			while (isClientActiv == true) {
				responseToClient(clientSocket);
			}
		}
	}
	
}
