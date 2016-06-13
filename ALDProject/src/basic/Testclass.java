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

public class Testclass {

	public static ArrayList<Street> graph = new ArrayList<>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			File fcity = new File("src\\ressources\\citys.txt");
			File fstreets = new File("src\\ressources\\streets.txt");
			String s;
			String parts[];
			BufferedReader br_city = new BufferedReader(new FileReader(fcity));
			BufferedReader br_streets = new BufferedReader(new FileReader(fstreets));
			ArrayList<City> citys = new ArrayList<City>();

			/*
			 * while ((s = br_city.readLine()) != null) { //Einlesen der
			 * Staedte, lesen bis zum ende der Datei parts = s.split(";");
			 * //Aufteilen der Zeile if (parts.length < 2) { continue; //wenn
			 * Zeile nicht aus 2 Werten besteht->naechste Zeile } else {
			 * citys.add(new City((String) parts[0],
			 * Integer.parseInt(parts[1]))); //hinzufuegen der eingelesenen
			 * Zeile zur ArrayList, 1. Teil->Name, 2. Teil wird auf Int geparsed
			 * -> ID } }
			 
			br_city.close();
			br_streets.close();
			for (City c : citys) {
				System.out.println(c.getName() + " " + c.getId());
			}
*/
			// GET STREET OUT OF CSV

			while (!(br_streets.readLine() != null)) {
				String[] inputStreet = br_streets.readLine().split(";");
				graph.add(new Street(inputStreet[0], Integer.parseInt(inputStreet[1]), Integer.parseInt(inputStreet[2]),
						Integer.parseInt(inputStreet[3]), Integer.parseInt(inputStreet[4])));
			}
		}
		catch (IOException e) {e.printStackTrace();}
			// FINISHED
/*
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println("THIS IS TEST");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
		// SERVERTEST

		ServerSocket RPServer; // RP RoutenPlanerServer
		boolean RPSAktiv = true; // Server ON/OFF
		boolean CAktiv = true; // Client ON/OFF
		try {
			RPServer = new ServerSocket(1111);
			while (RPSAktiv = true) {
				Socket clientSocket = RPServer.accept();
				BufferedWriter ServerCommand = new BufferedWriter(
						new OutputStreamWriter(clientSocket.getOutputStream()));
				TestClient tc = new TestClient();
				Thread tc_thread = new Thread(tc);
				tc_thread.start();
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

