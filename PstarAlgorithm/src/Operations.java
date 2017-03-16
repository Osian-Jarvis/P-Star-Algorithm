import processing.core.PApplet;
import java.util.*;

public class Operations extends PApplet{
	
	HashMap<Integer, String> gSet = new HashMap<Integer, String>();
	
	boolean testRun = false;
	int timer = millis();
	PrimaryAgent primaryAgent = new PrimaryAgent(this, 0, 0, 25);
	SearchSpace grid = new SearchSpace(gSet);
	
	public static void main(String[] args){
		
		PApplet.main("Operations");
		
	}
	
	public void settings(){
	size(501,501);
	}
	
	public void setup(){
		frameRate(15);
		//grid.generateDataStructure();
		primaryAgent.setupOpenSet(grid.generateDataStructure());
		primaryAgent.setGoal(grid.getGoalX(), grid.getGoalY());
		
	}
	
	public void draw(){
		background(255);
		completeChecks();
		gridHexGenerator();
		System.out.println("Step Y");
		primaryAgent.basicSearch(primaryAgent.openSet);
		System.out.println("Step Z");
	}
	public void gridHexGenerator(){
	int gridHexSize = 25;
	
	for(int i = 0; i < width; i += gridHexSize){
	line(i, 0, i, height);
	}
	for(int j = 0; j < height; j += gridHexSize){
	line(0, j, width, j);
	}
	
	}
	
	public void completeChecks(){
		if(primaryAgent.goalFound == true){
			System.out.println("Search Complete Goal Found!");
			frameRate(0);
		}
		else if(primaryAgent.terminate == true){
			System.out.println("Program Terminated No Goal Located!");
		}
	}

}
