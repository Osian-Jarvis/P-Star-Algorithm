import processing.core.PApplet;

public class ClosedHex extends PApplet  {
	
	PApplet parent;
	
	int locationX, locationY;
	int closedHexSize = 25;
	boolean closed = true;
	ClosedHex(PApplet p, int locX, int locY, int hexSize, boolean c){
		locationX = locX;
		locationY = locY;
		closedHexSize = hexSize;
		parent = p;
		closed = c;
	}
	
	public void displayClosedHex(){
		parent.fill(122);
		parent.rect(locationX, locationY, closedHexSize, closedHexSize);
	}
	
	public int getlocX(){
		return locationX;
	}
	public int getlocY(){
		return locationY;
	}
}
