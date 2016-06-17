package business.utilities;
import java.util.Comparator;

import beans.Street;

public class CompareStreets implements Comparator<Street> {

	@Override
	public int compare(Street o1, Street o2) {
		// TODO Auto-generated method stub
		if (o1.getDistance() < o2.getDistance())
			return 1;
		else if (o1.getDistance() < o2.getDistance())
			return -1;
		else return 0;
	}

}
