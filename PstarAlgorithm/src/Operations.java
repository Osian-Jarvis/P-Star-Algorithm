import processing.core.PApplet;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Operations extends PApplet{
	ArrayList<Integer> xSpaces = new ArrayList<Integer>();
	ArrayList<Integer> ySpaces = new ArrayList<Integer>();
	ArrayList<ClosedHex> closedHexList = new ArrayList<ClosedHex>();
	ArrayList<SafeHex> safeHexList = new ArrayList<SafeHex>();
	ArrayList<DangerHex> dangerHexList = new ArrayList<DangerHex>();
	ArrayList<PrimaryAgent> agentPathList = new ArrayList<PrimaryAgent>();
	ArrayList<HostileAgent> hostileAgentList = new ArrayList<HostileAgent>();
	HashMap<Integer, String> gSet = new HashMap<Integer, String>();
	boolean testRun = false;
	boolean xMoved, yMoved = false;
	boolean xPlus, xMinus, yPlus, yMinus;
	int goalZoneX, goalZoneY;
	int counter = 0;
	int numberOfHostiles = 1;
	Random rn = new Random();
	PrimaryAgent primaryAgent = new PrimaryAgent(this, 0, 0, 25);
	SearchSpace grid = new SearchSpace(this, 450,450);
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
		//safeTest.displaySafeHex();
		//primaryAgent.setupOpenSet(grid.generateDataStructure());
		primaryAgent.setGoal(grid.getGoalX(), grid.getGoalY());
		//gridHexSet(xSpaces, ySpaces, 0);
		//closedLocs = grid.closedLocationsTest();
		
		closedHexList = collectClosedHexs();
		safeHexList = collectSafeHexs(closedHexList);
		dangerHexList = collectDangerHexs(closedHexList, safeHexList);
		hostileAgentGenerator();
		agentPathList.add(new PrimaryAgent(this, primaryAgent.size1x, primaryAgent.size1y, 25));

	}
	
	public void draw(){
		background(255);
		grid.displayGoalZone();
		for(ClosedHex ch: closedHexList){
			ch.displayClosedHex();
		}
		for(SafeHex sh: safeHexList){
			sh.displaySafeHex();
		}
		for(DangerHex dh: dangerHexList){
			dh.displayDangerHex();
		}
		primaryAgent.displayPrimaryAgent();
		gridHexLineGenerator();
		hostileAgentMovement();
		safeSearchTest();
		primaryAgentHostileAgentDetection();
		primaryAgentMoveX();
		primaryAgentMoveY();
		agentEdgeCollision();
		completeChecks();
		System.out.println(counter);
		System.out.println(agentPathList.size());
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
		if(primaryAgent.size1x == goalZoneX && primaryAgent.size1y == goalZoneY){
			System.out.println("Search Complete Goal Found!");
			System.out.println("Counter " + counter);
			System.out.println("Agent Steps " + agentPathList.size());
			printAgentPath();
			//System.exit(0);
			frameRate(0);
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
			hexListClosed.add(new ClosedHex(this, posGeneratorX*25, posGeneratorY*25, 25, 122));
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
	
	public ArrayList<SafeHex> collectSafeHexs(ArrayList<ClosedHex> closedHexList){
		ArrayList<SafeHex> hexListSafe = new ArrayList<SafeHex>();
		
		int posGeneratorX, posGeneratorY;

		for(ClosedHex ch: closedHexList){
		posGeneratorX = randomNumberGenerator();
		posGeneratorY = randomNumberGenerator();
		if(posGeneratorX*25 != ch.getlocX() && posGeneratorY*25 != ch.getlocY() || posGeneratorX*25 != primaryAgent.getLocationX() && posGeneratorY*25 != primaryAgent.getLocationY() || posGeneratorX*25 != grid.getGoalX() && posGeneratorY*25 != grid.getGoalY()) {
		hexListSafe.add(new SafeHex(this, posGeneratorX*25, posGeneratorY*25, 25));
		}
		}
		System.out.println("Safe Hexs Generated!");
		return hexListSafe;
	}
	
	public ArrayList<DangerHex> collectDangerHexs(ArrayList<ClosedHex> closedHexList, ArrayList<SafeHex> safeHexList){
		ArrayList<DangerHex> hexListDanger = new ArrayList<DangerHex>();
		
		int posGeneratorX, posGeneratorY;
		
		for(int i = 0; i < 1; i++){
			posGeneratorX = randomNumberGenerator();
			posGeneratorY = randomNumberGenerator();
			if(posGeneratorX*25 != closedHexList.get(i).getlocX() && posGeneratorY*25 != closedHexList.get(i).getlocY() || posGeneratorX*25 != primaryAgent.getLocationX() && posGeneratorY*25 != primaryAgent.getLocationY() || posGeneratorX*25 != grid.getGoalX() && posGeneratorY*25 != grid.getGoalY() || posGeneratorX*25 != safeHexList.get(i).getLocX() && posGeneratorY*25 != safeHexList.get(i).getLocY() || posGeneratorX*25 != grid.getGoalX() && posGeneratorY*25 != grid.getGoalY()){
				hexListDanger.add(new DangerHex(this, posGeneratorX*25, posGeneratorY*25, 25));
				if(posGeneratorX*25+25 != closedHexList.get(i).getlocX() && posGeneratorY*25 != closedHexList.get(i).getlocY() || posGeneratorX*25+25 != primaryAgent.getLocationX() && posGeneratorY*25 != primaryAgent.getLocationY() || posGeneratorX*25+25 != grid.getGoalX() && posGeneratorY*25 != grid.getGoalY() || posGeneratorX*25+25 != safeHexList.get(i).getLocX() && posGeneratorY*25 != safeHexList.get(i).getLocY()  || posGeneratorX*25+25 != grid.getGoalX() && posGeneratorY*25 != grid.getGoalY()){
					//+25X
					hexListDanger.add(new DangerHex(this, posGeneratorX*25+25, posGeneratorY*25, 25));
				}
				if(posGeneratorX*25 != closedHexList.get(i).getlocX() && posGeneratorY*25+25 != closedHexList.get(i).getlocY() || posGeneratorX*25 != primaryAgent.getLocationX() && posGeneratorY*25+25 != primaryAgent.getLocationY() || posGeneratorX*25 != grid.getGoalX() && posGeneratorY*25+25 != grid.getGoalY() || posGeneratorX*25 != safeHexList.get(i).getLocX() && posGeneratorY*25+25 != safeHexList.get(i).getLocY()  || posGeneratorX*25 != grid.getGoalX() && posGeneratorY*25+25 != grid.getGoalY()){
					//+25Y
					hexListDanger.add(new DangerHex(this, posGeneratorX*25, posGeneratorY*25+25, 25));
				} 
				if(posGeneratorX*25-25 != closedHexList.get(i).getlocX() && posGeneratorY*25 != closedHexList.get(i).getlocY() || posGeneratorX*25-25 != primaryAgent.getLocationX() && posGeneratorY*25 != primaryAgent.getLocationY() || posGeneratorX*25-25 != grid.getGoalX() && posGeneratorY*25 != grid.getGoalY() || posGeneratorX*25-25 != safeHexList.get(i).getLocX() && posGeneratorY*25 != safeHexList.get(i).getLocY() || posGeneratorX*25-25 != grid.getGoalX() && posGeneratorY*25 != grid.getGoalY()){
					//-25X
					hexListDanger.add(new DangerHex(this, posGeneratorX*25-25, posGeneratorY*25, 25));
				}
				if(posGeneratorX*25 != closedHexList.get(i).getlocX() && posGeneratorY*25-25 != closedHexList.get(i).getlocY() || posGeneratorX*25 != primaryAgent.getLocationX() && posGeneratorY*25-25 != primaryAgent.getLocationY() || posGeneratorX*25 != grid.getGoalX() && posGeneratorY*25-25 != grid.getGoalY() || posGeneratorX*25 != safeHexList.get(i).getLocX() && posGeneratorY*25-25 != safeHexList.get(i).getLocY()  || posGeneratorX*25 != grid.getGoalX() && posGeneratorY*25-25 != grid.getGoalY()){
					//-25Y
					hexListDanger.add(new DangerHex(this, posGeneratorX*25, posGeneratorY*25-25, 25));
				}
				if(posGeneratorX*25+25 != closedHexList.get(i).getlocX() && posGeneratorY*25+25 != closedHexList.get(i).getlocY() || posGeneratorX*25+25 != primaryAgent.getLocationX() && posGeneratorY*25+25 != primaryAgent.getLocationY() || posGeneratorX*25+25 != grid.getGoalX() && posGeneratorY*25+25 != grid.getGoalY() || posGeneratorX*25+25 != safeHexList.get(i).getLocX() && posGeneratorY*25+25 != safeHexList.get(i).getLocY()  || posGeneratorX*25+25 != grid.getGoalX() && posGeneratorY*25+25 != grid.getGoalY()){
					//+25X +25Y
					hexListDanger.add(new DangerHex(this, posGeneratorX*25+25, posGeneratorY*25+25, 25));
				}				
				if(posGeneratorX*25+25 != closedHexList.get(i).getlocX() && posGeneratorY*25-25 != closedHexList.get(i).getlocY() || posGeneratorX*25+25 != primaryAgent.getLocationX() && posGeneratorY*25-25 != primaryAgent.getLocationY() || posGeneratorX*25+25 != grid.getGoalX() && posGeneratorY*25-25 != grid.getGoalY() || posGeneratorX*25+25 != safeHexList.get(i).getLocX() && posGeneratorY*25-25 != safeHexList.get(i).getLocY()  || posGeneratorX*25+25 != grid.getGoalX() && posGeneratorY*25-25 != grid.getGoalY()){
					//+25X -25Y
					hexListDanger.add(new DangerHex(this, posGeneratorX*25+25, posGeneratorY*25-25, 25));
				}				
				if(posGeneratorX*25-25 != closedHexList.get(i).getlocX() && posGeneratorY*25-25 != closedHexList.get(i).getlocY() || posGeneratorX*25-25 != primaryAgent.getLocationX() && posGeneratorY*25-25 != primaryAgent.getLocationY() || posGeneratorX*25-25 != grid.getGoalX() && posGeneratorY*25-25 != grid.getGoalY() || posGeneratorX*25-25 != safeHexList.get(i).getLocX() && posGeneratorY*25-25 != safeHexList.get(i).getLocY()  || posGeneratorX*25-25 != grid.getGoalX() && posGeneratorY*25-25 != grid.getGoalY()){
					//-25X -25Y
					hexListDanger.add(new DangerHex(this, posGeneratorX*25-25, posGeneratorY*25-25, 25));
				}				
				if(posGeneratorX*25-25 != closedHexList.get(i).getlocX() && posGeneratorY*25+25 != closedHexList.get(i).getlocY() || posGeneratorX*25-25 != primaryAgent.getLocationX() && posGeneratorY*25+25 != primaryAgent.getLocationY() || posGeneratorX*25-25 != grid.getGoalX() && posGeneratorY*25+25 != grid.getGoalY() || posGeneratorX*25-25 != safeHexList.get(i).getLocX() && posGeneratorY*25+25 != safeHexList.get(i).getLocY()  || posGeneratorX*25-25 != grid.getGoalX() && posGeneratorY*25+25 != grid.getGoalY()){
					//-25X +25Y
					hexListDanger.add(new DangerHex(this, posGeneratorX*25-25, posGeneratorY*25+25, 25));
				}
			}
		}
		return hexListDanger;
	}
	
	public void collisionTest(ArrayList<ClosedHex> hexListClosed, ArrayList<SafeHex> hexListSafe){
		
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
		
			if(primaryAgent.size1x+agentSize == closedHexLocX.get(i) && primaryAgent.size1y+agentSize == closedHexLocY.get(i) || primaryAgent.size1x-agentSize == closedHexLocX.get(i)+agentSize && primaryAgent.size1y+agentSize == closedHexLocY.get(i)+agentSize){
			primaryAgent.size1x = currentXloc;
			primaryAgent.size1y = currentYloc;
			}
		}
		for(int j = 0; j < hexListSafe.size(); j++){
			safeHexLocX.add(hexListSafe.get(j).getLocX());
			safeHexLocY.add(hexListSafe.get(j).getLocY());
		}
		
	}
	
	public void hostileAgentGenerator(){
		for(int i = 0; i < numberOfHostiles; i++){
			hostileAgentList.add(new HostileAgent(this, dangerHexList.get(i).getLocX(), dangerHexList.get(i).getLocY(), 25));
			System.out.println(dangerHexList.get(i).getLocX());
			System.out.println(dangerHexList.get(i).getLocY());
		}
	}
	
	public void hostileAgentMovement(){
		hostileAgentCollisionX();
		hostileAgentCollisionY();
		for(HostileAgent hl: hostileAgentList){
			hl.displayHostileAgent();
			int movementGenerator = randomMovementGenerator();
			
			if(movementGenerator == 0){
				hl.locX += 25;
			}
			else if(movementGenerator == 1){
				hl.locX -= 25;
			}
			else if(movementGenerator == 2){
				hl.locY += 25;
			}
			else if(movementGenerator == 3){
				hl.locY -= 25;
			}
			if(hl.locX >= 500){
				hl.locX -= 25;
			}
			if(hl.locY >= 500){
				hl.locY -= 25;
			}
			if(hl.locX < 0){
				hl.locX += 25;
			}
			if(hl.locY < 0){
				hl.locY += 25;
			}
			hostileAgentMovementLimiter();
			hostileAgentSuccess();
		}
	}
	
	public void hostileAgentCollisionX(){
		for(HostileAgent hl: hostileAgentList){
			for(SafeHex sh: safeHexList){
				if(hl.locX+25 == sh.locationX && hl.locY == sh.locationY){
					hl.locX-=25;
				}
				if(hl.locX-25 == sh.locationX && hl.locY == sh.locationY){
					hl.locX+=25;
				}
			}
			for(ClosedHex ch: closedHexList){
				if(hl.locX+25 == ch.locationX && hl.locY == ch.locationY){
					hl.locX-=25;
				}
				if(hl.locX-25 == ch.locationX && hl.locY == ch.locationY){
					hl.locX+=25;
				}
			}
		}
	}
	public void hostileAgentCollisionY(){
		for(HostileAgent hl: hostileAgentList){
			for(SafeHex sh: safeHexList){
				if(hl.locY+25 == sh.locationY && hl.locX == sh.locationX){
					hl.locY-=25;
				}
				if(hl.locY-25 == sh.locationY && hl.locX == sh.locationX){
					hl.locY+=25;
				}
			}
			for(ClosedHex ch: closedHexList){
				if(hl.locY+25 == ch.locationY && hl.locX == ch.locationX){
					hl.locY-=25;
				}
				if(hl.locY-25 == ch.locationY && hl.locX == ch.locationX){
					hl.locY+=25;
				}
			}
		}
	}
	
	public void hostileAgentMovementLimiter(){
		for(HostileAgent hl: hostileAgentList){
			for(DangerHex dh: dangerHexList){
				if(hl.locX > dh.locationX+75){
					hl.locX -= 25;
				}
				if(hl.locX < dh.locationX-75){
					hl.locX += 25;
				}
				if(hl.locY > dh.locationY+75){
					hl.locY -= 25;
				}
				if(hl.locY < dh.locationY-75){
					hl.locY += 25;
				}
			}
		}
	}
	
	public void hostileAgentSuccess(){
		for(HostileAgent hl: hostileAgentList){
			if(primaryAgent.size1x == hl.locX && primaryAgent.size1y == hl.locY){
				System.out.println("Search Failure Primary Agent Disrupted");
				frameRate(0);
			}
		}
	}
	
	public void primaryAgentMoveX(){
		primaryAgentCollisionX();
		goalZoneX = primaryAgent.getGoalX(grid.getGoalX());
		//System.out.println("Goal X Axis: " + goalZoneX);
		//System.out.println("Primary Agent X Location: " + primaryAgent.size1x);
		if(primaryAgent.size1x < goalZoneX && xMoved == false){
			primaryAgent.size1x += 25;
			xMoved = true;
		}
		if(primaryAgent.size1x > goalZoneX && xMoved == false){
			primaryAgent.size1x -= 25;
			xMoved = true;
		}
		if(primaryAgent.size1x == goalZoneX){
			xMoved = true;
		}
		if(xMoved == true){
			counter++;
			agentPathList.add(new PrimaryAgent(this, primaryAgent.getLocationX(), primaryAgent.getLocationY(), 25));
		}
		
	}
	public void primaryAgentMoveY(){
		primaryAgentCollisionY();
		goalZoneY = primaryAgent.getGoalY(grid.getGoalY());
		//System.out.println("Goal Y Axis: " + goalZoneY);
		//System.out.println("Primary Agent Y Location: " + primaryAgent.size1y);
		if(primaryAgent.size1y < goalZoneY && xMoved == true){
		primaryAgent.size1y += 25;
		xMoved = false;
		yMoved = true;
		}
		if(primaryAgent.size1y > goalZoneY && xMoved == true){
		primaryAgent.size1y -= 25;
		xMoved = false;
		yMoved = true;
		}
		if(primaryAgent.size1y == goalZoneY){
		 xMoved = false;
		 yMoved = true;
		}
		if(yMoved == true){
		counter++;
		agentPathList.add(new PrimaryAgent(this, primaryAgent.getLocationX(), primaryAgent.getLocationY(), 25));
		}
	}
	public void agentEdgeCollision(){
		if(primaryAgent.size1x < 0){
			primaryAgent.size1x += 25;
		}
		if(primaryAgent.size1x >= 500){
			primaryAgent.size1x -= 25;
		}
		if(primaryAgent.size1y < 0){
			primaryAgent.size1y += 25;
		}
		if(primaryAgent.size1y >= 500){
			primaryAgent.size1y -= 25;
		}
	}
	public void primaryAgentCollisionX(){
		for(ClosedHex ch: closedHexList){
			if(primaryAgent.size1x+25 == ch.locationX && primaryAgent.size1y == ch.locationY && primaryAgent.size1x < goalZoneX){
				System.out.println("ClosedHex is + 25 on X axis rerouting");
				primaryAgent.size1x -= 25;
				xMoved = false;
			}
			if(primaryAgent.size1x-25 == ch.locationX && primaryAgent.size1y == ch.locationY && primaryAgent.size1x > goalZoneX){
				System.out.println("ClosedHex is - 25 on X axis rerouting");
				primaryAgent.size1x += 25;
				xMoved = false;
			}
		}
		for(DangerHex dh: dangerHexList){
			if(primaryAgent.size1x+25 == dh.locationX && primaryAgent.size1y == dh.locationY && primaryAgent.size1x < goalZoneX){
				System.out.println("ClosedHex is + 25 on X axis rerouting");
				primaryAgent.size1x -= 25;
				xMoved = false;
			}
			if(primaryAgent.size1x-25 == dh.locationX && primaryAgent.size1y == dh.locationY && primaryAgent.size1x > goalZoneX){
				System.out.println("ClosedHex is - 25 on X axis rerouting");
				primaryAgent.size1x += 25;
				xMoved = false;
			}
		}
	}
	public void primaryAgentCollisionY(){
		for(ClosedHex ch: closedHexList){
			if(primaryAgent.size1y+25 == ch.locationY && primaryAgent.size1x == ch.locationX && primaryAgent.size1y < goalZoneY){
				System.out.println("ClosedHex is + 25 on Y axis rerouting");
				primaryAgent.size1y -= 25;
				yMoved = false;
			}
			if(primaryAgent.size1y-25 == ch.locationY && primaryAgent.size1x == ch.locationX && primaryAgent.size1y > goalZoneY){
				System.out.println("ClosedHex is - 25 on Y axis rerouting");
				primaryAgent.size1y += 25;
				yMoved = false;
			}
		}
		for(DangerHex dh: dangerHexList){
			if(primaryAgent.size1y+25 == dh.locationY && primaryAgent.size1x == dh.locationX && primaryAgent.size1y < goalZoneY){
				System.out.println("ClosedHex is + 25 on Y axis rerouting");
				primaryAgent.size1y -= 25;
				yMoved = false;
			}
			if(primaryAgent.size1y-25 == dh.locationY && primaryAgent.size1x == dh.locationX && primaryAgent.size1y > goalZoneY){
				System.out.println("ClosedHex is - 25 on Y axis rerouting");
				primaryAgent.size1y += 25;
				yMoved = false;
			}
		}
	}
	
	public void primaryAgentHostileAgentDetection(){
		for(HostileAgent hl: hostileAgentList){
			if(primaryAgent.size1x+25 == hl.locX && primaryAgent.size1y == hl.locY){
				primaryAgent.size1x-=25;
			}
			if(primaryAgent.size1x-25 == hl.locX && primaryAgent.size1y == hl.locY){
				primaryAgent.size1x+=25;
			}
			if(primaryAgent.size1y+25 == hl.locY && primaryAgent.size1x == hl.locX){
				primaryAgent.size1y-=25;
			}
			if(primaryAgent.size1y-25 == hl.locY && primaryAgent.size1x == hl.locX){
				primaryAgent.size1y+=25;
			}
		}
	}
	
	public int randomNumberGenerator(){
		int min = 1;
		int max = 19;
		int randomNumber = ThreadLocalRandom.current().nextInt(min, max + 1);
		return randomNumber;
	}
	public int randomMovementGenerator(){
		int min = 0;
		int max = 3;
		int randomNumber = ThreadLocalRandom.current().nextInt(min, max + 1);
		return randomNumber;
	}
	public void safeSearchTest(){
		for(SafeHex sh: safeHexList){
			if(primaryAgent.size1x + 25 == sh.locationX && primaryAgent.size1y == sh.locationY && sh.locationX <= goalZoneX){
				primaryAgent.size1x += 25;
				System.out.println("Moved to safe zone!");
				xMoved = true;
			}
			if(primaryAgent.size1x - 25 == sh.locationX && primaryAgent.size1y == sh.locationY && sh.locationX >= goalZoneX){
				primaryAgent.size1x -= 25;
				System.out.println("Moved to safe zone!");
				xMoved = true;
			}
			if(primaryAgent.size1y + 25 == sh.locationY && primaryAgent.size1x == sh.locationX && sh.locationY <= goalZoneY){
				primaryAgent.size1y += 25;
				System.out.println("Moved to safe zone!");
				yMoved = true;
			}
			if(primaryAgent.size1y - 25 == sh.locationY && primaryAgent.size1x == sh.locationX && sh.locationY >= goalZoneY){
				primaryAgent.size1y -= 25;
				System.out.println("Moved to safe zone!");
				yMoved = true;
			}
		}
	}
	public void printAgentPath(){
		for(PrimaryAgent pa: agentPathList){
			pa.displayPrimaryAgentPath();
		}
	}
}
