import java.util.*;
import processing.core.PApplet;


public class SearchSpace extends PApplet {

	public HashMap<Integer, String> gridStructure = new HashMap<Integer, String>(400);
	int gridSpaceSize = 400;
	int lastNumber = 0;
	int nextNumber = 25;
	int goalAreaX = 450;
	int goalAreaY = 450;
	int counter = 0;
	int hexSize = 25;
	
	PApplet parent;


	SearchSpace(PApplet p,HashMap<Integer, String> set){
		gridStructure = set;
		parent = p;
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
	
	public int[][] closedLocationsTest(){
		
		//int isClosed = 0;
		
		int[][] locs = new int [20][20];
		
		for(int i = 0; i < 20; i++){
			int lastNumber = 0;
			for(int j = 0; j < 20; j++){
				locs[i][j] = 0+lastNumber;
				lastNumber += 25;
			}
		}
		System.out.println(Arrays.deepToString(locs));
		return locs;
	}
	
	public void closedHex(){
		parent.fill(122);
		parent.rect(350, 400, hexSize, hexSize);
	}
	public void displayGoalZone(){
		parent.fill(188,0,188);
		parent.rect(goalAreaX, goalAreaY, hexSize, hexSize);
	}
	
	
	
}
