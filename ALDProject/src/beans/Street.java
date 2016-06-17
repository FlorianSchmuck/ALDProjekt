package beans;

public class Street {

	private final String name;
	private final int id;
	private final int distance;
	private final int sourceCity_id;
	private final int destinationCity_id;

	public Street(String name, int id, int distance, int source_id, int destination_id) {
		super();
		this.name = name;
		this.id = id;
		this.distance = distance;
		this.sourceCity_id = source_id;
		this.destinationCity_id = destination_id;
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
		return sourceCity_id;
	}

	public int getDestination_id() {
		return destinationCity_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + destinationCity_id;
		result = prime * result + distance;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + sourceCity_id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		//TODO refactor
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Street other = (Street) obj;
		if (destinationCity_id != other.destinationCity_id)
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
		if (sourceCity_id != other.sourceCity_id)
			return false;
		return true;
	}
	
}
