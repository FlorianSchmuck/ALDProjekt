package basic;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Testclass {

	public static void main(String[] args){
		// TODO Auto-generated method stub
		
				try {
					File fcity = new File("src\\ressources\\citys.txt");
					File fstreets = new File("src\\ressources\\streets.txt");
					String s;
					String parts[];
					FileReader fr_city = new FileReader(fcity);
					FileReader fr_streets = new FileReader(fstreets);
					BufferedReader br_city = new BufferedReader(fr_city);
					BufferedReader br_streets = new BufferedReader(fr_streets);
					ArrayList<City> citys = new ArrayList<City>();
					
					while ((s = br_city.readLine()) != null) {
						//Einlesen der Städte, prüfen ob Datei leer
						parts = s.split(";");
						//Aufteilen der Zeile
						if (parts.length < 2)
						{
							continue;
							//wenn Zeile nicht aus 2 Werten besteht->nächste Zeile
						}
						else {
							citys.add(new City((String) parts[0], Integer.parseInt(parts[1])));
							//hinzufügen der eingelesenen Zeile zur ArrayList, 1. Teil->Name, 2. Teil wird auf Int geparsed -> ID
						}
					}
					br_city.close();
					br_streets.close();
					for (City c :citys)
					{
						System.out.println(c.getName()+" "+ c.getId());
					}
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
	}

}
