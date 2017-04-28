import processing.core.PApplet;

public class ClosedHex extends PApplet  {
	
	PApplet parent;
	
	int locationX, locationY;
	int closedHexSize = 25;
	int colourValue;
	ClosedHex(PApplet p, int locX, int locY, int hexSize, int cV){
		locationX = locX;
		locationY = locY;
		closedHexSize = hexSize;
		parent = p;
		colourValue = cV;
	}
	
	public void displayClosedHex(){
		parent.fill(colourValue);
		parent.rect(locationX, locationY, closedHexSize, closedHexSize);
	}
	
	public int getlocX(){
		return locationX;
	}
	public int getlocY(){
		return locationY;
	}
}
