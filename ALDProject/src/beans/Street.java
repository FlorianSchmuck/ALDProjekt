package beans;

public class Street {

	private final String name;
	private final int streetId;
	private final int distance;
	private final int sourceCityId;
	private final int destinationCityId;

	public Street(String name, int streetId,  int source_id, int destination_id,int distance) {
		super();
		this.name = name;
		this.streetId = streetId;
		this.distance = distance;
		this.sourceCityId = source_id;
		this.destinationCityId = destination_id;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return streetId;
	}

	public int getDistance() {
		return distance;
	}

	public int getSource_id() {
		return sourceCityId;
	}

	public int getDestination_id() {
		return destinationCityId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + destinationCityId;
		result = prime * result + distance;
		result = prime * result + streetId;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + sourceCityId;
		return result;
	}
	

	@Override
	public String toString() {
		return "Street [name=" + name + ", streetId=" + streetId + ", distance=" + distance + ", sourceCityId="
				+ sourceCityId + ", destinationCityId=" + destinationCityId + "]";
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
		if (destinationCityId != other.destinationCityId)
			return false;
		if (distance != other.distance)
			return false;
		if (streetId != other.streetId)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (sourceCityId != other.sourceCityId)
			return false;
		return true;
	}
	
}
