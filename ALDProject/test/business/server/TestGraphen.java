package business.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import beans.ArrayGraph;
import beans.ListGraph;
import beans.Street;
import beans.WeightedEdge;

public class TestGraphen {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File f = new File("src\\ressources\\streets.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String s;
			String[] inputArray;
			ArrayList<Street> streets = new ArrayList<Street>();
			while ((s = br.readLine()) != null) {
				inputArray = s.split(";");
				if (!(inputArray[0].equals("##")) && inputArray.length == 5) {
					streets.add(
							new Street(inputArray[0], Integer.parseInt(inputArray[1]), Integer.parseInt(inputArray[2]),
									Integer.parseInt(inputArray[3]), Integer.parseInt(inputArray[4])));
				}
			}

			ListGraph listGraph = new ListGraph(streets.size(), true);
			ArrayGraph arrayGraph = new ArrayGraph(streets.size(), true);
			for (Street street : streets) {
				listGraph.addEdge(street.getSource_id(), street.getDestination_id(), street.getDistance());
				arrayGraph.addEdge(street.getSource_id(), street.getDestination_id(), street.getDistance());
			}
			System.out.println(listGraph.hasEdge(1, 2));
			System.out.println(arrayGraph.hasEdge(1, 2));
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
