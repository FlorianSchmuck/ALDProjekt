package basic;
import java.util.ArrayList;

public class Street {

	private final String name;
	private final int id;
	private final int distance;
	private final int source_id;
	private final int destination_id;

	public Street(String name, int id, int distance, int source_id, int destination_id) {
		super();
		this.name = name;
		this.id = id;
		this.distance = distance;
		this.source_id = source_id;
		this.destination_id = destination_id;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public int getDistance() {
		return distance;
	}

	public int getSource_id() {
		return source_id;
	}

	public int getDestination_id() {
		return destination_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + destination_id;
		result = prime * result + distance;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + source_id;
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
		Street other = (Street) obj;
		if (destination_id != other.destination_id)
			return false;
		if (distance != other.distance)
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (source_id != other.source_id)
			return false;
		return true;
	}
	
}
