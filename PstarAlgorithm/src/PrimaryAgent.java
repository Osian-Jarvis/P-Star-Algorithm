import java.util.*;
import processing.core.PApplet;

public class PrimaryAgent extends Agent {
	
	HashMap<Integer, String> closedSet = new HashMap<Integer, String>();
	HashMap<Integer, String> openSet = new HashMap<Integer, String>();
	Queue<String> safetyQueue = new LinkedList<String>();
	
	int counter;
	int goalNodeX, goalNodeY;
	int size1x = 0;
	int size1y = 0;
	int pAgentSize = 25;
	boolean goalFound = false, terminate = false;
	
	int loopKey;
	String loopValue;
	
	PApplet parent;
	
	PrimaryAgent(PApplet p, int pAgentX, int pAgentY, int agentSize ) {
		
		super(p, pAgentX, pAgentY, agentSize);
		size1x = pAgentX;
		size1y = pAgentY;
		pAgentSize = agentSize;
	}
	
	public void displayPrimaryAgent(){
		super.parent.fill(0,0,200);
		super.parent.rect(size1x, size1y, pAgentSize, pAgentSize);
	}
	
	public void basicSearch(){
		while(!goalFound){
			size1x += 25;
			if(size1x >= 500){
				size1x = 0;
				size1y += 25;
			}
			if(size1x == goalNodeX && size1y == goalNodeY){
				goalFound = true;
				System.out.println("Goal Found");
			}
		}
	}
	
	public HashMap<Integer, String> setupOpenSet(HashMap<Integer, String> data){
		
		for(int i = 0; i < data.size(); i++){
			if(data.get(i) == "neutral" || data.get(i) == "open" ){
			openSet.put(i, "open");
			}
		}
		System.out.println("Open Set Initalized");
		System.out.println(data.toString());
		return data;
	}
	
	public void setGoal(int gX, int gY){
		goalNodeX = gX;
		goalNodeY = gY;
		
	}
	
	public int getLocationX(){
		return size1x;
	}
	
	public int getLocationY(){
		return size1y;
	}
	
	public void hexSearchNoDTASTC(){
		size1x += 25;
		if(size1x >= 500){
			size1y += 25;
			size1x = 0;
		}
	}
	
	public void hexSearch(ArrayList<Integer> x, ArrayList<Integer> y){
		for(int i = 0; i< y.size(); i++){
			System.out.println("yAxis");
			for(int j = 0; j < x.size(); j++){
				System.out.println("xAxis");
				size1x = x.get(j);
				size1y = y.get(i);
			}
		}
	}
	
	public int getGoalX(int gX){
		return gX;
	}
	public int getGoalY(int gY){
		return gY;
	}

	
	
}
