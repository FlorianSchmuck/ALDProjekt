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
	private BufferedWriter trueServerCommand;
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
		streetGraphList = new ArrayList<>();
		theIOAccessLayerInstance = IOAccessLayer.getTheInstance();
		cityFile = super.getFileFromRessource("citys.txt");
		streetsFile = super.getFileFromRessource("streets.txt");
	}

	private void startServerShell(Socket clientSocket) throws Exception {
		trueServerCommand = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
		trueServerCommand.write("Willkommen am Server ... bitte um Ihre Eingabe");
		trueServerCommand.newLine();
		trueServerCommand.write("Gestalten Sie die Eingabe wie folgt:");
		trueServerCommand.newLine();
		trueServerCommand.write("Startort ; Zielort ; Suchkriterium");
		trueServerCommand.newLine();
		trueServerCommand.write("Beenden Sie die Session mit exit");
		trueServerCommand.newLine();
		trueServerCommand.flush();
	}
	
	private void responseToClient(Socket clientSocket) throws Exception {
		BufferedReader trueClientResponse = new BufferedReader(
				new InputStreamReader(clientSocket.getInputStream()));
		String line;
		String[] ClientRequest = trueClientResponse.readLine().split(BasicServer.fileSeparator);
		while ((line = trueClientResponse.readLine()) != null){
			if (line.equals("error")){
				trueServerCommand.write("Bitte korrigieren Sie Ihre Eingabe");
				trueServerCommand.flush();
			}
			else {
				for (Street street : streetGraphList) {
					if (street.getSource_id() == Integer.parseInt(ClientRequest[0])) {
						trueServerCommand.write(street.toString());
						trueServerCommand.flush();
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
