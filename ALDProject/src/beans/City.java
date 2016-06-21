package beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import business.utilities.CompareStreets;

public class City implements Comparator<City> {

	private final String name;
	private final int id;
	// private List<Street> streets = new ArrayList<Street>();
	private EdgeHeap vHeap;

	public City(String name, int id) {
		super();
		this.name = name;
		this.id = id;
	}

	public void AddStreet(Street s) {
		// streets.add(s);
		boolean insert = true;
		while (insert)
			insert = vHeap.insert(new WeightedEdge(s.getId(), s.getDistance()));

	}

	/*public void sortStreets() {
		Collections.sort(streets, new CompareStreets());
		// implementiert die CompareStreets Klasse
	}
	*/

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	/*
	 * public ArrayList<Street> getStreets() { return (ArrayList<Street>)
	 * streets; }
	 */
	public EdgeHeap getVertexHeap() {
		return vHeap;
	}

	@Override
	public int compare(City o1, City o2) {
		// TODO Auto-generated method stub
		if (o1.getName().compareTo(o2.getName()) > 0)
			return 1;
		else if (o1.getName().compareTo(o2.getName()) < 0)
			return -1;
		else
			return 0;

	}

}
