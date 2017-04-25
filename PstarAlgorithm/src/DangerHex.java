import processing.core.PApplet;
import java.util.*;

public class DangerHex extends PApplet {

	int locationX, locationY;
	int size;
	PApplet parent;
	
	DangerHex(PApplet p, int dHexX, int dHexY, int hexSize){	
		locationX = dHexX;
		locationY = dHexY;
		size = hexSize;
		parent = p;
	}
	
	public void displayDangerHex(){
		parent.fill(200,0,0);
		parent.rect(locationX, locationY, size, size);
	}
	
	public int getLocX(){
		return locationX;
	}
	public int getLocY(){
		return locationY;
	}
}
