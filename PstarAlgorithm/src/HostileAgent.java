import processing.core.PApplet;
import java.util.*;

public class HostileAgent extends Agent {

	int size = 25;
	int locX, locY;
	PApplet parent;
	
	
	HostileAgent(PApplet p, int hAgentX, int hAgentY, int hAgentsize){
		super(p, hAgentX, hAgentY, hAgentsize);
		locX = hAgentX;
		locY = hAgentY;
		size = hAgentsize;
	}
	
	public void displayHostileAgent(){
		super.parent.fill(255,255,0);
		super.parent.rect(locX, locY, size, size);
	}
	
}
