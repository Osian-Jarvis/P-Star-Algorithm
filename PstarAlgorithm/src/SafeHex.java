import processing.core.PApplet;

public class SafeHex extends PApplet {
	int locationX, locationY, safeHexSize;
	PApplet parent;
	SafeHex(PApplet p, int locX, int locY, int hexSize){
		locationX = locX;
		locationY = locY;
		safeHexSize = hexSize;
		parent = p;
	}
	
	public void displaySafeHex(){
		parent.fill(0,200,0);
		parent.rect(locationX,locationY,safeHexSize,safeHexSize);
	}
	
	public int getLocX(){
		return locationX;
	}
	
	public int getLocY(){
		return locationY;
	}
	
}
