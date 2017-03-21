import processing.core.PApplet;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Operations extends PApplet{
	ArrayList<Integer> xSpaces = new ArrayList<Integer>();
	ArrayList<Integer> ySpaces = new ArrayList<Integer>();
	ArrayList<ClosedHex> closedHexList = new ArrayList<ClosedHex>();
	ArrayList<SafeHex> safeHexList = new ArrayList<SafeHex>();
	HashMap<Integer, String> gSet = new HashMap<Integer, String>();
	boolean testRun = false;
	int goalZoneX, goalZoneY;
	Random rn = new Random();
	PrimaryAgent primaryAgent = new PrimaryAgent(this, 0, 0, 25);
	SearchSpace grid = new SearchSpace(this, gSet);
	SafeHex safeTest = new SafeHex(this, 50,50, 25);
	int[][] closedLocs = new int[20][20];
	
	public static void main(String[] args){
		PApplet.main("Operations");
	}
	
	public void settings(){
	size(501,501);
	}
	
	public void setup(){
		frameRate(15);
		safeTest.displaySafeHex();
		goalZoneX = primaryAgent.getGoalX(grid.getGoalX());
		goalZoneY = primaryAgent.getGoalY(grid.getGoalY());
		//primaryAgent.setupOpenSet(grid.generateDataStructure());
		primaryAgent.setGoal(grid.getGoalX(), grid.getGoalY());
		gridHexSet(xSpaces, ySpaces, 0);
		//closedLocs = grid.closedLocationsTest();
		
		closedHexList = collectClosedHexs();
		safeHexList = collectSafeHexs(closedHexList);
		//System.out.println(safeHexList.size());
		//System.out.println(closedHexList.size());
	}
	
	public void draw(){
		background(255);
		collision(closedHexList, safeHexList);
		grid.displayGoalZone();
		for(SafeHex sh: safeHexList){
			sh.displaySafeHex();
		}
		for(ClosedHex ch: closedHexList){
			ch.displayClosedHex();
		}
		primaryAgent.displayPrimaryAgent();
		gridHexLineGenerator();
		//primaryAgent.basicSearch();
		primaryAgent.hexSearchNoDTASTC();
		//primaryAgent.hexSearch(xSpaces, ySpaces);
		completeChecks();
	}
	
	
	
	public void gridHexLineGenerator(){
		
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
			frameRate(60);
			//System.exit(0);
		}
		else if(primaryAgent.terminate == true){
			System.out.println("Program Terminated No Goal Located!");
		}
	}
	
	public void gridHexSet(ArrayList<Integer> x, ArrayList<Integer> y, int gridSpaceing){
		for(int i = 0; i < 21; i++){
			y.add(i, 0 + gridSpaceing);
			x.add(i, 0 + gridSpaceing);
			gridSpaceing += 25;
		}
		//System.out.println(y.toString());
		//System.out.println(x.toString());
	}
	
	public ArrayList<ClosedHex> collectClosedHexs(){
		ArrayList<ClosedHex> hexListClosed = new ArrayList<ClosedHex>();
		
		int posGeneratorX, posGeneratorY;
		
		for(int i = 0; i < 20; i++){
			posGeneratorX = randomNumberGenerator();
			posGeneratorY = randomNumberGenerator();
			hexListClosed.add(new ClosedHex(this, posGeneratorX*25, posGeneratorY*25, 25));
			//System.out.println("Closed Hex Locations = " + posGeneratorX + " " + posGeneratorY);
			if(posGeneratorX*25 == primaryAgent.getLocationX() && posGeneratorY*25 == primaryAgent.getLocationY() || posGeneratorX*25 == grid.getGoalX() && posGeneratorY*25 == grid.getGoalY()){
				hexListClosed.remove(i);
				i -= 1;
				System.out.println("Goal or Start Location Insecure reattempting");
			}
		}
		System.out.println("Closed Hexs Generated!");
		return hexListClosed;
	}
	
	public ArrayList<SafeHex> collectSafeHexs(ArrayList <ClosedHex> closedHexList){
		ArrayList<SafeHex> hexListSafe = new ArrayList<SafeHex>();
		
		int posGeneratorX, posGeneratorY;

		for(int i = 0; i < 20; i++){
		posGeneratorX = randomNumberGenerator();
		posGeneratorY = randomNumberGenerator();
		hexListSafe.add(new SafeHex(this, posGeneratorX*25, posGeneratorY*25, 25));
			if(posGeneratorX*25 == closedHexList.get(i).getlocX() && posGeneratorY*25 == closedHexList.get(i).getlocY() || posGeneratorX*25 == primaryAgent.getLocationX() && posGeneratorY*25 == primaryAgent.getLocationY() || posGeneratorX*25 == grid.getGoalX() && posGeneratorY*25 == grid.getGoalY()) {
				hexListSafe.remove(i);
				i -= 1;
				System.out.println("Goal or Start Location Insecure Reattempting");
			}
		}
		System.out.println("Safe Hexs Generated!");
		return hexListSafe;
	}
	
	public void collision(ArrayList<ClosedHex> hexListClosed, ArrayList<SafeHex> hexListSafe){
		
		ArrayList<Integer> closedHexLocX = new ArrayList<Integer>();
		ArrayList<Integer> closedHexLocY = new ArrayList<Integer>();
		ArrayList<Integer> safeHexLocX = new ArrayList<Integer>();
		ArrayList<Integer> safeHexLocY = new ArrayList<Integer>();

		int agentSize = 25;
		int currentXloc = primaryAgent.getLocationX();
		int currentYloc = primaryAgent.getLocationY();
		for(int i = 0; i < hexListClosed.size(); i++){
			closedHexLocX.add(hexListClosed.get(i).getlocX());
			closedHexLocY.add(hexListClosed.get(i).getlocY());
		
			if(primaryAgent.size1x+agentSize > closedHexLocX.get(i) && primaryAgent.size1y+agentSize > closedHexLocY.get(i) && primaryAgent.size1x-agentSize < closedHexLocX.get(i)+agentSize && primaryAgent.size1y+agentSize < closedHexLocY.get(i)+agentSize){
			primaryAgent.size1x = currentXloc;
			primaryAgent.size1y = currentYloc;
			}
		}
		for(int j = 0; j < hexListSafe.size(); j++){
			safeHexLocX.add(hexListSafe.get(j).getLocX());
			safeHexLocY.add(hexListSafe.get(j).getLocY());
		}
		
	}
	
	public int randomNumberGenerator(){
		int min = 1;
		int max = 19;
		int randomNumber = ThreadLocalRandom.current().nextInt(min, max + 1);
		return randomNumber;
	}
}
