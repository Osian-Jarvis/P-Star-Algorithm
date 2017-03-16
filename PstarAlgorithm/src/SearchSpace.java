import java.util.*;

public class SearchSpace {

	public HashMap<Integer, String> gridStructure = new HashMap<Integer, String>(400);
	int gridSpaceSize = 400;
	int lastNumber = 0;
	int nextNumber = 25;
	int goalAreaX = 300;
	int goalAreaY = 300;
	int counter = 0;


	SearchSpace(HashMap<Integer, String> set){
		gridStructure = set;
	}
	
	public HashMap<Integer, String> generateDataStructure(){
		
		HashMap<Integer, String> gridS = new HashMap<Integer, String>();
		
		for(int i = 0; i < 400; i++){
			gridS.put(i, "neutral");
		}
		System.out.println("Grid Structure Inialized");
		System.out.println(gridS.toString());
		
		return gridS;
	}
	
	public int getGoalX(){
		
		return goalAreaX;
	}
	
	public int getGoalY(){
		
		return goalAreaY;
	}
	
}
