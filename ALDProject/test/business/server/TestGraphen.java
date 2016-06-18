package business.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import beans.ArrayGraph;
import beans.City;
import beans.ListGraph;
import beans.Street;
import business.utilities.IOAccessLayer;

public class TestGraphen {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File f = new File("src\\ressources\\streets.txt");
		try {
			IOAccessLayer acl =  IOAccessLayer.getTheInstance();
			List<Street> streets = acl.readStreetsFile(new File(BasicServer.ressourcePath+"streets.txt"));			
			

			ListGraph listGraph = new ListGraph(streets.size(), true);
			ArrayGraph arrayGraph = new ArrayGraph(streets.size(), true);
			for (Street street : streets) {
				listGraph.addEdge(street.getSource_id(), street.getDestination_id(), street.getDistance());
				arrayGraph.addEdge(street.getSource_id(), street.getDestination_id(), street.getDistance());
			}
			System.out.println(listGraph.hasEdge(1, 2));
			System.out.println(arrayGraph.hasEdge(1, 2));
			
			List<City> cities = acl.readCityFile(new File(BasicServer.ressourcePath+"citys.txt"));
			
			for(City city : cities)
			{
				System.out.println(city.getName());
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
