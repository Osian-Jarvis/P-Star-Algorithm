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
	boolean goalFound, terminate;
	
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
		super.parent.rect(size1x, size1y, pAgentSize, pAgentSize);
		super.parent.fill(100,100,100);
	}
	

	public void basicSearch(HashMap<Integer, String> oSet){
		System.out.println("Step A");
		displayPrimaryAgent();
		while(size1x <= 500 && size1y <= 500){
			size1x += 25;
			System.out.println("Step B");
			if(size1x >= 500){
				size1x = 0;
				size1y += 25;
				System.out.println("Step C");
			}
			if(size1x == goalNodeX && size1y == goalNodeY){
				goalFound = true;
				terminate = true;
				System.out.println("Goal Found");
			}
			if(oSet.isEmpty()){
				System.out.println("Cannot Locate Goal Rationally OR Open Set Cannot Be Accessed");
				terminate = true;
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

	
	
}
