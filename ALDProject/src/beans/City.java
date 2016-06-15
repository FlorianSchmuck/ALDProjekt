package beans;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class City implements Comparator<Street> {

	private final String name;
	private final int id;
	private List<Street> streets = new ArrayList<Street>();

	public City(String name, int id) {
		super();
		this.name = name;
		this.id = id;
	}

	public void AddStreet(Street s) {
		streets.add(s);
	}
	public void sortStreets()
	{
		Collections.sort(streets,new CompareStreets());
		//implementiert die CompareStreets Klasse
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public ArrayList<Street> getStreets() {
		return (ArrayList<Street>) streets;
	}

	@Override
	public int compare(Street o1, Street o2) {
		// TODO Auto-generated method stub
		if (o1.getDistance() < o2.getDistance())
			return 1;
		else if (o1.getDistance() < o2.getDistance())
			return -1;
		else return 0;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((streets == null) ? 0 : streets.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		City other = (City) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (streets == null) {
			if (other.streets != null)
				return false;
		} else if (!streets.equals(other.streets))
			return false;
		return true;
	}
	

}
