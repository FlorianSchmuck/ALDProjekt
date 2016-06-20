package beans;

import java.util.List;

public class VertexTree {

	private City[] vertextTreeArray;
	private int count = 0;
	private int index = 0 ;
	private int capicity;
	private Nodes root;
	
	public VertexTree(int size,List<City> cityList){
		root = null;
		capicity = size +1; 
		vertextTreeArray = new City[capicity]; // Kapazität Baum festlegen
		initializeTree(cityList);
		
	}
	public void initializeTree(List<City> cityList){
		for(City element : cityList){
			insertCityToTree(element);
		}		
	}
	public void insertCityToTree(City insertCity){
		if(index > capicity) //Next is outside of Bounds
			return;
		//Change to blind adding because of sorted list
		vertextTreeArray[index] = insertCity;
		index++;
	}
	private int right(int pos){
		return (pos*2);
	}
	private int left(int pos){
		return(pos*2+1);
	}
	//public City findBy(String searchCity,int pos){
/*		if(vertextTreeArray[pos].getName() == searchCity){
			return vertextTreeArray[pos];
		}
		else if(, o2)
		}*/
		//return;
}
