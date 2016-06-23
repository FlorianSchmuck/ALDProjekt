package business.utilities;

import java.util.Comparator;

import beans.WeightedEdge;

public class CompareWeightedEdge implements Comparator<WeightedEdge> {

	@Override
	public int compare(WeightedEdge o1, WeightedEdge o2) {
		// TODO Auto-generated method stub
		if(o1.weight<o2.weight)
			return -1;
		if(o1.weight>o2.weight)
			return 1;
		return 0;
		
	}

}
