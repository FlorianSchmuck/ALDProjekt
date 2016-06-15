package basic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import beans.City;
import beans.Street;

public class Testclass {

	public static ArrayList<Street> graph = new ArrayList<>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//BufferedReader br_city = new BufferedReader(new FileReader());
		//BufferedReader br_streets = new BufferedReader(new FileReader(fstreets));
		ArrayList<City> citys = new ArrayList<City>();

		ServerSocket RPServer; // RP RoutenPlanerServer
		boolean RPSAktiv = true; // Server ON/OFF
		boolean CAktiv = true; // Client ON/OFF
		try {
			RPServer = new ServerSocket(1111);
			while (RPSAktiv = true) {
				Socket clientSocket = RPServer.accept();
				BufferedWriter ServerCommand = new BufferedWriter(
						new OutputStreamWriter(clientSocket.getOutputStream()));
				//TestClient tc = new TestClient();
				//Thread tc_thread = new Thread(tc);
				//tc_thread.start();
				ServerCommand.write("Willkommen am Server ... bitte um Ihre Eingabe");
				ServerCommand.newLine();
				ServerCommand.write("Gestalten Sie die Eingabe wie folgt:");
				ServerCommand.newLine();
				ServerCommand.write("Startort ; Zielort ; Suchkritärium");
				ServerCommand.newLine();
				ServerCommand.flush();

				while (CAktiv = true) {
					BufferedReader ClientResponse = new BufferedReader(
							new InputStreamReader(clientSocket.getInputStream()));
					String[] ClientRequest = ClientResponse.readLine().split(";");
					for (Street street : graph) {
						if (street.getSource_id() == Integer.parseInt(ClientRequest[0])) {
							ServerCommand.write(street.toString());
							ServerCommand.flush();
						}

					}

				}
			}
		}

		catch (IOException e) {
			e.printStackTrace();
		}
	}
}

